package org.chagolchana.noconnect.android.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.activity.BriarActivity;
import org.chagolchana.noconnect.android.fragment.BaseFragment.BaseFragmentListener;
import org.chagolchana.noconnect.android.sharing.BlogSharingStatusActivity;

import javax.annotation.Nullable;
import javax.inject.Inject;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class BlogActivity extends BriarActivity
		implements BaseFragmentListener {

	@Inject
	BlogController blogController;

	@Override
	public void onCreate(@Nullable Bundle state) {
		super.onCreate(state);

		// GroupId from Intent
		Intent i = getIntent();
		byte[] b = i.getByteArrayExtra(GROUP_ID);
		if (b == null) throw new IllegalStateException("No group ID in intent");
		final GroupId groupId = new GroupId(b);
		blogController.setGroupId(groupId);

		setContentView(R.layout.activity_fragment_container_toolbar);
		Toolbar toolbar = setUpCustomToolbar(false);

		// Open Sharing Status on Toolbar click
		if (toolbar != null) {
			toolbar.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(BlogActivity.this,
									BlogSharingStatusActivity.class);
							i.putExtra(GROUP_ID, groupId.getBytes());
							startActivity(i);
						}
					});
		}

		if (state == null) {
			showInitialFragment(BlogFragment.newInstance(groupId));
		}
	}

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

}
