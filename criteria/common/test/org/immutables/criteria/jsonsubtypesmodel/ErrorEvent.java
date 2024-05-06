package org.immutables.criteria.jsonsubtypesmodel;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonSerialize(as = ImmutableErrorEvent.class)
@JsonDeserialize(as = ImmutableErrorEvent.class)
public interface ErrorEvent extends Event<Error> {
  @Override
  Error data();
}
