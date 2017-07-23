package org.chagolchana.noconnect.api.blog.event;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.api.blog.Blog;
import org.chagolchana.noconnect.api.sharing.InvitationRequest;
import org.chagolchana.noconnect.api.sharing.event.InvitationRequestReceivedEvent;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BlogInvitationRequestReceivedEvent extends
		InvitationRequestReceivedEvent<Blog> {

	public BlogInvitationRequestReceivedEvent(Blog blog, ContactId contactId,
			InvitationRequest<Blog> request) {
		super(blog, contactId, request);
	}

}
