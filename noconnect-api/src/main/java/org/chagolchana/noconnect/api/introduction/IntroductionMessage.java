package org.chagolchana.noconnect.api.introduction;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.BaseMessageHeader;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.ROLE_INTRODUCER;

@Immutable
@NotNullByDefault
public class IntroductionMessage extends BaseMessageHeader {

	private final SessionId sessionId;
	private final MessageId messageId;
	private final int role;

	IntroductionMessage(SessionId sessionId, MessageId messageId,
			GroupId groupId, int role, long time, boolean local, boolean sent,
			boolean seen, boolean read) {

		super(messageId, groupId, time, local, sent, seen, read);
		this.sessionId = sessionId;
		this.messageId = messageId;
		this.role = role;
	}

	public SessionId getSessionId() {
		return sessionId;
	}

	public MessageId getMessageId() {
		return messageId;
	}

	public boolean isIntroducer() {
		return role == ROLE_INTRODUCER;
	}

}
