package org.chagolchana.noconnect.android.sharing;

import android.content.Context;

import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.api.sharing.SharingInvitationItem;

import javax.inject.Inject;

import static org.chagolchana.noconnect.android.sharing.InvitationAdapter.InvitationClickListener;

@MethodsNotNullByDefault
@ParametersNotNullByDefault
public class BlogInvitationActivity
		extends InvitationActivity<SharingInvitationItem> {

	@Inject
	BlogInvitationController controller;

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	@Override
	protected InvitationController<SharingInvitationItem> getController() {
		return controller;
	}

	@Override
	protected InvitationAdapter<SharingInvitationItem, ?> getAdapter(
			Context ctx,
			InvitationClickListener<SharingInvitationItem> listener) {
		return new SharingInvitationAdapter(ctx, listener);
	}

	@Override
	protected int getAcceptRes() {
		return R.string.blogs_sharing_joined_toast;
	}

	@Override
	protected int getDeclineRes() {
		return R.string.blogs_sharing_declined_toast;
	}

}
