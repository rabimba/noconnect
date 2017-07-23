package org.chagolchana.chagol.api.reliability;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

@NotNullByDefault
public interface ReliabilityLayerFactory {

	/**
	 * Returns a reliability layer that writes to the given lower layer.
	 */
	ReliabilityLayer createReliabilityLayer(WriteHandler writeHandler);
}
