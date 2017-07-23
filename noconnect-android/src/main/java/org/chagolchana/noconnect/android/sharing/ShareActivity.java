package org.chagolchana.noconnect.android.sharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.sync.GroupId;
import org.chagolchana.noconnect.android.contactselection.ContactSelectorActivity;
import org.chagolchana.noconnect.android.sharing.BaseMessageFragment.MessageFragmentListener;

import java.util.Collection;

import javax.annotation.Nullable;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public abstract class ShareActivity extends ContactSelectorActivity
		implements MessageFragmentListener {

	@Override
	public void onCreate(@Nullable Bundle bundle) {
		super.onCreate(bundle);

		Intent i = getIntent();
		byte[] b = i.getByteArrayExtra(GROUP_ID);
		if (b == null) throw new IllegalStateException("No GroupId");
		groupId = new GroupId(b);
	}

	@UiThread
	@Override
	public void contactsSelected(Collection<ContactId> contacts) {
		super.contactsSelected(contacts);
		showNextFragment(getMessageFragment());
	}

	abstract BaseMessageFragment getMessageFragment();

	@UiThread
	@Override
	public boolean onButtonClick(String message) {
		share(contacts, message);
		setResult(RESULT_OK);
		supportFinishAfterTransition();
		return true;
	}

	abstract void share(Collection<ContactId> contacts, String msg);

}
