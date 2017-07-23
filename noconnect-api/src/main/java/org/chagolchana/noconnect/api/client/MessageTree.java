package org.chagolchana.noconnect.api.client;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.MessageId;

import java.util.Collection;
import java.util.Comparator;

import javax.annotation.Nullable;

@NotNullByDefault
public interface MessageTree<T extends MessageTree.MessageNode> {

	void add(Collection<T> nodes);

	void add(T node);

	void setComparator(Comparator<T> comparator);

	void clear();

	Collection<T> depthFirstOrder();

	@NotNullByDefault
	interface MessageNode {

		MessageId getId();

		@Nullable
		MessageId getParentId();

		void setLevel(int level);

		long getTimestamp();
	}

}
