package org.chagolchana.chagol.client;

import org.chagolchana.chagol.api.Bytes;
import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.client.ContactGroupFactory;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupFactory;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class ContactGroupFactoryImpl implements ContactGroupFactory {

	private static final byte[] LOCAL_GROUP_DESCRIPTOR = new byte[0];

	private final GroupFactory groupFactory;
	private final ClientHelper clientHelper;

	@Inject
	ContactGroupFactoryImpl(GroupFactory groupFactory,
			ClientHelper clientHelper) {
		this.groupFactory = groupFactory;
		this.clientHelper = clientHelper;
	}

	@Override
	public Group createLocalGroup(ClientId clientId) {
		return groupFactory.createGroup(clientId, LOCAL_GROUP_DESCRIPTOR);
	}

	@Override
	public Group createContactGroup(ClientId clientId, Contact contact) {
		AuthorId local = contact.getLocalAuthorId();
		AuthorId remote = contact.getAuthor().getId();
		byte[] descriptor = createGroupDescriptor(local, remote);
		return groupFactory.createGroup(clientId, descriptor);
	}

	@Override
	public Group createContactGroup(ClientId clientId, AuthorId authorId1,
			AuthorId authorId2) {
		byte[] descriptor = createGroupDescriptor(authorId1, authorId2);
		return groupFactory.createGroup(clientId, descriptor);
	}

	private byte[] createGroupDescriptor(AuthorId local, AuthorId remote) {
		try {
			if (Bytes.COMPARATOR.compare(local, remote) < 0)
				return clientHelper.toByteArray(BdfList.of(local, remote));
			else return clientHelper.toByteArray(BdfList.of(remote, local));
		} catch (FormatException e) {
			throw new RuntimeException(e);
		}
	}
}
