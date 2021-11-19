import {createContext, useCallback, useContext, useState} from "react";
import {apiContext} from "./ApiContext";

export const userContext = createContext(null)

export default function UserContext({children}) {

    const [currentUser, setCurrentUser] = useState(undefined)
    const {currentUserDetails} = useContext(apiContext)

    const loadUser = useCallback(async () => currentUserDetails().then(x => {
        setCurrentUser(x)
    }), [currentUserDetails, setCurrentUser]);


    return (
        <userContext.Provider value={{loadUser, currentUser}}>
            {children}
        </userContext.Provider>
    )


}