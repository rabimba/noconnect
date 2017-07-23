package org.chagolchana.chagol.api.data;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.OutputStream;

@NotNullByDefault
public interface BdfWriterFactory {

	BdfWriter createWriter(OutputStream out);
}
