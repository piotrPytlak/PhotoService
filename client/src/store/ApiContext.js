import { createContext } from "react";

export const apiContext = createContext(null);

let header = {
  "Access-Control-Allow-Credentials": "true",
  "Access-Control-Allow-Origin": "http://localhost:3000/",
  "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
  "Content-Type": "application/json",
};

const serverUrl = "http://localhost:8080/";

const PermitType = {
  AUTHENTICATED: false,
  NO_AUTHENTICATED: true,
};

export default function ApiContext({ children }) {
  const login = async (email, password) => {
    const credentials = {
      email: email,
      password: password,
    };

    const body = {
      method: "POST",
      headers: header,
      credentials: "include",
      body: btoa(JSON.stringify(credentials)),
    };

    const response = await fetch(serverUrl + "login", body);
    return response.status;
  };

  const userPhotos = async (param) => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "userPhotos/" + param, body);

    if (response.ok) {
      return response.json();
    } else {
      return [];
    }
  };

  const userAllPhotos = async (param) => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "userAllPhotos/" + param, body);

    if (response.ok) {
      return response.json();
    } else {
      return [];
    }
  };

  const userAlbums = async (param) => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "userAlbums/" + param, body);

    if (response.ok) {
      return response.json();
    } else return [];
  };

  const currentUserAlbums = async () => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "currentUserAlbums", body);

    if (response.ok) return response.json();
    else console.log("cos poszlo nie tak ");
  };

  const addAlbum = async (albumObject) => {
    const body = {
      method: "POST",
      headers: header,
      credentials: "include",
      body: JSON.stringify(albumObject),
    };

    const response = await fetch(serverUrl + "addAlbum", body);

    return response.status;
  };

  const userPhotosLoad = async (userId, lastPhotoId) => {
    const urlUserId = new URLSearchParams({
      userId: userId,
      lastPhotoId: lastPhotoId,
    });

    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + "userPhotosPull?" + urlUserId,
      body
    );

    if (response.ok) {
      return response.json();
    } else {
      return [];
    }
  };

  const register = async (registerObject) => {
    const body = {
      method: "POST",
      headers: header,
      credentials: "include",
      body: JSON.stringify(registerObject),
    };

    return await fetch(serverUrl + "register", body);
  };

  const logout = async () => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    return await fetch(serverUrl + "logout", body);
  };

  const loginStatus = async () => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "check", body);

    if (response.ok) {
      return response.json();
    } else {
      return { authority: null };
    }
  };

  const searchBar = async (param) => {
    const urlSearchParams = new URLSearchParams({ username: param });

    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + "user/search?" + urlSearchParams,
      body
    );

    if (response.ok) return response.json();
    else throw new Error("Error!");
  };

  const userDetails = async (param) => {
    const body = {
      method: "GET",
      header: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + `user/getUserInformation/${param}`,
      body
    );

    if (response.ok) return response.json();
    else throw new Error("Error!");
  };

  const currentUserDetails = async () => {
    const body = {
      method: "GET",
      header: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + `user/getCurrentUserInformation`,
      body
    );

    if (response.ok) return response.json();
    else return undefined;
  };

  const addPhoto = async (form) => {
    const body = {
      method: "POST",
      headers: header,
      credentials: "include",
      body: JSON.stringify(form),
    };

    const response = await fetch(serverUrl + "addPhoto", body);

    return response.status;
  };

  const putRate = async (photoId, rateList) => {
    const dataBody = {
      photoId: photoId,
      rateObjList: rateList,
    };

    const body = {
      method: "PUT",
      headers: header,
      credentials: "include",
      body: JSON.stringify(dataBody),
    };

    const response = await fetch(serverUrl + "addRate", body);

    return response.status;
  };

  const getPhotoInformation = async (photoId) => {
    const body = {
      method: "GET",
      header: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + `getPhotoInformation/${photoId}`,
      body
    );

    if (response.ok) return response.json();
    else throw new Error("Error!");
  };

  const addComment = async (commentObject) => {
    const body = {
      method: "POST",
      headers: header,
      credentials: "include",
      body: JSON.stringify(commentObject),
    };

    return await fetch(serverUrl + "addComment", body);
  };

  const photoComments = async (photoId) => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(serverUrl + "getComments/" + photoId, body);

    if (response.ok) return response.json();
    else return [];
  };

  const photoLoadComments = async (photoId, lastCommentId) => {
    const urlPhotoId = new URLSearchParams({
      photoId: photoId,
      lastCommentId: lastCommentId,
    });

    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + "pullComments?" + urlPhotoId,
      body
    );

    return response.json();
  };

  const userAlbumsWithThumbnail = async (userId) => {
    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + "userAlbumsWithThumbnail/" + userId,
      body
    );

    if (response.ok) response.json();
  };

  const photosInAlbum = async (userId, albumId) => {
    const dataBody = new URLSearchParams({
      userId: userId,
      albumId: albumId,
    });

    const body = {
      method: "GET",
      headers: header,
      credentials: "include",
    };

    const response = await fetch(
      serverUrl + "photosInUserAlbum?" + dataBody,
      body
    );
  };

  return (
    <apiContext.Provider
      value={{
        addAlbum,
        currentUserAlbums,
        photoLoadComments,
        photoComments,
        getPhotoInformation,
        putRate,
        userAllPhotos,
        addPhoto,
        login,
        register,
        searchBar,
        PermitType,
        userAlbums,
        serverUrl,
        userPhotos,
        userPhotosLoad,
        loginStatus,
        userDetails,
        logout,
        currentUserDetails,
        addComment,
      }}
    >
      {children}
    </apiContext.Provider>
  );
}
