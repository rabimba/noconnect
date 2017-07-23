package org.chagolchana.noconnect.android.forum;

import android.os.Bundle;

import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityModule;
import org.chagolchana.noconnect.android.activity.BaseActivity;
import org.chagolchana.noconnect.android.controller.BriarController;
import org.chagolchana.noconnect.android.controller.BriarControllerImpl;
import org.chagolchana.noconnect.android.threaded.ThreadItemAdapter;
import org.mockito.Mockito;

import javax.annotation.Nullable;

/**
 * This class exposes the ForumController and offers the possibility to
 * override it.
 */
@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class TestForumActivity extends ForumActivity {

	@Override
	public ForumController getController() {
		return forumController;
	}

	public ThreadItemAdapter<ForumItem> getAdapter() {
		return adapter;
	}

	@Override
	public void onCreate(@Nullable Bundle state) {
		setTheme(R.style.BriarTheme_NoActionBar);
		super.onCreate(state);
	}

	@Override
	protected ActivityModule getActivityModule() {
		return new ActivityModule(this) {

			@Override
			protected BriarController provideBriarController(
					BriarControllerImpl briarController) {
				BriarController c = Mockito.mock(BriarController.class);
				Mockito.when(c.hasEncryptionKey()).thenReturn(true);
				return c;
			}

		};
	}

	@Override
	protected ForumModule getForumModule() {
		return new ForumModule() {
			@Override
			ForumController provideForumController(BaseActivity activity,
					ForumControllerImpl forumController) {
				return Mockito.mock(ForumController.class);
			}
		};
	}
}
