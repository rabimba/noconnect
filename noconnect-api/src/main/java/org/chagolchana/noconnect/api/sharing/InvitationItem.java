package org.chagolchana.noconnect.api.sharing;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class InvitationItem<S extends Shareable> {

	private final S shareable;
	private final boolean subscribed;

	public InvitationItem(S shareable, boolean subscribed) {
		this.shareable = shareable;
		this.subscribed = subscribed;
	}

	public S getShareable() {
		return shareable;
	}

	public GroupId getId() {
		return shareable.getId();
	}

	public String getName() {
		return shareable.getName();
	}

	public boolean isSubscribed() {
		return subscribed;
	}

}
