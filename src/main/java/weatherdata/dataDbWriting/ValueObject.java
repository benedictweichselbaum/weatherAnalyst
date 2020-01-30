package weatherdata.dataDbWriting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueObject {

    Double tempCurrent;
    Double tempMax;
    Double tempMin;
    Integer humidity;
    String description;
}
