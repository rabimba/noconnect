package org.chagolchana.noconnect.api.forum;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.noconnect.api.client.NamedGroup;
import org.chagolchana.noconnect.api.sharing.Shareable;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class Forum extends NamedGroup implements Shareable {

	public Forum(Group group, String name, byte[] salt) {
		super(group, name, salt);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Forum && super.equals(o);
	}

}
