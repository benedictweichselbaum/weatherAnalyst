package weatherdata.dataDbWriting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ValueObject {

    Double tempCurrent;
    Double tempMax;
    Double tempMin;
    Integer humidity;
    String description;
    Double vWind;
    String timestamp;
}
