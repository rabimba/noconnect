package org.chagolchana.noconnect.sharing;

import org.chagolchana.chagol.api.data.BdfDictionary;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
interface SessionEncoder {

	BdfDictionary encodeSession(Session s);

}
