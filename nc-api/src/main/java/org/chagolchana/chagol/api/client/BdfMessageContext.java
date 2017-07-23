package org.chagolchana.chagol.api.client;

import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class BdfMessageContext {

	private final BdfDictionary dictionary;
	private final Collection<MessageId> dependencies;

	public BdfMessageContext(BdfDictionary dictionary,
			Collection<MessageId> dependencies) {
		this.dictionary = dictionary;
		this.dependencies = dependencies;
	}

	public BdfMessageContext(BdfDictionary dictionary) {
		this(dictionary, Collections.<MessageId>emptyList());
	}

	public BdfDictionary getDictionary() {
		return dictionary;
	}

	public Collection<MessageId> getDependencies() {
		return dependencies;
	}
}
