package org.chagolchana.chagol.api.transport;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.plugin.TransportId;

public class StreamContext {

	private final ContactId contactId;
	private final TransportId transportId;
	private final SecretKey tagKey, headerKey;
	private final long streamNumber;

	public StreamContext(ContactId contactId, TransportId transportId,
			SecretKey tagKey, SecretKey headerKey, long streamNumber) {
		this.contactId = contactId;
		this.transportId = transportId;
		this.tagKey = tagKey;
		this.headerKey = headerKey;
		this.streamNumber = streamNumber;
	}

	public ContactId getContactId() {
		return contactId;
	}

	public TransportId getTransportId() {
		return transportId;
	}

	public SecretKey getTagKey() {
		return tagKey;
	}

	public SecretKey getHeaderKey() {
		return headerKey;
	}

	public long getStreamNumber() {
		return streamNumber;
	}
}
