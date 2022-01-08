package pl.pytlak.photoart.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddRateRequest {

    Long photoId;

    List<RateObj> rateObjList;
}
