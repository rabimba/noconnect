package org.chagolchana.chagol.api.data;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.db.Metadata;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface MetadataParser {

	BdfDictionary parse(Metadata m) throws FormatException;
}
