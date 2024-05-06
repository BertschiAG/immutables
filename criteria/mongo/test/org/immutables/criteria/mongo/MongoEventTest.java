package org.immutables.criteria.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.immutables.criteria.backend.Backend;
import org.immutables.criteria.jsonsubtypesmodel.ErrorEvent;
import org.immutables.criteria.jsonsubtypesmodel.Event;
import org.immutables.criteria.jsonsubtypesmodel.EventCriteria;
import org.immutables.criteria.jsonsubtypesmodel.ImmutableError;
import org.immutables.criteria.jsonsubtypesmodel.ImmutableErrorEvent;
import org.immutables.criteria.repository.sync.SyncReadable;
import org.immutables.criteria.repository.sync.SyncWritable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mongodb.reactivestreams.client.MongoDatabase;

@ExtendWith(MongoExtension.class)
public class MongoEventTest {

  private final SyncReadable<Event> syncReadable;
  private final SyncWritable<Event> syncWritable;

  MongoEventTest(MongoDatabase database) {
    Objects.requireNonNull(database);
    Backend.Session session = new BackendResource(database).backend().open(Event.class);
    this.syncReadable = new SyncReadable<>(session);
    this.syncWritable = new SyncWritable<>(session);
  }

  @Test
  void insertAndFindOne() {
    ErrorEvent errorEvent = ImmutableErrorEvent.builder()
        .id(UUID.randomUUID())
        .instant(Instant.now().truncatedTo(ChronoUnit.MILLIS))
        .data(toError(getRandomException()))
        .build();
    
    this.syncWritable.insert(errorEvent);
    Event<?> loadedEvent = this.syncReadable.find(EventCriteria.event.id.is(errorEvent.id())).one();
    
    assertEquals(errorEvent, loadedEvent);
  }

  private static RuntimeException getRandomException() {
    return new RuntimeException(String.format("Aw snap! (%s)", UUID.randomUUID()));
  }

  private static ImmutableError toError(RuntimeException runtimeException) {
    return ImmutableError.builder()
        .message(runtimeException.getMessage())
        .stacktrace(Arrays.stream(runtimeException.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList()))
        .build();
  }
}
