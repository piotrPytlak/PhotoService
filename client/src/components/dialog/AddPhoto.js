import {Button, Dialog, DialogTitle, TextField, Typography} from "@mui/material"
import {makeStyles} from "@mui/styles";
import React, {useCallback, useContext, useEffect, useRef, useState} from 'react'
import "@pathofdev/react-tag-input/build/index.css";
import ReactTagInput from "@pathofdev/react-tag-input";
import UploadForm from "../UploadPhoto/UploadForm";
import {Alert, Autocomplete} from "@material-ui/lab";
import {apiContext} from "../../store/ApiContext";
import {useParams} from "react-router-dom";
import {uploadPhotoContext} from "../../store/UploadPhotoContext";


const useStyle = makeStyles({
    addPhotoElements: {
        width: '80%'
    },
    addPhotoElementsAlbum: {
        width: '80%',
        marginBottom: '5px'
    }
})

const style = {
    addPhotoElementsStyle: {
        marginBottom: '5px'
    },
    sx: {
        width: '300px'
    }
}

const message = {
    1: <>Add photo!</>,
    2: <>Error!!!</>,
    3: <>Loading not end yet !!!</>,
    200: <>Success</>
}

export default function AddPhoto(props) {
    const {userId} = useParams();

    const {onClose} = props
    const [tags, setTags] = useState([])
    const classes = useStyle()
    const {userAlbums, addPhoto} = useContext(apiContext)
    const [albums, setAlbums] = useState([])
    const {files} = useContext(uploadPhotoContext);
    const [response, setResponse] = useState({responseCode: undefined})
    const photoObject = useRef({
        title: '',
        description: '',
        albumId: null,
        tags: [],
        photoUuid: null
    })

    useEffect(() => {
        userAlbums(userId).then((x) => {
            setAlbums(x)
        })
    }, [setAlbums, userAlbums, userId])

    const onClickHandler = useCallback(() => {

        if (photoObject.current.title.length < 3 || files.length === 0) {
            setResponse({
                responseCode: photoObject.current.title.length < 3 ? 1 : 2
            });
            return;
        } else if (!files[0]?.serverId) {
            setResponse({
                responseCode: 3
            });
            return;
        }


        photoObject.current = {
            ...photoObject.current,
            tags: tags,
            photoUuid: files[0]?.serverId + '.' + files[0]?.fileExtension
        }

        addPhoto(photoObject.current).then(status => {
            setResponse({responseCode: status});
            status === 200 && setTimeout(() => {
                onClose();
            }, 3000);
        })


    })

    const onTitleHandler = useCallback((event) => photoObject.current.title = event.target.value, [photoObject])
    const onDescriptionHandler = useCallback((event) => photoObject.current.description = event.target.value, [photoObject])

    const onSelectAlbum = useCallback((event, value) => photoObject.current.albumId = value.albumId, [photoObject])

    return (
        <>
            <Dialog open={true} onClose={onClose} fullWidth={true}>
                <DialogTitle titleStyle={{textAlign: "center"}}>
                    <Typography variant="h5" align="center">Add photo</Typography>
                </DialogTitle>

                <div style={{
                    display: 'flex',
                    justifyContent: 'flex-start',
                    flexDirection: 'column',
                    alignItems: 'center',
                    marginBottom: '20px'
                }}>
                    <TextField error={response.responseCode === 1} onChange={onTitleHandler} id="filled-basic"
                               label="Title"
                               variant="filled"
                               className={classes.addPhotoElements}
                               style={style.addPhotoElementsStyle}/>
                    <TextField onChange={onDescriptionHandler} id="filled-basic" label="Description" variant="filled"
                               className={classes.addPhotoElements} style={style.addPhotoElementsStyle}/>

                    <Autocomplete onChange={onSelectAlbum} classes={{root: classes.addPhotoElementsAlbum}}
                                  getOptionLabel={(option) => option.name}
                                  id="combo-box-demo"
                                  options={albums}
                                  sx={{width: '100%'}}
                                  renderInput={(params) => <TextField {...params} label="Albums"/>}
                    />

                    <ReactTagInput
                        maxTags={50}
                        placeholder="Type tag and press enter"
                        editable={true}
                        readOnly={false}
                        removeOnBackspace={true}
                        tags={tags}
                        onChange={(newTags) => setTags(newTags)}
                    />

                </div>

                {!!response.responseCode && response.responseCode !== 200 &&
                <Alert variant="filled" severity="error" style={{marginTop: '30px'}}>
                    {message[response.responseCode]}
                </Alert>}

                {response.responseCode === 200 &&
                <Alert variant="filled" severity="success" style={{marginTop: '30px'}}>
                    {message[response.responseCode]}
                </Alert>
                }

                <div>
                    <UploadForm/>
                </div>
                <Button onClick={onClickHandler} variant="contained" style={{backgroundColor: 'black'}}>Submit</Button>

            </Dialog>
        </>)
}