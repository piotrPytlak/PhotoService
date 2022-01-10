import * as React from "react";
import {useCallback, useContext, useEffect, useState} from "react";
import {ImageList, ImageListItem, ImageListItemBar, Paper} from "@mui/material";
import {userContext} from "../../store/UserContext";
import {apiContext} from "../../store/ApiContext";
import {useHistory, useParams} from "react-router-dom";
import {IconButton, LinearProgress} from "@material-ui/core";

function InfoIcon() {
    return null;
}

export default function TabBarAlbumPhotos() {

    const {serverUrl, photosInAlbum, albumPhotosPull} = useContext(apiContext)
    const [photos, setPhotos] = useState([])
    const [load, setLoad] = useState(false)
    const history = useHistory();

    const {userId, albumId} = useParams();

    const scrollHandler = useCallback((event) => {

        if ((event.target.scrollHeight - event.target.scrollTop - event.target.offsetHeight) === 0 && photos.length >= 6 && !load) {
            albumPhotosPull(userId, albumId, (photos).at(-1).photoId)
                .then((x) => {
                    !!x.length && setPhotos([...photos, ...x])
                })
        }
    }, [photos, setPhotos, setLoad, load, albumPhotosPull])

    useEffect(() => {
        setLoad(true)
        photosInAlbum(userId, albumId).then(x => {
            setPhotos(x)
        }).finally(() => {
            setLoad(false)
        })
    }, [setPhotos, userId])

    const onClickPhoto = useCallback((photo) => history.push(`/photo/gallery/${userId}/${photo.photoId}/${albumId}`),
        [history, userId])

    return (
        <>
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'center',
                minHeight: 'calc(100vh - 392px)',
                height: 'auto',
                maxHeight: 'calc(100vh - 45px)'
            }}>
                <Paper
                    onScroll={scrollHandler}

                    style={{
                        width: '85%',
                        marginTop: '10px',
                        height: 'auto',
                        padding: '0 11px',
                        minHeight: 'calc(100vh - 410px)'
                    }}
                    sx={{width: 500, height: 450, overflowY: 'scroll'}}>

                        <ImageList style={{marginTop: '15px'}} variant="masonry" cols={4} gap={5}>
                            {photos.map((item) => (
                                <ImageListItem
                                    onClick={onClickPhoto.bind(this, item)}
                                    key={item.photoId}>
                                    <img
                                        src={`${serverUrl + 'images/' + item.photoPath}?w=248&fit=crop&auto=format`}
                                        srcSet={`${serverUrl + 'images/' + item.photoPath}?w=248&fit=crop&auto=format&dpr=2 2x`}
                                        alt={item.photoTitle}
                                        loading="lazy"
                                    />
                                    <ImageListItemBar
                                        title={item.photoTitle}
                                        subtitle={new Date(item.creationTime).toLocaleDateString("en-US")}
                                        actionIcon={
                                            <IconButton
                                                sx={{color: 'rgba(255, 255, 255, 0.54)'}}
                                                aria-label={`info about ${item.photoTitle}`}
                                            >
                                                <InfoIcon/>
                                            </IconButton>
                                        }
                                    />
                                </ImageListItem>
                            ))}
                        </ImageList>
                        {load && <LinearProgress/>}

                </Paper>

            </div>

        </>
    )
}