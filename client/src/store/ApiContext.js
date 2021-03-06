import {createContext} from "react";
import imageCompression from 'browser-image-compression';

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

export default function ApiContext({children}) {
    const login = async (email, password) => {
        const credentials = {
            email: email,
            password: password,
        };


        // const handleCompressedUpload = (e) => {
        //     const image = e.target.files[0];
        //     new Compressor(image, {
        //         quality: 0.7, // 0.6 can also be used, but its not recommended to go below.
        //         success: (compressedResult) => {
        //
        //         },
        //     });
        // };

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

    const editAboutMe = async (content) => {

        const data = {
            content: content
        }

        const body = {
            method: "POST",
            headers: header,
            credentials: "include",
            body: JSON.stringify(data),
        };

        const response = await fetch(serverUrl + "user/editAboutMe", body);

        return response.ok ? response.json() : undefined;

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
            return {authority: null};
        }
    };

    const searchBar = async (param) => {
        const urlSearchParams = new URLSearchParams({username: param});

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

        if (response.ok) return response.json();
        else return [];
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

        if (response.ok) return response.json();
        else return [];

    };

    const uploadAvatar = async (doBeforeRequest, loadUser, fieldName, file, metadata, load, error, progress, abort, transfer, options) => {


        const currentFile = await doBeforeRequest() || file;
        const formData = new FormData();
        formData.append(fieldName, currentFile);

        const request = new XMLHttpRequest();
        request.open('POST', `${serverUrl}uploadAvatar`);
        request.withCredentials = true;


        request.upload.onprogress = (e) => {
            progress(e.lengthComputable, e.loaded, e.total);
        };

        request.onload = function () {
            if (request.status >= 200 && request.status < 300) {
                load(request.responseText);
                loadUser();
            } else error('Server error')
        };

        request.send(formData);


        return {
            abort: () => {
                request.abort();
                abort();
            },
        };
    };


    const albumPhotosPull = async (albumId, userId, lastPhotoId) => {

        const urlPullPhotos = new URLSearchParams({
            albumId: albumId,
            userId: userId,
            lastPhotoId: lastPhotoId
        });

        const body = {
            method: "GET",
            headers: header,
            credentials: "include",
        };

        const response = await fetch(
            serverUrl + "albumPhotosPull?" + urlPullPhotos,
            body
        );

        if (response.ok)
            return response.json()
        else return []

    }


    const uploadBackGround = async (doBeforeRequest, loadUser, fieldName, file, metadata, load, error, progress, abort, transfer, options) => {


        const currentFile = await doBeforeRequest() || file;
        const formData = new FormData();
        formData.append(fieldName, currentFile);

        const request = new XMLHttpRequest();
        request.open('POST', `${serverUrl}uploadBackground`);
        request.withCredentials = true;


        request.upload.onprogress = (e) => {
            progress(e.lengthComputable, e.loaded, e.total);
        };

        request.onload = function () {
            if (request.status >= 200 && request.status < 300) {
                load(request.responseText);
                loadUser();
            } else error('Server error')
        };

        request.send(formData);


        return {
            abort: () => {
                request.abort();
                abort();
            },
        };
    };

    return (
        <apiContext.Provider
            value={{
                albumPhotosPull,
                photosInAlbum,
                userAlbumsWithThumbnail,
                uploadBackGround,
                uploadAvatar,
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
                editAboutMe
            }}
        >
            {children}
        </apiContext.Provider>
    );
}
