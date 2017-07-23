package org.chagolchana.noconnect.android.privategroup.list;

import org.chagolchana.noconnect.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupListModule {

	@ActivityScope
	@Provides
	GroupListController provideGroupListController(
			GroupListControllerImpl groupListController) {
		return groupListController;
	}
}
