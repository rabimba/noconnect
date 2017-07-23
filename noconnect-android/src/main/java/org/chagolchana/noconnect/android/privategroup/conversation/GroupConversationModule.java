package org.chagolchana.noconnect.android.privategroup.conversation;

import org.chagolchana.noconnect.android.activity.ActivityScope;
import org.chagolchana.noconnect.android.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupConversationModule {

	@ActivityScope
	@Provides
	GroupController provideGroupController(BaseActivity activity,
			GroupControllerImpl groupController) {
		activity.addLifecycleController(groupController);
		return groupController;
	}
}
