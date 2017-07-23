package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.FormatException;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.util.StringUtils;

@NotNullByDefault
class AsciiArmour {

	static String wrap(byte[] b, int lineLength) {
		String wrapped = StringUtils.toHexString(b);
		StringBuilder s = new StringBuilder();
		int length = wrapped.length();
		for (int i = 0; i < length; i += lineLength) {
			int end = Math.min(i + lineLength, length);
			s.append(wrapped.substring(i, end));
			s.append("\r\n");
		}
		return s.toString();
	}

	static byte[] unwrap(String s) throws FormatException {
		try {
			return StringUtils.fromHexString(s.replaceAll("[^0-9a-fA-F]", ""));
		} catch (IllegalArgumentException e) {
			throw new FormatException();
		}
	}
}
