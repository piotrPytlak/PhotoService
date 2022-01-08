import {createContext, useCallback, useContext, useEffect, useState} from "react";
import {apiContext} from "./ApiContext";
import {useParams} from "react-router-dom";

export const galleryContext = createContext(null)


export default function GalleryContext({children}) {


    const {getPhotoInformation} = useContext(apiContext)
    const [photoInformation, setPhotoInformation] = useState({})
    const [comments, setComments] = useState([]);
    const {photoId} = useParams()

    const load = useCallback(() =>
            getPhotoInformation(photoId).then(x => setPhotoInformation(x)),
        [photoId, setPhotoInformation])

    useEffect(() => load(), [load])



    return (
        <galleryContext.Provider
            value={{
                comments, 
                setComments,
                load,
                photoInformation,
                setPhotoInformation
            }}>
            {children}
        </galleryContext.Provider>
    )


}