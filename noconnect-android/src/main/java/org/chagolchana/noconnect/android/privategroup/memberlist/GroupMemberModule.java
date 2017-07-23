package org.chagolchana.noconnect.android.privategroup.memberlist;

import org.chagolchana.noconnect.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupMemberModule {

	@ActivityScope
	@Provides
	GroupMemberListController provideGroupMemberListController(
			GroupMemberListControllerImpl groupMemberListController) {
		return groupMemberListController;
	}
}
