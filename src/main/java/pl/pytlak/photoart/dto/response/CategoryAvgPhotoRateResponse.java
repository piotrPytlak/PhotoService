package pl.pytlak.photoart.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAvgPhotoRateResponse {

    String describe;

    String title;

    String camera;

    String model;

    String ISO;

    Long photoId;

    Float creativityAvg;

    Float lightingAvg;

    Float originalityAvg;

    Float qualityAvg;

    Float artisticImpressionsAvg;

}
