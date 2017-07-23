package org.chagolchana.noconnect.android.privategroup.creation;

import android.content.Intent;
import android.os.Bundle;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.activity.BriarActivity;
import org.chagolchana.noconnect.android.controller.handler.UiResultExceptionHandler;
import org.chagolchana.noconnect.android.privategroup.conversation.GroupActivity;

import javax.annotation.Nullable;
import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class CreateGroupActivity extends BriarActivity
		implements CreateGroupListener {

	@Inject
	CreateGroupController controller;

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(@Nullable Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_fragment_container);

		if (bundle == null) {
			showInitialFragment(new CreateGroupFragment());
		}
	}

	@Override
	public void onGroupNameChosen(String name) {
		controller.createGroup(name,
				new UiResultExceptionHandler<GroupId, DbException>(this) {
					@Override
					public void onResultUi(GroupId g) {
						openNewGroup(g);
					}

					@Override
					public void onExceptionUi(DbException exception) {
						handleDbException(exception);
					}
				});
	}

	private void openNewGroup(GroupId g) {
		Intent i = new Intent(this, GroupActivity.class);
		i.putExtra(GROUP_ID, g.getBytes());
		startActivity(i);
		finish();
	}
}
