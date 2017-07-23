package org.chagolchana.noconnect;

import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.feed.FeedModule;
import org.chagolchana.noconnect.forum.ForumModule;
import org.chagolchana.noconnect.introduction.IntroductionModule;
import org.chagolchana.noconnect.messaging.MessagingModule;
import org.chagolchana.noconnect.privategroup.PrivateGroupModule;
import org.chagolchana.noconnect.privategroup.invitation.GroupInvitationModule;
import org.chagolchana.noconnect.sharing.SharingModule;

public interface BriarCoreEagerSingletons {

	void inject(BlogModule.EagerSingletons init);

	void inject(FeedModule.EagerSingletons init);

	void inject(ForumModule.EagerSingletons init);

	void inject(GroupInvitationModule.EagerSingletons init);

	void inject(IntroductionModule.EagerSingletons init);

	void inject(MessagingModule.EagerSingletons init);

	void inject(PrivateGroupModule.EagerSingletons init);

	void inject(SharingModule.EagerSingletons init);
}
