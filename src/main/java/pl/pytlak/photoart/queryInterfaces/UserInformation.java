package pl.pytlak.photoart.queryInterfaces;


public interface UserInformation {

    Long getUserId();

    Integer getUserAge();

    String getUsername();

    String getFirstName();

    String getLastName();

    String getAboutMe();

    String getAvatarPath();

    String getBackgroundPath();

    Long getFollowers();

    Long getFollowing();

    Long getPhotoCount();

    Long getAlbumCount();

}
