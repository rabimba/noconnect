package org.chagolchana.noconnect.android.login;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.noconnect.android.controller.handler.ResultHandler;

@NotNullByDefault
public interface SetupController {

	float estimatePasswordStrength(String password);

	void storeAuthorInfo(String nickname, String password,
			ResultHandler<Void> resultHandler);

}
