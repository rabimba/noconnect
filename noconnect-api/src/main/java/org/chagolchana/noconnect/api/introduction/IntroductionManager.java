package org.chagolchana.noconnect.api.introduction;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.ClientId;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.api.messaging.ConversationManager.ConversationClient;

import java.util.Collection;

import javax.annotation.Nullable;

@NotNullByDefault
public interface IntroductionManager extends ConversationClient {

	/**
	 * The unique ID of the introduction client.
	 */
	ClientId CLIENT_ID = new ClientId("org.briarproject.briar.introduction");

	/**
	 * Sends two initial introduction messages.
	 */
	void makeIntroduction(Contact c1, Contact c2, @Nullable String msg,
			final long timestamp) throws DbException, FormatException;

	/**
	 * Accepts an introduction.
	 */
	void acceptIntroduction(final ContactId contactId,
			final SessionId sessionId, final long timestamp)
			throws DbException, FormatException;

	/**
	 * Declines an introduction.
	 */
	void declineIntroduction(final ContactId contactId,
			final SessionId sessionId, final long timestamp)
			throws DbException, FormatException;

	/**
	 * Returns all introduction messages for the given contact.
	 */
	Collection<IntroductionMessage> getIntroductionMessages(ContactId contactId)
			throws DbException;

}
