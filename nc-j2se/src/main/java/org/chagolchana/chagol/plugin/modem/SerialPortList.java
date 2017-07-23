package org.chagolchana.chagol.plugin.modem;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
interface SerialPortList {

	String[] getPortNames();
}
