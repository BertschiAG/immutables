package org.immutables.criteria.jsonsubtypesmodel;

import java.time.Instant;
import java.util.UUID;

import org.immutables.criteria.Criterion;
import org.immutables.criteria.matcher.AbstractContextHolder;
import org.immutables.criteria.matcher.AndMatcher;
import org.immutables.criteria.matcher.ComparableMatcher;
import org.immutables.criteria.matcher.CriteriaContext;
import org.immutables.criteria.matcher.NotMatcher;
import org.immutables.criteria.matcher.ObjectMatcher;
import org.immutables.criteria.matcher.OrMatcher;
import org.immutables.criteria.matcher.Projection;
import org.immutables.criteria.matcher.WithMatcher;

/**
 * Base class for {@link EventCriteria} also used as matcher attribute on other
 * criteria.
 *
 * @param <R>
 *          root self-type for fluent API
 */
public abstract class EventCriteriaTemplate<R> extends AbstractContextHolder
    implements Criterion<Event>, AndMatcher<EventCriteria>, OrMatcher<EventCriteria>, NotMatcher<R, EventCriteria>,
    WithMatcher<R, EventCriteria>, Projection<Event> {

  public final ObjectMatcher.Template<R, UUID> id;
  public final ComparableMatcher.Template<R, Instant> instant;

  @SuppressWarnings("unchecked")
  EventCriteriaTemplate(CriteriaContext context) {
    super(context);
    this.id = (ObjectMatcher.Template<R, UUID>) ObjectMatcher.creator()
        .create(context.appendPath(Event.class, "id", ObjectMatcher.creator()));
    this.instant = (ComparableMatcher.Template<R, Instant>) ComparableMatcher.creator().create(
        context.appendPath(Event.class, "instant", ComparableMatcher.creator()));
  }
}