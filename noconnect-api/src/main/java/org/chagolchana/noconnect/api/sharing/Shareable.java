package org.chagolchana.noconnect.api.sharing;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.sync.Group;
import org.chagolchana.chagol.api.sync.GroupId;

@NotNullByDefault
public interface Shareable {

	GroupId getId();

	Group getGroup();

	String getName();

}
