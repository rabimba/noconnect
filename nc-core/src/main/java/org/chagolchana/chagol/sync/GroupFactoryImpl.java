package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.util.StringUtils;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class GroupFactoryImpl implements GroupFactory {

	private final CryptoComponent crypto;

	@Inject
	GroupFactoryImpl(CryptoComponent crypto) {
		this.crypto = crypto;
	}

	@Override
	public Group createGroup(ClientId c, byte[] descriptor) {
		byte[] hash = crypto.hash(GroupId.LABEL,
				StringUtils.toUtf8(c.getString()), descriptor);
		return new Group(new GroupId(hash), c, descriptor);
	}
}
