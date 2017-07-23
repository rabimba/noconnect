package org.chagolchana.chagol.api.plugin.duplex;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.PluginCallback;

/**
 * An interface for handling connections created by a duplex transport plugin.
 */
@NotNullByDefault
public interface DuplexPluginCallback extends PluginCallback {

	void incomingConnectionCreated(DuplexTransportConnection d);

	void outgoingConnectionCreated(ContactId c, DuplexTransportConnection d);
}
