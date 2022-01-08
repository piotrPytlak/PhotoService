package pl.pytlak.photoart.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateObj {

    String rateCategory;

    Long id;

    Float value;
}
