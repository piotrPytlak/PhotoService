import {
    Button,
    Dialog,
    DialogTitle,
    TextField,
    Typography,
} from "@mui/material";
import {makeStyles} from "@mui/styles";
import React, {
    useCallback,
    useContext,
    useEffect,
    useRef,
    useState,
} from "react";
import "@pathofdev/react-tag-input/build/index.css";
import ReactTagInput from "@pathofdev/react-tag-input";
import UploadForm from "../UploadPhoto/UploadForm";
import {Alert, Autocomplete} from "@material-ui/lab";
import {apiContext} from "../../store/ApiContext";
import {useHistory, useParams} from "react-router-dom";
import {uploadPhotoContext} from "../../store/UploadPhotoContext";

const useStyle = makeStyles({
    addPhotoElements: {
        width: "80%",
    },
    addPhotoElementsAlbum: {
        width: "80%",
        marginBottom: "5px",
    },
});

const style = {
    addPhotoElementsStyle: {
        marginBottom: "5px",
    },
    sx: {
        width: "300px",
    },
};

const message = {
    1: <>Add photo!</>,
    2: <>Too short title</>,
    3: <>Loading not end yet !!!</>,
    4: <>No selected album</>,
    200: <>Success</>,
};

export default function AddPhoto(props) {
    const {userId} = useParams();

    const {onClose} = props;
    const [tags, setTags] = useState([]);
    const classes = useStyle();
    const {currentUserAlbums, addPhoto} = useContext(apiContext);
    const [albums, setAlbums] = useState([]);
    const {files} = useContext(uploadPhotoContext);
    const [response, setResponse] = useState({responseCode: undefined});
    const history = useHistory();
    const photoObject = useRef({
        title: "",
        description: "",
        albumId: null,
        tags: [],
        photoUuid: null,
        camera: null,
        model: null,
        iso: null,
        exif: null,
    });

    useEffect(() => {
        currentUserAlbums().then((x) => {
            setAlbums(x);
        });
    }, [setAlbums, currentUserAlbums, userId]);

    const onClickHandler = useCallback(() => {
        if (photoObject.current.title.length < 3 || files.length === 0 || !photoObject.current?.albumId) {
            setResponse({
                responseCode: photoObject.current.title.length < 3 ? 2 :
                    !photoObject.current?.albumId ? 4 : 1,
            });
            return;
        } else if (!files[0]?.serverId) {
            setResponse({
                responseCode: 3,
            });
            return;
        }

        photoObject.current = {
            ...photoObject.current,
            tags: tags,
            photoUuid: files[0]?.serverId + "." + files[0]?.fileExtension,
            ...files[0]?.metadataService
        };

        console.log(photoObject.current);
        addPhoto(photoObject.current).then((status) => {
            setResponse({responseCode: status});
            status === 200 &&
            setTimeout(() => {
                onClose();
                history.go(0);
            }, 2000);
        });
    });

    const onTitleHandler = useCallback(
        (event) => (photoObject.current.title = event.target.value),
        [photoObject]
    );
    const onDescriptionHandler = useCallback(
        (event) => (photoObject.current.description = event.target.value),
        [photoObject]
    );

    const onSelectAlbum = useCallback(
        (event, value) => (photoObject.current.albumId = value.albumId),
        [photoObject]
    );

    return (
        <>
            <Dialog open={true} onClose={onClose} fullWidth={true}>
                <DialogTitle titleStyle={{textAlign: "center"}}>
                    <Typography variant="h5" align="center">
                        Add photo
                    </Typography>
                </DialogTitle>

                <div
                    style={{
                        display: "flex",
                        justifyContent: "flex-start",
                        flexDirection: "column",
                        alignItems: "center",
                        marginBottom: "20px",
                    }}
                >
                    <TextField
                        error={response.responseCode === 2}
                        onChange={onTitleHandler}
                        id="filled-basic"
                        label="Title"
                        variant="filled"
                        className={classes.addPhotoElements}
                        style={style.addPhotoElementsStyle}
                    />
                    <TextField
                        onChange={onDescriptionHandler}
                        id="filled-basic"
                        label="Description"
                        variant="filled"
                        className={classes.addPhotoElements}
                        style={style.addPhotoElementsStyle}
                    />

                    <Autocomplete
                        onChange={onSelectAlbum}
                        classes={{root: classes.addPhotoElementsAlbum}}
                        getOptionLabel={(option) => option.name}
                        id="combo-box-demo"
                        options={albums}
                        sx={{width: "100%"}}
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

                {!!response.responseCode && response.responseCode !== 200 && (
                    <Alert
                        variant="filled"
                        severity="error"
                        style={{marginTop: "30px"}}
                    >
                        {message[response.responseCode]}
                    </Alert>
                )}

                {response.responseCode === 200 && (
                    <Alert
                        variant="filled"
                        severity="success"
                        style={{marginTop: "30px"}}
                    >
                        {message[response.responseCode]}
                    </Alert>
                )}

                <div>
                    <UploadForm/>
                </div>
                <Button
                    onClick={onClickHandler}
                    variant="contained"
                    style={{backgroundColor: "black"}}
                >
                    Submit
                </Button>
            </Dialog>
        </>
    );
}
