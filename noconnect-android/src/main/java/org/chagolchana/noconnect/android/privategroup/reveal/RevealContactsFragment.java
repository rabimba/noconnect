package org.chagolchana.noconnect.android.privategroup.reveal;

import android.content.Context;
import android.os.Bundle;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.contact.BaseContactListAdapter.OnContactClickListener;
import org.chagolchana.noconnect.android.contactselection.BaseContactSelectorFragment;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorController;

import java.util.Collection;

import javax.inject.Inject;

import static org.chagolchana.noconnect.android.activity.BriarActivity.GROUP_ID;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class RevealContactsFragment extends
		BaseContactSelectorFragment<RevealableContactItem, RevealableContactAdapter> {

	private final static String TAG = RevealContactsFragment.class.getName();

	@Inject
	RevealContactsController controller;

	public static RevealContactsFragment newInstance(GroupId groupId) {
		Bundle args = new Bundle();
		args.putByteArray(GROUP_ID, groupId.getBytes());
		RevealContactsFragment fragment = new RevealContactsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void injectFragment(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	protected ContactSelectorController<RevealableContactItem> getController() {
		return controller;
	}

	@Override
	protected RevealableContactAdapter getAdapter(Context context,
			OnContactClickListener<RevealableContactItem> listener) {
		return new RevealableContactAdapter(context, listener);
	}

	@Override
	protected void onSelectionChanged() {
		Collection<ContactId> selected = adapter.getSelectedContactIds();
		Collection<ContactId> disabled = adapter.getDisabledContactIds();
		selected.removeAll(disabled);

		// tell the activity which contacts have been selected
		listener.contactsSelected(selected);
	}

	@Override
	public String getUniqueTag() {
		return TAG;
	}

}
