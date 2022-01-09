import * as React from 'react';
import {useCallback, useContext, useMemo, useRef, useState} from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {makeStyles} from "@material-ui/core/styles";
import CloseIcon from '@mui/icons-material/Close';
import Button from "@material-ui/core/Button";
import {FilePond, registerPlugin} from 'react-filepond'
import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import FilePondPluginFileValidateSize from 'filepond-plugin-file-validate-size';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css'
import 'filepond/dist/filepond.min.css'
import Cropper from "react-cropper";
import "cropperjs/dist/cropper.css";
import {Fade} from "@material-ui/core";
// import {networkContext} from "../../../NetworkContainer/NetworkContext";
import {userContext} from "../../store/UserContext";
import {apiContext} from "../../store/ApiContext";
import {useHistory} from "react-router-dom";


registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview, FilePondPluginFileValidateSize, FilePondPluginFileValidateType)


const styled = makeStyles({
    textField: {
        marginBottom: '30px'
    },
    alert: {
        marginTop: '30px'
    },
    titleBanner: {
        display: 'flex',
        justifyContent: 'space-between'
    },
    grid: {
        width: '219px'
    },
    dialog: {
        minWidth: '650px',
        overflow: 'hidden'
    }
})

const style = {

    list: {
        width: '100%'
    },
    cropperStyle: {
        maxHeight: 400,
        maxWidth: '100%'
    },
    boxStyle: {
        display: 'flex',
        flexDirection: 'column',
        m: 'auto',
        width: 'fit-content',
    },
    dialog: {
        marginBottom: '10px'
    }
}

export default function ChangeBackgroundDialog(props) {

    const classes = styled();
    const {setOption} = props;
    const cropperRef = useRef();
    const {currentUser} = useContext(userContext);
    const {serverUrl, uploadBackGround} = useContext(apiContext);
    const [files, setFiles] = useState([])
    const history = useHistory();


    const afterUpload = useCallback(() => history.go(0), [history]);


    const handleClose = () => {
        setOption(undefined);
    };

    const handleFileRemove = useCallback(() => setFiles([]), [setFiles])
    const imageURL = useMemo(() => !!files[0] ? URL.createObjectURL(files[0].file) :
        undefined, [files[0]]);


    const doBeforeRequest = useCallback(() => new Promise((resolve, reject) => {
            const canvas = cropperRef.current?.cropper.getCroppedCanvas()
            !!canvas ? canvas.toBlob(
                (blob) => resolve(blob)
            ) : resolve(undefined)
        })
        , [])


    return (
        <>

            <Dialog classes={{paper: classes.dialog}}
                    disableEnforceFocus
                    fullWidth={true}
                    open={true}
                    onClose={handleClose}
            >

                <div className={classes.titleBanner}>
                    <DialogTitle>Change Background</DialogTitle>
                    <Button onClick={handleClose}>
                        <CloseIcon/>
                    </Button>


                </div>
                <Fade in={files.length > 0} timeout={600}>
                    <Cropper

                        src={imageURL}
                        style={style.cropperStyle}
                        aspectRatio={18/4}
                        guides={false}
                        ref={cropperRef}
                        viewMode={2}
                        background={false}
                    />
                </Fade>
                <DialogContent>
                    <DialogContentText style={style.dialog}>
                        Upload background
                    </DialogContentText>
                </DialogContent>
                <FilePond
                    credits={null}
                    instantUpload={false}
                    files={files}
                    onupdatefiles={setFiles}
                    maxFileSize={'7MB'}
                    allowFileTypeValidation={true}
                    acceptedFileTypes={['image/*']}
                    onremovefile={handleFileRemove}
                    server={{
                        process: uploadBackGround.bind(this, doBeforeRequest, afterUpload),
                        revert: (uniqueFileId, load, error) => load(),
                    }}
                    name="files"
                    allowImagePreview={false}
                    labelIdle='Drag & Drop your files or <span class="filepond--label-action">Browse</span>'
                />

                <DialogActions>

                </DialogActions>
            </Dialog>
        </>
    );
}