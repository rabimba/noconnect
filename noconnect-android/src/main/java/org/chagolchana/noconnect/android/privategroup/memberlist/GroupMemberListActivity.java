package org.chagolchana.noconnect.android.privategroup.memberlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.TextView;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.activity.BriarActivity;
import org.chagolchana.noconnect.android.controller.handler.UiResultExceptionHandler;
import org.chagolchana.noconnect.android.view.BriarRecyclerView;

import java.util.Collection;

import javax.annotation.Nullable;
import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class GroupMemberListActivity extends BriarActivity {

	@Inject
	GroupMemberListController controller;

	private MemberListAdapter adapter;
	private BriarRecyclerView list;
	private GroupId groupId;

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(@Nullable final Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_sharing_status);

		Intent i = getIntent();
		byte[] b = i.getByteArrayExtra(GROUP_ID);
		if (b == null) throw new IllegalStateException("No GroupId in intent.");
		groupId = new GroupId(b);

		list = (BriarRecyclerView) findViewById(R.id.list);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		list.setLayoutManager(linearLayoutManager);
		adapter = new MemberListAdapter(this);
		list.setAdapter(adapter);

		TextView info = (TextView) findViewById(R.id.info);
		info.setText(R.string.sharing_status_groups);
	}

	@Override
	public void onStart() {
		super.onStart();
		controller.loadMembers(groupId,
				new UiResultExceptionHandler<Collection<MemberListItem>, DbException>(
						this) {
					@Override
					public void onResultUi(Collection<MemberListItem> members) {
						adapter.addAll(members);
					}

					@Override
					public void onExceptionUi(DbException exception) {
						handleDbException(exception);
					}
				});
		list.startPeriodicUpdate();
	}

	@Override
	public void onStop() {
		super.onStop();
		list.stopPeriodicUpdate();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
