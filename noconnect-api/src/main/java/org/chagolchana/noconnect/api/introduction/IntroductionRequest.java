package org.chagolchana.noconnect.api.introduction;

import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.noconnect.api.client.SessionId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class IntroductionRequest extends IntroductionResponse {

	@Nullable
	private final String message;
	private final boolean answered, exists, introducesOtherIdentity;

	public IntroductionRequest(SessionId sessionId, MessageId messageId,
			GroupId groupId, int role, long time, boolean local, boolean sent,
			boolean seen, boolean read, AuthorId authorId, String name,
			boolean accepted, @Nullable String message, boolean answered,
			boolean exists, boolean introducesOtherIdentity) {

		super(sessionId, messageId, groupId, role, time, local, sent, seen,
				read, authorId, name, accepted);

		this.message = message;
		this.answered = answered;
		this.exists = exists;
		this.introducesOtherIdentity = introducesOtherIdentity;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public boolean wasAnswered() {
		return answered;
	}

	public boolean contactExists() {
		return exists;
	}

	public boolean doesIntroduceOtherIdentity() {
		return introducesOtherIdentity;
	}
}
