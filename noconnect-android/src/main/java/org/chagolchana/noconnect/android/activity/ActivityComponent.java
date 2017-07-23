package org.chagolchana.noconnect.android.activity;

import android.app.Activity;

import org.chagolchana.noconnect.android.AndroidComponent;
import org.chagolchana.noconnect.android.blog.BlogActivity;
import org.chagolchana.noconnect.android.blog.BlogFragment;
import org.chagolchana.noconnect.android.blog.BlogModule;
import org.chagolchana.noconnect.android.blog.BlogPostFragment;
import org.chagolchana.noconnect.android.blog.FeedFragment;
import org.chagolchana.noconnect.android.blog.FeedPostFragment;
import org.chagolchana.noconnect.android.blog.ReblogActivity;
import org.chagolchana.noconnect.android.blog.ReblogFragment;
import org.chagolchana.noconnect.android.blog.RssFeedImportActivity;
import org.chagolchana.noconnect.android.blog.RssFeedManageActivity;
import org.chagolchana.noconnect.android.blog.WriteBlogPostActivity;
import org.chagolchana.noconnect.android.contact.ContactListFragment;
import org.chagolchana.noconnect.android.contact.ContactModule;
import org.chagolchana.noconnect.android.contact.ConversationActivity;
import org.chagolchana.noconnect.android.forum.CreateForumActivity;
import org.chagolchana.noconnect.android.forum.ForumActivity;
import org.chagolchana.noconnect.android.forum.ForumListFragment;
import org.chagolchana.noconnect.android.forum.ForumModule;
import org.chagolchana.noconnect.android.introduction.ContactChooserFragment;
import org.chagolchana.noconnect.android.introduction.IntroductionActivity;
import org.chagolchana.noconnect.android.introduction.IntroductionMessageFragment;
import org.chagolchana.noconnect.android.invitation.AddContactActivity;
import org.chagolchana.noconnect.android.keyagreement.IntroFragment;
import org.chagolchana.noconnect.android.keyagreement.KeyAgreementActivity;
import org.chagolchana.noconnect.android.keyagreement.ShowQrCodeFragment;
import org.chagolchana.noconnect.android.login.ChangePasswordActivity;
import org.chagolchana.noconnect.android.login.PasswordActivity;
import org.chagolchana.noconnect.android.login.SetupActivity;
import org.chagolchana.noconnect.android.navdrawer.NavDrawerActivity;
import org.chagolchana.noconnect.android.panic.PanicPreferencesActivity;
import org.chagolchana.noconnect.android.panic.PanicResponderActivity;
import org.chagolchana.noconnect.android.privategroup.conversation.GroupActivity;
import org.chagolchana.noconnect.android.privategroup.conversation.GroupConversationModule;
import org.chagolchana.noconnect.android.privategroup.creation.CreateGroupActivity;
import org.chagolchana.noconnect.android.privategroup.creation.CreateGroupFragment;
import org.chagolchana.noconnect.android.privategroup.creation.CreateGroupMessageFragment;
import org.chagolchana.noconnect.android.privategroup.creation.CreateGroupModule;
import org.chagolchana.noconnect.android.privategroup.creation.GroupInviteActivity;
import org.chagolchana.noconnect.android.privategroup.creation.GroupInviteFragment;
import org.chagolchana.noconnect.android.privategroup.invitation.GroupInvitationActivity;
import org.chagolchana.noconnect.android.privategroup.invitation.GroupInvitationModule;
import org.chagolchana.noconnect.android.privategroup.list.GroupListFragment;
import org.chagolchana.noconnect.android.privategroup.list.GroupListModule;
import org.chagolchana.noconnect.android.privategroup.memberlist.GroupMemberListActivity;
import org.chagolchana.noconnect.android.privategroup.memberlist.GroupMemberModule;
import org.chagolchana.noconnect.android.privategroup.reveal.GroupRevealModule;
import org.chagolchana.noconnect.android.privategroup.reveal.RevealContactsActivity;
import org.chagolchana.noconnect.android.privategroup.reveal.RevealContactsFragment;
import org.chagolchana.noconnect.android.settings.SettingsActivity;
import org.chagolchana.noconnect.android.sharing.BlogInvitationActivity;
import org.chagolchana.noconnect.android.sharing.BlogSharingStatusActivity;
import org.chagolchana.noconnect.android.sharing.ForumInvitationActivity;
import org.chagolchana.noconnect.android.sharing.ForumSharingStatusActivity;
import org.chagolchana.noconnect.android.sharing.ShareBlogActivity;
import org.chagolchana.noconnect.android.sharing.ShareBlogFragment;
import org.chagolchana.noconnect.android.sharing.ShareBlogMessageFragment;
import org.chagolchana.noconnect.android.sharing.ShareForumActivity;
import org.chagolchana.noconnect.android.sharing.ShareForumFragment;
import org.chagolchana.noconnect.android.sharing.ShareForumMessageFragment;
import org.chagolchana.noconnect.android.sharing.SharingModule;
import org.chagolchana.noconnect.android.splash.SplashScreenActivity;

import dagger.Component;

@ActivityScope
@Component(
		modules = {ActivityModule.class, ForumModule.class, SharingModule.class,
				BlogModule.class, ContactModule.class, GroupListModule.class,
				CreateGroupModule.class, GroupInvitationModule.class,
				GroupConversationModule.class, GroupMemberModule.class,
				GroupRevealModule.class},
		dependencies = AndroidComponent.class)
public interface ActivityComponent {

	Activity activity();

	void inject(SplashScreenActivity activity);

	void inject(SetupActivity activity);

	void inject(NavDrawerActivity activity);

	void inject(PasswordActivity activity);

	void inject(PanicResponderActivity activity);

	void inject(PanicPreferencesActivity activity);

	void inject(AddContactActivity activity);

	void inject(KeyAgreementActivity activity);

	void inject(ConversationActivity activity);

	void inject(ForumInvitationActivity activity);

	void inject(BlogInvitationActivity activity);

	void inject(CreateGroupActivity activity);

	void inject(GroupActivity activity);

	void inject(GroupInviteActivity activity);

	void inject(GroupInvitationActivity activity);

	void inject(GroupMemberListActivity activity);

	void inject(RevealContactsActivity activity);

	void inject(CreateForumActivity activity);

	void inject(ShareForumActivity activity);

	void inject(ShareBlogActivity activity);

	void inject(ForumSharingStatusActivity activity);

	void inject(BlogSharingStatusActivity activity);

	void inject(ForumActivity activity);

	void inject(BlogActivity activity);

	void inject(WriteBlogPostActivity activity);

	void inject(BlogFragment fragment);

	void inject(BlogPostFragment fragment);

	void inject(FeedPostFragment fragment);

	void inject(ReblogFragment fragment);

	void inject(ReblogActivity activity);

	void inject(SettingsActivity activity);

	void inject(ChangePasswordActivity activity);

	void inject(IntroductionActivity activity);

	void inject(RssFeedImportActivity activity);

	void inject(RssFeedManageActivity activity);

	// Fragments
	void inject(ContactListFragment fragment);

	void inject(CreateGroupFragment fragment);

	void inject(CreateGroupMessageFragment fragment);

	void inject(GroupListFragment fragment);

	void inject(GroupInviteFragment fragment);

	void inject(RevealContactsFragment activity);

	void inject(ForumListFragment fragment);

	void inject(FeedFragment fragment);

	void inject(IntroFragment fragment);

	void inject(ShowQrCodeFragment fragment);

	void inject(ContactChooserFragment fragment);

	void inject(ShareForumFragment fragment);

	void inject(ShareForumMessageFragment fragment);

	void inject(ShareBlogFragment fragment);

	void inject(ShareBlogMessageFragment fragment);

	void inject(IntroductionMessageFragment fragment);

}
