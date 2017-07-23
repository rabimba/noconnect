package org.chagolchana.chagol.sync;

interface ThrowingRunnable<T extends Throwable> {

	void run() throws T;
}
