package org.chagolchana.chagol.event;

import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.event.EventListener;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@NotNullByDefault
class EventBusImpl implements EventBus {

	private final Collection<EventListener> listeners =
			new CopyOnWriteArrayList<EventListener>();

	@Override
	public void addListener(EventListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListener(EventListener l) {
		listeners.remove(l);
	}

	@Override
	public void broadcast(Event e) {
		for (EventListener l : listeners) l.eventOccurred(e);
	}
}
