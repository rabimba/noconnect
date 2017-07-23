package org.chagolchana.chagol.plugin.modem;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
interface ModemFactory {

	Modem createModem(Modem.Callback callback, String portName);
}
