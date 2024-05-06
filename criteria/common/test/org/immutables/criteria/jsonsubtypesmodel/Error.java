package org.immutables.criteria.jsonsubtypesmodel;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonSerialize(as = ImmutableError.class)
@JsonDeserialize(as = ImmutableError.class)
public interface Error {
  String message();
  List<String> stacktrace();
}
