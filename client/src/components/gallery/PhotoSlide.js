import React, {useContext, useEffect, useRef, useState} from "react";

import "react-lightbox-pack/dist/index.css";
import {apiContext} from "../../store/ApiContext";
import {LightBox} from "react-lightbox-pack";
import {Redirect, useHistory, useParams} from "react-router-dom";
import "./styleGallery.css";

export function PhotoSlide() {
    // State
    const [toggle, setToggle] = useState(false);
    const [sIndex, setSIndex] = useState(0);
    const {serverUrl, userAllPhotos} = useContext(apiContext);
    const [photos, setPhotos] = useState([]);
    const [dataPhoto, setDataPhoto] = useState([]);
    const lightBox = useRef();
    const history = useHistory();
    const {userId, photoId} = useParams();
    const [isClosed, setIsClosed] = useState(false);

    const onChangeIndex = (x) => {
        const newIndex = typeof x === "function" ? x(sIndex) : sIndex;

        const newPhoto = dataPhoto[newIndex];
        let currentIndex = photos.indexOf(newPhoto);
        currentIndex = currentIndex < 2 ? 2 : currentIndex;
        currentIndex = photos.length - currentIndex < 3 ? photos.length - 3 : currentIndex;
        const indexStart = currentIndex - 2 > 0 ? currentIndex - 2 : 0;

        const indexEnd = indexStart + 5 < photos.length ? indexStart + 5 : photos.length;
        const newDataPhoto = photos.slice(indexStart, indexEnd);
        history.push(`/photo/gallery/${userId}/${newPhoto.id}`);

        JSON.stringify(dataPhoto) === JSON.stringify(newDataPhoto) &&
        setSIndex(newIndex);
        setDataPhoto(newDataPhoto);
    };

    const onCloseHandler = () => {
        setIsClosed(true);
    };

    useEffect(
        () =>
            userAllPhotos(userId).then((x) => {
                const photoList = x.map((photo) => {
                    return {
                        id: photo.photoId,
                        image: `${serverUrl}images/${photo.photoPath}`,
                        thumbnail:
                            "https://lh3.googleusercontent.com/proxy/qNxKnVlotKLm8f251E4w0OV9G-2F3RqsUv29FtDek6m30M151m82n3by5r9ax9QWPADTjLg0OR30muBcxxUk-RFSePIf2SM",
                        title: photo.photoTitle,
                        description: photo.Description,
                    };
                });
                setPhotos(photoList);

                const selectedPhoto = photoList.find((x) => x.id == photoId);
                let currentIndex = photoList.indexOf(selectedPhoto);
                currentIndex = currentIndex < 2 ? 2 : currentIndex;
                currentIndex = photoList.length - currentIndex < 3 ? photoList.length - 3 : currentIndex;
                const indexStart = currentIndex - 2 > 0 ? currentIndex - 2 : 0;
                const indexEnd =
                    indexStart + 5 < photoList.length
                        ? indexStart + 5
                        : photoList.length;
                const newDataPhoto = photoList.slice(indexStart, indexEnd);

                setDataPhoto(newDataPhoto);

                setToggle(true);
                setSIndex(newDataPhoto.indexOf(selectedPhoto));
            }),
        [userId]
    );

    useEffect(() => {
        const thumbnail = lightBox.current?.firstChild?.lastChild;
        thumbnail?.classList.add("photos-thumbnail");
        const photoContainer = thumbnail?.previousSibling;
        photoContainer?.classList.add("photo-container");
        photoContainer?.firstChild.classList.add("photo");
    });
    console.log(dataPhoto.length);
    return (
        dataPhoto.length > 0 &&
        <div ref={lightBox} className={"slideContainer"}>
            <LightBox
                state={toggle}
                event={onCloseHandler}
                data={dataPhoto}
                imageWidth="60vw"
                imageHeight="70vh"
                thumbnailHeight={50}
                thumbnailWidth={50}
                setImageIndex={onChangeIndex}
                imageIndex={sIndex}
            />
            {isClosed && <Redirect to={`/user/${userId}`}/>}
        </div>
    );
}
