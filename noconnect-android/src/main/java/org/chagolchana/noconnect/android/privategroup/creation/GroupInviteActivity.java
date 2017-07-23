package org.chagolchana.noconnect.android.privategroup.creation;

import android.content.Intent;
import android.os.Bundle;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorActivity;
import org.chagolchana.noconnect.android.controller.handler.UiResultExceptionHandler;
import org.chagolchana.noconnect.android.sharing.BaseMessageFragment.MessageFragmentListener;

import java.util.Collection;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static org.chagolchana.noconnect.api.privategroup.PrivateGroupConstants.MAX_GROUP_INVITATION_MSG_LENGTH;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class GroupInviteActivity extends ContactSelectorActivity
		implements MessageFragmentListener {

	@Inject
	CreateGroupController controller;

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(@Nullable Bundle bundle) {
		super.onCreate(bundle);

		Intent i = getIntent();
		byte[] g = i.getByteArrayExtra(GROUP_ID);
		if (g == null) throw new IllegalStateException("No GroupId in intent");
		groupId = new GroupId(g);

		if (bundle == null) {
			showInitialFragment(GroupInviteFragment.newInstance(groupId));
		}
	}

	@Override
	public void contactsSelected(Collection<ContactId> contacts) {
		super.contactsSelected(contacts);

		showNextFragment(new CreateGroupMessageFragment());
	}

	@Override
	public boolean onButtonClick(String message) {
		if (groupId == null)
			throw new IllegalStateException("GroupId was not initialized");
		controller.sendInvitation(groupId, contacts, message,
				new UiResultExceptionHandler<Void, DbException>(this) {
					@Override
					public void onResultUi(Void result) {
						setResult(RESULT_OK);
						supportFinishAfterTransition();
					}

					@Override
					public void onExceptionUi(DbException exception) {
						setResult(RESULT_CANCELED);
						handleDbException(exception);
					}
				});
		return true;
	}

	@Override
	public int getMaximumMessageLength() {
		return MAX_GROUP_INVITATION_MSG_LENGTH;
	}
}
