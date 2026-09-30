package dev.smirg.url_shortener;

import dev.smirg.url_shortener.util.Base62Encoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base62EncoderTest {

    private final Base62Encoder encoder = new Base62Encoder();

    @Test
    void encodeThenDecodeShouldReturnOriginal() {
        long original = 12345L;
        String encoded = encoder.encode(original);
        long decoded = encoder.decode(encoded);
        assertEquals(original, decoded);
    }

    @Test
    void encodeProducesDifferentValuesForDifferentInputs() {
        assertNotEquals(encoder.encode(1L), encoder.encode(2L));
    }

    @Test
    void encodeIsNotEmpty() {
        assertFalse(encoder.encode(999L).isEmpty());
    }
}
