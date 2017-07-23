package org.chagolchana.noconnect.android.sharing;

import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.controller.ActivityLifecycleController;
import org.chagolchana.noconnect.android.controller.handler.ExceptionHandler;
import org.chagolchana.noconnect.android.controller.handler.ResultExceptionHandler;
import org.chagolchana.noconnect.api.sharing.InvitationItem;

import java.util.Collection;

@NotNullByDefault
public interface InvitationController<I extends InvitationItem>
		extends ActivityLifecycleController {

	void loadInvitations(boolean clear,
			ResultExceptionHandler<Collection<I>, DbException> handler);

	void respondToInvitation(I item, boolean accept,
			ExceptionHandler<DbException> handler);

	interface InvitationListener {

		void loadInvitations(boolean clear);

	}

}
