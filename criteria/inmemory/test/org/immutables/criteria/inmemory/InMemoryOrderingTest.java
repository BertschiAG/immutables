/*
 * Copyright 2026 Immutables Authors and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.immutables.criteria.inmemory;

import org.immutables.criteria.backend.Backend;
import org.immutables.criteria.typemodel.ImmutableStringHolder;
import org.immutables.criteria.typemodel.StringHolderCriteria;
import org.immutables.criteria.typemodel.StringHolderRepository;
import org.immutables.criteria.typemodel.TypeHolder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.immutables.check.Checkers.check;

/**
 * Ordering by an attribute which is absent on some of the stored values.
 *
 * <p>A nullable attribute is a first-class question when filtering ({@code isAbsent()}), so sorting
 * by one has to work as well. The expected order is the one MongoDB produces: missing values first
 * when ascending, last when descending.
 */
class InMemoryOrderingTest {

  private final Backend backend = new InMemoryBackend();
  private final StringHolderRepository repository = new StringHolderRepository(backend);
  private final StringHolderCriteria string = StringHolderCriteria.stringHolder;
  private final Supplier<ImmutableStringHolder> generator = TypeHolder.StringHolder.generator();

  @Test
  void orderByNullableAscending() {
    insertFixture();

    check(ids(repository.findAll().orderBy(string.nullable.asc()).fetch())).isOf("id2", "id1", "id3");
  }

  @Test
  void orderByNullableDescending() {
    insertFixture();

    check(ids(repository.findAll().orderBy(string.nullable.desc()).fetch())).isOf("id3", "id1", "id2");
  }

  /**
   * The null is reached only through a tie on the first key, so a sort key without any null in it
   * is enough to hit the missing value on the second one.
   */
  @Test
  void orderByValueThenNullable() {
    insertFixture();

    check(ids(repository.findAll().orderBy(string.value.asc(), string.nullable.asc()).fetch()))
            .isOf("id2", "id1", "id3");
  }

  private void insertFixture() {
    repository.insert(generator.get().withId("id1").withValue("same").withNullable("a"));
    repository.insert(generator.get().withId("id2").withValue("same").withNullable(null));
    repository.insert(generator.get().withId("id3").withValue("same").withNullable("b"));
  }

  private static List<String> ids(List<TypeHolder.StringHolder> holders) {
    return holders.stream().map(TypeHolder.StringHolder::id).collect(Collectors.toList());
  }
}
