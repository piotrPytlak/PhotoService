import * as React from "react";
import {useCallback, useContext, useEffect, useState} from "react";
import {ImageList, ImageListItem, ImageListItemBar, Paper,} from "@mui/material";
import {apiContext} from "../../store/ApiContext";
import {useHistory, useParams} from "react-router-dom";
import {IconButton, LinearProgress} from "@material-ui/core";
import CollectionsIcon from '@mui/icons-material/Collections';

export default function TabBarAlbums() {


    const {serverUrl, userAlbumsWithThumbnail, photosInAlbum} =
        useContext(apiContext);
    const [albums, setAlbums] = useState([]);
    const [load, setLoad] = useState(false);
    const history = useHistory();
    const {userId} = useParams();


    useEffect(() => {
        setLoad(true);
        userAlbumsWithThumbnail(userId)
            .then((x) => {
                setAlbums(x);
            })
            .finally(() => {
                setLoad(false);
            });
    }, [setAlbums, userAlbumsWithThumbnail, userId]);

    const onClickAlbum = useCallback(
        (album) => history.push(`/album/user/${userId}/${album.albumId}`),
        [history, userId]
    );


    return (
        <>
            <div
                style={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "center",
                    minHeight: "calc(100vh - 392px)",
                    height: "auto",
                    maxHeight: "calc(100vh - 45px)",
                }}
            >
                <Paper
                    // onScroll={scrollHandler}
                    style={{
                        width: "85%",
                        marginTop: "10px",
                        height: "auto",
                        minHeight: "calc(100vh - 410px)",
                    }}
                    sx={{width: 500, height: 450, overflowY: "scroll"}}
                >
                    <ImageList
                        cols={5 }
                        style={{marginTop: "15px"}}
                    >
                        {albums.map((item) => (
                            <ImageListItem
                                onClick={onClickAlbum.bind(this, item)}
                                key={item.photoId}
                            >
                                <img
                                    src={`${serverUrl}images/${item?.photoPath || "no_image.jpg"}?w=248&fit=crop&auto=format`}
                                    srcSet={`${serverUrl}images/${item?.photoPath || "no_image.jpg"}?w=248&fit=crop&auto=format&dpr=2 2x`}
                                    alt={item.albumName}
                                    loading="lazy"
                                />
                                <ImageListItemBar
                                    title={item.albumName}
                                    subtitle={item.albumData}

                                    actionIcon={

                                        <CollectionsIcon sx={{color: '#FFFF', marginRight: '10px'}}/>

                                    }
                                />
                            </ImageListItem>
                        ))}
                    </ImageList>
                    {load && <LinearProgress/>}
                </Paper>
            </div>

        </>
    );
}
