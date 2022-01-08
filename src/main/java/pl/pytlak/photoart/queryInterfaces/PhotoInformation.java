package pl.pytlak.photoart.queryInterfaces;

public interface PhotoInformation {



    String getDescribe();

    String getTitle();

    String getCamera();

    String getModel();

    String getISO();

    String getExif();

    Long getPhotoId();

    Float getCreativitySUM();

    Float getLightingSUM();

    Float getOriginalitySUM();

    Float getQualitySUM();

    Float getArtisticImpressionsSUM();

    Float getCreativityCOUNT();

    Float getLightingCOUNT();

    Float getOriginalityCOUNT();

    Float getQualityCOUNT();

    Float getArtisticImpressionsCOUNT();


}
