package org.chagolchana.noconnect.android.blog;

import org.chagolchana.noconnect.android.activity.ActivityScope;
import org.chagolchana.noconnect.android.activity.BaseActivity;
import org.chagolchana.noconnect.android.controller.SharingController;
import org.chagolchana.noconnect.android.controller.SharingControllerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class BlogModule {

	@ActivityScope
	@Provides
	BlogController provideBlogController(BaseActivity activity,
			BlogControllerImpl blogController) {
		activity.addLifecycleController(blogController);
		return blogController;
	}

	@ActivityScope
	@Provides
	FeedController provideFeedController(FeedControllerImpl feedController) {
		return feedController;
	}

	@ActivityScope
	@Provides
	SharingController provideSharingController(
			SharingControllerImpl sharingController) {
		return sharingController;
	}

}
