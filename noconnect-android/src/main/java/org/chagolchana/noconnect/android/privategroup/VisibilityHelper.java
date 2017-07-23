package org.chagolchana.noconnect.android.privategroup;

import android.content.Context;
import android.support.annotation.DrawableRes;

import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.api.privategroup.Visibility;

import static org.chagolchana.noconnect.api.privategroup.Visibility.INVISIBLE;

public class VisibilityHelper {

	public static String getVisibilityString(Context ctx, Visibility v,
			String contact) {
		switch (v) {
			case VISIBLE:
				return ctx.getString(R.string.groups_reveal_visible);
			case REVEALED_BY_US:
				return ctx.getString(
						R.string.groups_reveal_visible_revealed_by_us);
			case REVEALED_BY_CONTACT:
				return ctx.getString(
						R.string.groups_reveal_visible_revealed_by_contact,
						contact);
			case INVISIBLE:
				return ctx.getString(R.string.groups_reveal_invisible);
			default:
				throw new IllegalArgumentException("Unknown visibility");
		}
	}

	@DrawableRes
	public static int getVisibilityIcon(Visibility v) {
		if (v == INVISIBLE) {
			return R.drawable.ic_visibility_off;
		}
		return R.drawable.ic_visibility;
	}

}
