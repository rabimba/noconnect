package org.chagolchana.chagol.properties;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.client.BdfMessageValidator;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.system.Clock;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.plugin.TransportId.MAX_TRANSPORT_ID_LENGTH;
import static org.chagolchana.chagol.api.properties.TransportPropertyConstants.MAX_PROPERTIES_PER_TRANSPORT;
import static org.chagolchana.chagol.api.properties.TransportPropertyConstants.MAX_PROPERTY_LENGTH;
import static org.chagolchana.chagol.util.ValidationUtils.checkLength;
import static org.chagolchana.chagol.util.ValidationUtils.checkSize;

@Immutable
@NotNullByDefault
class TransportPropertyValidator extends BdfMessageValidator {

	TransportPropertyValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock) {
		super(clientHelper, metadataEncoder, clock);
	}

	@Override
	protected BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws FormatException {
		// Transport ID, version, properties
		checkSize(body, 3);
		// Transport ID
		String transportId = body.getString(0);
		checkLength(transportId, 1, MAX_TRANSPORT_ID_LENGTH);
		// Version
		long version = body.getLong(1);
		if (version < 0) throw new FormatException();
		// Properties
		BdfDictionary dictionary = body.getDictionary(2);
		checkSize(dictionary, 0, MAX_PROPERTIES_PER_TRANSPORT);
		for (String key : dictionary.keySet()) {
			checkLength(key, 0, MAX_PROPERTY_LENGTH);
			String value = dictionary.getString(key);
			checkLength(value, 0, MAX_PROPERTY_LENGTH);
		}
		// Return the metadata
		BdfDictionary meta = new BdfDictionary();
		meta.put("transportId", transportId);
		meta.put("version", version);
		meta.put("local", false);
		return new BdfMessageContext(meta);
	}
}
