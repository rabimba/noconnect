package org.chagolchana.chagol;

import org.chagolchana.chagol.plugin.AndroidPluginModule;
import org.chagolchana.chagol.system.AndroidSystemModule;

import dagger.Module;

@Module(includes = {
		AndroidPluginModule.class,
		AndroidSystemModule.class
})
public class BrambleAndroidModule {
}
