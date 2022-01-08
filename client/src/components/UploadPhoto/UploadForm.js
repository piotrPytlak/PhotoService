import FilePondPluginFileEncode from "filepond-plugin-file-encode";
import FilePondPluginFileMetadata from "filepond-plugin-file-metadata";
import FilePondPluginImageExifOrientation from "filepond-plugin-image-exif-orientation";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css";
import "filepond/dist/filepond.min.css";
import { load } from "piexifjs";
import React, { useContext } from "react";
import { FilePond, registerPlugin } from "react-filepond";
import { apiContext } from "../../store/ApiContext";
import { uploadPhotoContext } from "../../store/UploadPhotoContext";
import { useState } from "react";
import { useCallback } from "react";

registerPlugin(
  FilePondPluginImageExifOrientation,
  FilePondPluginImagePreview,
  FilePondPluginFileEncode,
  FilePondPluginFileMetadata
);

const piexif = require("piexifjs");

const log = [];
function debugExif(exif) {
  for (const ifd in exif) {
    if (ifd === "thumbnail") {
      const thumbnailData = exif[ifd] === null ? "null" : exif[ifd];
      log.push(`- thumbnail: ${thumbnailData}`);
    } else {
      log.push(`- ${ifd}`);
      for (const tag in exif[ifd]) {
        log.push(
          `    - ${piexif.TAGS[ifd][tag]["name"]}: ${exif[ifd][tag]}`
        );
      }
    }
  }
  return log.join('\n');
}

export default function UploadForm() {
  const { serverUrl } = useContext(apiContext);
  const { files, setFiles } = useContext(uploadPhotoContext);
  const [photoDetails, setPhotoDetails] = useState([]);

  const getBase64DataFromJpegFile = useCallback(
    (file) => `data:image/jpeg;base64,${file?.getFileEncodeBase64String()}`,
    []
  );

  const setFilesFilePond = useCallback(
    (files) => {
      files.forEach((file) => {
        try {
          const encodedFile = getBase64DataFromJpegFile(file);
          file["metadataService"] = {
            camera: load(encodedFile)["0th"][271],
            model: load(encodedFile)["0th"][272],
            iso: load(encodedFile)["Exif"][34855],
            exif: debugExif(load(encodedFile)),
          };
        } catch (e) {
          console.log(e);
        }
      });
      setFiles(files);
    },
    [getBase64DataFromJpegFile, setFiles]
  );

  console.log(files);

  return (
    <div className="App">
      <FilePond
        files={files}
        onupdatefiles={setFilesFilePond}
        allowMultiple={true}
        maxFiles={1}
        server={`${serverUrl}uploadBuffer`}
        name="files"
        labelIdle='Drag & Drop your files or <span class="filepond--label-action">Browse</span>'
      />
    </div>
  );
}
