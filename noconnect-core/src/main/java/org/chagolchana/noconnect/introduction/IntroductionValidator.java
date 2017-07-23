package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.BdfMessageContext;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.client.SessionId;
import org.chagolchana.noconnect.client.BdfQueueMessageValidator;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_AUTHOR_NAME_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_PUBLIC_KEY_LENGTH;
import static org.chagolchana.chagol.api.identity.AuthorConstants.MAX_SIGNATURE_LENGTH;
import static org.chagolchana.chagol.api.plugin.TransportId.MAX_TRANSPORT_ID_LENGTH;
import static org.chagolchana.chagol.api.properties.TransportPropertyConstants.MAX_PROPERTIES_PER_TRANSPORT;
import static org.chagolchana.chagol.api.properties.TransportPropertyConstants.MAX_PROPERTY_LENGTH;
import static org.chagolchana.chagol.util.ValidationUtils.checkLength;
import static org.chagolchana.chagol.util.ValidationUtils.checkSize;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.ACCEPT;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.E_PUBLIC_KEY;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.GROUP_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MAC;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MAC_LENGTH;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MAX_INTRODUCTION_MESSAGE_LENGTH;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MESSAGE_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MESSAGE_TIME;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MSG;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.NAME;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.PUBLIC_KEY;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.SESSION_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.SIGNATURE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TIME;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TRANSPORT;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_ABORT;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_ACK;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_REQUEST;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_RESPONSE;

@Immutable
@NotNullByDefault
class IntroductionValidator extends BdfQueueMessageValidator {

	IntroductionValidator(ClientHelper clientHelper,
			MetadataEncoder metadataEncoder, Clock clock) {
		super(clientHelper, metadataEncoder, clock);
	}

	@Override
	protected BdfMessageContext validateMessage(Message m, Group g,
			BdfList body) throws FormatException {

		BdfDictionary d;
		long type = body.getLong(0);
		byte[] id = body.getRaw(1);
		checkLength(id, SessionId.LENGTH);

		if (type == TYPE_REQUEST) {
			d = validateRequest(body);
		} else if (type == TYPE_RESPONSE) {
			d = validateResponse(body);
		} else if (type == TYPE_ACK) {
			d = validateAck(body);
		} else if (type == TYPE_ABORT) {
			d = validateAbort(body);
		} else {
			throw new FormatException();
		}

		d.put(TYPE, type);
		d.put(SESSION_ID, id);
		d.put(GROUP_ID, m.getGroupId());
		d.put(MESSAGE_ID, m.getId());
		d.put(MESSAGE_TIME, m.getTimestamp());
		return new BdfMessageContext(d);
	}

	private BdfDictionary validateRequest(BdfList message)
			throws FormatException {

		checkSize(message, 4, 5);

		// parse contact name
		String name = message.getString(2);
		checkLength(name, 1, MAX_AUTHOR_NAME_LENGTH);

		// parse contact's public key
		byte[] key = message.getRaw(3);
		checkLength(key, 0, MAX_PUBLIC_KEY_LENGTH);

		// parse (optional) message
		String msg = null;
		if (message.size() == 5) {
			msg = message.getString(4);
			checkLength(msg, 0, MAX_INTRODUCTION_MESSAGE_LENGTH);
		}

		// Return the metadata
		BdfDictionary d = new BdfDictionary();
		d.put(NAME, name);
		d.put(PUBLIC_KEY, key);
		if (msg != null) {
			d.put(MSG, msg);
		}
		return d;
	}

	private BdfDictionary validateResponse(BdfList message)
			throws FormatException {

		checkSize(message, 3, 6);

		// parse accept/decline
		boolean accept = message.getBoolean(2);

		long time = 0;
		byte[] pubkey = null;
		BdfDictionary tp = new BdfDictionary();
		if (accept) {
			checkSize(message, 6);

			// parse timestamp
			time = message.getLong(3);

			// parse ephemeral public key
			pubkey = message.getRaw(4);
			checkLength(pubkey, 0, MAX_PUBLIC_KEY_LENGTH);

			// parse transport properties
			tp = message.getDictionary(5);
			if (tp.size() < 1) throw new FormatException();
			for (String tId : tp.keySet()) {
				checkLength(tId, 1, MAX_TRANSPORT_ID_LENGTH);
				BdfDictionary tProps = tp.getDictionary(tId);
				checkSize(tProps, 0, MAX_PROPERTIES_PER_TRANSPORT);
				for (String propId : tProps.keySet()) {
					checkLength(propId, 0, MAX_PROPERTY_LENGTH);
					String prop = tProps.getString(propId);
					checkLength(prop, 0, MAX_PROPERTY_LENGTH);
				}
			}
		} else {
			checkSize(message, 3);
		}

		// Return the metadata
		BdfDictionary d = new BdfDictionary();
		d.put(ACCEPT, accept);
		if (accept) {
			d.put(TIME, time);
			d.put(E_PUBLIC_KEY, pubkey);
			d.put(TRANSPORT, tp);
		}
		return d;
	}

	private BdfDictionary validateAck(BdfList message) throws FormatException {
		checkSize(message, 4);

		byte[] mac = message.getRaw(2);
		checkLength(mac, 1, MAC_LENGTH);

		byte[] sig = message.getRaw(3);
		checkLength(sig, 1, MAX_SIGNATURE_LENGTH);

		// Return the metadata
		BdfDictionary d = new BdfDictionary();
		d.put(MAC, mac);
		d.put(SIGNATURE, sig);
		return d;
	}

	private BdfDictionary validateAbort(BdfList message)
			throws FormatException {

		checkSize(message, 2);

		// Return the metadata
		return new BdfDictionary();
	}
}
