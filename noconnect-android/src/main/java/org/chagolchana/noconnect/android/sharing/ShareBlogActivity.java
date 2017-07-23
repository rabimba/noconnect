package org.chagolchana.noconnect.android.sharing;

import android.os.Bundle;
import android.widget.Toast;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.controller.handler.UiExceptionHandler;

import java.util.Collection;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static android.widget.Toast.LENGTH_SHORT;
import static org.chagolchana.chagol.api.sync.SyncConstants.MAX_MESSAGE_BODY_LENGTH;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class ShareBlogActivity extends ShareActivity {

	@Inject
	ShareBlogController controller;

	@Override
	BaseMessageFragment getMessageFragment() {
		return ShareBlogMessageFragment.newInstance();
	}

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(@Nullable Bundle bundle) {
		super.onCreate(bundle);

		if (bundle == null) {
			showInitialFragment(ShareBlogFragment.newInstance(groupId));
		}
	}

	@Override
	public int getMaximumMessageLength() {
		return MAX_MESSAGE_BODY_LENGTH;
	}

	@Override
	void share(Collection<ContactId> contacts, String msg) {
		controller.share(groupId, contacts, msg,
				new UiExceptionHandler<DbException>(this) {
					@Override
					public void onExceptionUi(DbException exception) {
						Toast.makeText(ShareBlogActivity.this,
								R.string.blogs_sharing_error, LENGTH_SHORT)
								.show();
						handleDbException(exception);
					}
				});

	}

}
