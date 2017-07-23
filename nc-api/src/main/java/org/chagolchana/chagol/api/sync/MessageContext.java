package org.chagolchana.chagol.api.sync;

import org.chagolchana.chagol.api.db.Metadata;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class MessageContext {

	private final Metadata metadata;
	private final Collection<MessageId> dependencies;

	public MessageContext(Metadata metadata,
			Collection<MessageId> dependencies) {
		this.metadata = metadata;
		this.dependencies = dependencies;
	}

	public MessageContext(Metadata metadata) {
		this(metadata, Collections.<MessageId>emptyList());
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Collection<MessageId> getDependencies() {
		return dependencies;
	}
}
