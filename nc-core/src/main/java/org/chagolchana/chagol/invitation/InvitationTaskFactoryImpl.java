package org.chagolchana.chagol.invitation;

import org.chagolchana.chagol.api.contact.ContactExchangeTask;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfReaderFactory;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.invitation.InvitationTask;
import org.chagolchana.chagol.api.invitation.InvitationTaskFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.PluginManager;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class InvitationTaskFactoryImpl implements InvitationTaskFactory {

	private final CryptoComponent crypto;
	private final BdfReaderFactory bdfReaderFactory;
	private final BdfWriterFactory bdfWriterFactory;
	private final ContactExchangeTask contactExchangeTask;
	private final IdentityManager identityManager;
	private final PluginManager pluginManager;

	@Inject
	InvitationTaskFactoryImpl(CryptoComponent crypto,
			BdfReaderFactory bdfReaderFactory,
			BdfWriterFactory bdfWriterFactory,
			ContactExchangeTask contactExchangeTask,
			IdentityManager identityManager, PluginManager pluginManager) {
		this.crypto = crypto;
		this.bdfReaderFactory = bdfReaderFactory;
		this.bdfWriterFactory = bdfWriterFactory;
		this.contactExchangeTask = contactExchangeTask;
		this.identityManager = identityManager;
		this.pluginManager = pluginManager;
	}

	@Override
	public InvitationTask createTask(int localCode, int remoteCode) {
		return new ConnectorGroup(crypto, bdfReaderFactory, bdfWriterFactory,
				contactExchangeTask, identityManager, pluginManager,
				localCode, remoteCode);
	}
}
