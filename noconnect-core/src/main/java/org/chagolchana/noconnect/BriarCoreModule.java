package org.chagolchana.noconnect;

import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.client.BriarClientModule;
import org.chagolchana.noconnect.feed.DnsModule;
import org.chagolchana.noconnect.feed.FeedModule;
import org.chagolchana.noconnect.forum.ForumModule;
import org.chagolchana.noconnect.introduction.IntroductionModule;
import org.chagolchana.noconnect.messaging.MessagingModule;
import org.chagolchana.noconnect.privategroup.PrivateGroupModule;
import org.chagolchana.noconnect.privategroup.invitation.GroupInvitationModule;
import org.chagolchana.noconnect.sharing.SharingModule;

import dagger.Module;

@Module(includes = {
		BlogModule.class,
		BriarClientModule.class,
		FeedModule.class,
		DnsModule.class,
		ForumModule.class,
		GroupInvitationModule.class,
		IntroductionModule.class,
		MessagingModule.class,
		PrivateGroupModule.class,
		SharingModule.class
})
public class BriarCoreModule {

	public static void initEagerSingletons(BriarCoreEagerSingletons c) {
		c.inject(new BlogModule.EagerSingletons());
		c.inject(new FeedModule.EagerSingletons());
		c.inject(new ForumModule.EagerSingletons());
		c.inject(new GroupInvitationModule.EagerSingletons());
		c.inject(new MessagingModule.EagerSingletons());
		c.inject(new PrivateGroupModule.EagerSingletons());
		c.inject(new SharingModule.EagerSingletons());
		c.inject(new IntroductionModule.EagerSingletons());
	}
}
