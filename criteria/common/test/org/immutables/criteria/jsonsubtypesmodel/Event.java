package org.immutables.criteria.jsonsubtypesmodel;

import java.time.Instant;
import java.util.UUID;

import org.immutables.criteria.Criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(
        value = ImmutableErrorEvent.class,
        name = "org.immutables.criteria.jsonsubtypesmodel.ImmutableErrorEvent")
})
public interface Event<DATA> {
  @Criteria.Id
  UUID id();
  Instant instant();
  DATA data();
}
