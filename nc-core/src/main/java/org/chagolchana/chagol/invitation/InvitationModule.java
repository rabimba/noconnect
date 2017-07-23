package org.chagolchana.chagol.invitation;

import org.chagolchana.chagol.api.invitation.InvitationTaskFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class InvitationModule {

	@Provides
	InvitationTaskFactory provideInvitationTaskFactory(
			InvitationTaskFactoryImpl invitationTaskFactory) {
		return invitationTaskFactory;
	}
}
