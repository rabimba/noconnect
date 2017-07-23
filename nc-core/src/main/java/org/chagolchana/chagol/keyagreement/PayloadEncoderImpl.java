package org.chagolchana.chagol.keyagreement;

import org.chagolchana.chagol.api.data.BdfWriter;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.keyagreement.Payload;
import org.chagolchana.chagol.api.keyagreement.PayloadEncoder;
import org.chagolchana.chagol.api.keyagreement.TransportDescriptor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

import static org.chagolchana.chagol.api.keyagreement.KeyAgreementConstants.PROTOCOL_VERSION;

@Immutable
@NotNullByDefault
class PayloadEncoderImpl implements PayloadEncoder {

	private final BdfWriterFactory bdfWriterFactory;

	@Inject
	PayloadEncoderImpl(BdfWriterFactory bdfWriterFactory) {
		this.bdfWriterFactory = bdfWriterFactory;
	}

	@Override
	public byte[] encode(Payload p) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BdfWriter w = bdfWriterFactory.createWriter(out);
		try {
			w.writeListStart(); // Payload start
			w.writeLong(PROTOCOL_VERSION);
			w.writeRaw(p.getCommitment());
			for (TransportDescriptor d : p.getTransportDescriptors())
				w.writeList(d.getDescriptor());
			w.writeListEnd(); // Payload end
		} catch (IOException e) {
			// Shouldn't happen with ByteArrayOutputStream
			throw new AssertionError(e);
		}
		return out.toByteArray();
	}
}
