package org.chagolchana.noconnect.android.introduction;

import android.content.Intent;
import android.os.Bundle;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.activity.BriarActivity;
import org.chagolchana.noconnect.android.fragment.BaseFragment.BaseFragmentListener;

import static org.chagolchana.noconnect.android.contact.ConversationActivity.CONTACT_ID;

public class IntroductionActivity extends BriarActivity
		implements BaseFragmentListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		int id = intent.getIntExtra(CONTACT_ID, -1);
		if (id == -1) throw new IllegalStateException("No ContactId");
		ContactId contactId = new ContactId(id);

		setContentView(R.layout.activity_fragment_container);

		if (savedInstanceState == null) {
			showInitialFragment(ContactChooserFragment.newInstance(contactId));
		}
	}

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

}
