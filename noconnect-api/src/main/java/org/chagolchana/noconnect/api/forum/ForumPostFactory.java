package org.chagolchana.noconnect.api.forum;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.crypto.CryptoExecutor;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;

import java.security.GeneralSecurityException;

import javax.annotation.Nullable;

import static org.chagolchana.noconnect.api.forum.ForumManager.CLIENT_ID;

@NotNullByDefault
public interface ForumPostFactory {

	String SIGNING_LABEL_POST = CLIENT_ID + "/POST";

	@CryptoExecutor
	ForumPost createPost(GroupId groupId, long timestamp,
			@Nullable MessageId parent, LocalAuthor author, String body)
			throws FormatException, GeneralSecurityException;

}
