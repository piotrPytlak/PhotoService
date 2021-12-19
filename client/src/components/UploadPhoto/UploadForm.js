import FilePondPluginFileEncode from 'filepond-plugin-file-encode'
import FilePondPluginFileMetadata from 'filepond-plugin-file-metadata'
import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation'
import FilePondPluginImagePreview from 'filepond-plugin-image-preview'
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css'
import 'filepond/dist/filepond.min.css'
import { load } from "piexifjs"
import React, { useContext } from 'react'
import { FilePond, registerPlugin } from 'react-filepond'
import { apiContext } from "../../store/ApiContext"
import { uploadPhotoContext } from "../../store/UploadPhotoContext"

registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview, FilePondPluginFileEncode, FilePondPluginFileMetadata)

const piexif = require('piexifjs');

function debugExif(exif) {
    for (const ifd in exif) {
        if (ifd === 'thumbnail') {
            const thumbnailData = exif[ifd] === null ? "null" : exif[ifd];
            console.log(`- thumbnail: ${thumbnailData}`);
        } else {
            console.log(`- ${ifd}`);
            for (const tag in exif[ifd]) {
                console.log(`    - ${piexif.TAGS[ifd][tag]['name']}: ${exif[ifd][tag]}`);
            }
        }
    }
}


//TODO add fields to photoDetails
export default function UploadForm() {

    const { serverUrl } = useContext(apiContext)
    const { files, setFiles } = useContext(uploadPhotoContext);

    const getBase64DataFromJpegFile = file => `data:image/jpeg;base64,${file?.getFileEncodeBase64String()}`

    const test = getBase64DataFromJpegFile(files[0])

    if (!test.endsWith('undefined')) {
        console.log(debugExif(load(test)))
        console.log(load(test))
        console.log('Camera: ', load(test)['0th'][271])
        console.log('Model: ', load(test)['0th'][272])
        console.log('ISO: ', load(test)['Exif'][34855])

    }

    return (
        <div className="App">
            <FilePond
                files={files}
                onupdatefiles={setFiles}
                allowMultiple={true}
                maxFiles={1}
                server={`${serverUrl}uploadBuffer`}
                name="files"
                labelIdle='Drag & Drop your files or <span class="filepond--label-action">Browse</span>'
            />

        </div>
    )
}
