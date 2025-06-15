package magic.mammoth.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Cell {

    private final List<CellLimit> limits;

    private CellContent content;
}