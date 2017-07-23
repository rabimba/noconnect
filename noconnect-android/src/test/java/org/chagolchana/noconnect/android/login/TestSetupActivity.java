package org.chagolchana.noconnect.android.login;

/**
 * This class exposes the SetupController and offers the possibility to
 * override it.
 */
public class TestSetupActivity extends SetupActivity {

	SetupController getController() {
		return setupController;
	}

	void setController(SetupController setupController) {
		this.setupController = setupController;
	}
}
