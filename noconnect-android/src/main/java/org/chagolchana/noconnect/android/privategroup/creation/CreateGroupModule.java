package org.chagolchana.noconnect.android.privategroup.creation;

import org.chagolchana.noconnect.android.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateGroupModule {

	@ActivityScope
	@Provides
	CreateGroupController provideCreateGroupController(
			CreateGroupControllerImpl createGroupController) {
		return createGroupController;
	}

}
