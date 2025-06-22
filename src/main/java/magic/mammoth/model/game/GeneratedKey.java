package magic.mammoth.model.game;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Random;

@EqualsAndHashCode
@AllArgsConstructor
public class GeneratedKey {

    private static final String charSet = "0123456789abcdef";
    private static final Random random = new Random(System.nanoTime());

    @JsonValue
    private final String value;

    public GeneratedKey() {
        this(6);
    }

    public GeneratedKey(int length) {
        this.value = random.ints(length, 0, 16)
                .mapToObj(charSet::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
