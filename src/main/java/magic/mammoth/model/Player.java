package magic.mammoth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Player {

    private final String name;

    @JsonIgnore
    private final GeneratedKey apiKey = new GeneratedKey(24);
}
