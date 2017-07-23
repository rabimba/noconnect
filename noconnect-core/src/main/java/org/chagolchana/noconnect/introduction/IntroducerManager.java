package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.api.Bytes;
import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.Contact;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.data.BdfList;
import org.chagolchana.chagol.api.db.DbException;
import org.chagolchana.chagol.api.db.Transaction;
import org.chagolchana.chagol.api.event.Event;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.Message;
import org.chagolchana.chagol.api.sync.MessageId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.util.StringUtils;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static java.util.logging.Level.WARNING;
import static org.chagolchana.noconnect.api.introduction.IntroducerProtocolState.PREPARE_REQUESTS;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.AUTHOR_ID_1;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.AUTHOR_ID_2;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.CONTACT_1;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.CONTACT_2;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.CONTACT_ID_1;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.CONTACT_ID_2;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.GROUP_ID_1;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.GROUP_ID_2;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MAX_INTRODUCTION_MESSAGE_LENGTH;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MESSAGE_TIME;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.MSG;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.PUBLIC_KEY1;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.PUBLIC_KEY2;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.ROLE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.ROLE_INTRODUCER;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.SESSION_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.STATE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.STORAGE_ID;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_ABORT;
import static org.chagolchana.noconnect.api.introduction.IntroductionConstants.TYPE_REQUEST;

@Immutable
@NotNullByDefault
class IntroducerManager {

	private static final Logger LOG =
			Logger.getLogger(IntroducerManager.class.getName());

	private final MessageSender messageSender;
	private final ClientHelper clientHelper;
	private final Clock clock;
	private final CryptoComponent cryptoComponent;
	private final IntroductionGroupFactory introductionGroupFactory;

	@Inject
	IntroducerManager(MessageSender messageSender, ClientHelper clientHelper,
			Clock clock, CryptoComponent cryptoComponent,
			IntroductionGroupFactory introductionGroupFactory) {

		this.messageSender = messageSender;
		this.clientHelper = clientHelper;
		this.clock = clock;
		this.cryptoComponent = cryptoComponent;
		this.introductionGroupFactory = introductionGroupFactory;
	}

	public BdfDictionary initialize(Transaction txn, Contact c1, Contact c2)
			throws FormatException, DbException {

		// create local message to keep engine state
		long now = clock.currentTimeMillis();
		Bytes salt = new Bytes(new byte[64]);
		cryptoComponent.getSecureRandom().nextBytes(salt.getBytes());

		Message m = clientHelper.createMessage(
				introductionGroupFactory.createLocalGroup().getId(), now,
				BdfList.of(salt));
		MessageId sessionId = m.getId();

		Group g1 = introductionGroupFactory.createIntroductionGroup(c1);
		Group g2 = introductionGroupFactory.createIntroductionGroup(c2);

		BdfDictionary d = new BdfDictionary();
		d.put(SESSION_ID, sessionId);
		d.put(STORAGE_ID, sessionId);
		d.put(STATE, PREPARE_REQUESTS.getValue());
		d.put(ROLE, ROLE_INTRODUCER);
		d.put(GROUP_ID_1, g1.getId());
		d.put(GROUP_ID_2, g2.getId());
		d.put(CONTACT_1, c1.getAuthor().getName());
		d.put(CONTACT_2, c2.getAuthor().getName());
		d.put(CONTACT_ID_1, c1.getId().getInt());
		d.put(CONTACT_ID_2, c2.getId().getInt());
		d.put(AUTHOR_ID_1, c1.getAuthor().getId());
		d.put(AUTHOR_ID_2, c2.getAuthor().getId());

		// save local state to database
		clientHelper.addLocalMessage(txn, m, d, false);

		return d;
	}

	void makeIntroduction(Transaction txn, Contact c1, Contact c2,
			@Nullable String msg, long timestamp)
			throws DbException, FormatException {

		// TODO check for existing session with those contacts?
		//      deny new introduction under which conditions?

		// initialize engine state
		BdfDictionary localState = initialize(txn, c1, c2);

		// define action
		BdfDictionary localAction = new BdfDictionary();
		localAction.put(TYPE, TYPE_REQUEST);
		if (!StringUtils.isNullOrEmpty(msg)) {
			int msgLength = StringUtils.toUtf8(msg).length;
			if (msgLength > MAX_INTRODUCTION_MESSAGE_LENGTH)
				throw new IllegalArgumentException();
			localAction.put(MSG, msg);
		}
		localAction.put(PUBLIC_KEY1, c1.getAuthor().getPublicKey());
		localAction.put(PUBLIC_KEY2, c2.getAuthor().getPublicKey());
		localAction.put(MESSAGE_TIME, timestamp);

		// start engine and process its state update
		IntroducerEngine engine = new IntroducerEngine();
		processStateUpdate(txn,
				engine.onLocalAction(localState, localAction));
	}

	public void incomingMessage(Transaction txn, BdfDictionary state,
			BdfDictionary message) throws DbException, FormatException {

		IntroducerEngine engine = new IntroducerEngine();
		processStateUpdate(txn,
				engine.onMessageReceived(state, message));
	}

	private void processStateUpdate(Transaction txn,
			IntroducerEngine.StateUpdate<BdfDictionary, BdfDictionary>
					result) throws DbException, FormatException {

		// save new local state
		MessageId storageId =
				new MessageId(result.localState.getRaw(STORAGE_ID));
		clientHelper.mergeMessageMetadata(txn, storageId, result.localState);

		// send messages
		for (BdfDictionary d : result.toSend) {
			messageSender.sendMessage(txn, d);
		}

		// broadcast events
		for (Event event : result.toBroadcast) {
			txn.attach(event);
		}
	}

	public void abort(Transaction txn, BdfDictionary state) {
		IntroducerEngine engine = new IntroducerEngine();
		BdfDictionary localAction = new BdfDictionary();
		localAction.put(TYPE, TYPE_ABORT);
		try {
			processStateUpdate(txn,
					engine.onLocalAction(state, localAction));
		} catch (DbException e) {
			if (LOG.isLoggable(WARNING)) LOG.log(WARNING, e.toString(), e);
		} catch (IOException e) {
			if (LOG.isLoggable(WARNING)) LOG.log(WARNING, e.toString(), e);
		}
	}

}
