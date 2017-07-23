package org.chagolchana.noconnect.android.login;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.controller.ConfigController;
import org.chagolchana.noconnect.android.controller.handler.ResultHandler;

@NotNullByDefault
public interface PasswordController extends ConfigController {

	void validatePassword(String password,
			ResultHandler<Boolean> resultHandler);

	void changePassword(String password, String newPassword,
			ResultHandler<Boolean> resultHandler);
}
