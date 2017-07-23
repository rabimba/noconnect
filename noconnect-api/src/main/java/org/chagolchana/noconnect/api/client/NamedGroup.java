package org.chagolchana.noconnect.api.client;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public abstract class NamedGroup extends BaseGroup {

	private final String name;
	private final byte[] salt;

	public NamedGroup(Group group, String name, byte[] salt) {
		super(group);
		this.name = name;
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public byte[] getSalt() {
		return salt;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof NamedGroup && super.equals(o);
	}

}
