package org.chagolchana.chagol.api.plugin.simplex;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.PluginCallback;
import org.chagolchana.chagol.api.plugin.TransportConnectionReader;
import org.chagolchana.chagol.api.plugin.TransportConnectionWriter;

/**
 * An interface for handling readers and writers created by a simplex transport
 * plugin.
 */
@NotNullByDefault
public interface SimplexPluginCallback extends PluginCallback {

	void readerCreated(TransportConnectionReader r);

	void writerCreated(ContactId c, TransportConnectionWriter w);
}
