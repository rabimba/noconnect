package org.chagolchana.noconnect.privategroup.invitation;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
interface ProtocolEngineFactory {

	ProtocolEngine<CreatorSession> createCreatorEngine();

	ProtocolEngine<InviteeSession> createInviteeEngine();

	ProtocolEngine<PeerSession> createPeerEngine();
}
