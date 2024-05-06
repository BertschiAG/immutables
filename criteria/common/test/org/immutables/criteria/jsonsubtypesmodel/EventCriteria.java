package org.immutables.criteria.jsonsubtypesmodel;

import org.immutables.criteria.matcher.CriteriaContext;
import org.immutables.criteria.matcher.CriteriaCreator;
import org.immutables.criteria.matcher.Disjunction;

/**
 * A {@code EventCriteria} provides fluent, type-safe API for querying
 * documents based on {@link Event} model.
 * <p>
 * This class is immutable and thus thread-safe.
 * </p>
 */
public final class EventCriteria
    extends EventCriteriaTemplate<EventCriteria>
    implements Disjunction<EventCriteriaTemplate<EventCriteria>> {

  /** Default criteria instance */
  public static final EventCriteria event =
      new EventCriteria(new CriteriaContext(Event.class, creator()));

  /** Used to instantiate EventCriteria by other criteria */
  public static CriteriaCreator<EventCriteria> creator() {
    return EventCriteria::new;
  }

  private EventCriteria(CriteriaContext context) {
    super(context);
  }
}
