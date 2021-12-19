import {createContext, useState} from "react";


export const uploadPhotoContext = createContext(null)


export default function UploadPhotoContext({children}) {

    const [files, setFiles] = useState([])

    return <uploadPhotoContext.Provider value={{files,setFiles}}>
        {children}
    </uploadPhotoContext.Provider>


}