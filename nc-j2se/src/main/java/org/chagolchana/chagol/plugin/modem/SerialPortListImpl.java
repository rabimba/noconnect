package org.chagolchana.chagol.plugin.modem;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
class SerialPortListImpl implements SerialPortList {

	@Override
	public String[] getPortNames() {
		return jssc.SerialPortList.getPortNames();
	}
}
