package org.chagolchana.noconnect.android.forum;

import org.chagolchana.noconnect.android.activity.ActivityScope;
import org.chagolchana.noconnect.android.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ForumModule {

	@ActivityScope
	@Provides
	ForumController provideForumController(BaseActivity activity,
			ForumControllerImpl forumController) {
		activity.addLifecycleController(forumController);
		return forumController;
	}

}
