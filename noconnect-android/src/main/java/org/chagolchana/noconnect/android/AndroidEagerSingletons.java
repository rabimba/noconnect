package org.chagolchana.noconnect.android;

class AndroidEagerSingletons {

	static void initEagerSingletons(AndroidComponent c) {
		c.inject(new AppModule.EagerSingletons());
	}
}
