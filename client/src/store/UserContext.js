import {createContext, useContext, useState} from "react";
import {apiContext} from "./ApiContext";

export const userContext = createContext(null)

export default function UserContext({children}) {

    const [currentUser, setCurrentUser] = useState(undefined)
    const {currentUserDetails} = useContext(apiContext)

    const loadUser = async () => await currentUserDetails().then(x => {
        setCurrentUser(x)
    })


    return (
        <userContext.Provider value={{loadUser, currentUser}}>
            {children}
        </userContext.Provider>
    )


}