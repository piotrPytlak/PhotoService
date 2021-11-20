import {createContext, useCallback, useContext, useState} from "react";
import {apiContext} from "./ApiContext";

export const userContext = createContext(null)

const userTab =
    {
        aboutMe: 0,
        photos: 1,
        albums: 2
    }
export default function UserContext({children}) {

    const [currentUser, setCurrentUser] = useState(undefined)
    const {currentUserDetails, userDetails} = useContext(apiContext)
    const [selectedUser, setSelectedUser] = useState(undefined)
    const [selectedTab, setSelectedTab] = useState(userTab.aboutMe)


    const loadSelectUser = useCallback((userId) => currentUser?.id === userId ? setSelectedUser(currentUser) : userDetails(userId).then(x => {
        setSelectedUser(x)
    }), [setSelectedUser, currentUser, userDetails])


    const loadUser = useCallback(async () => currentUserDetails().then(x => {
        setCurrentUser(x)
    }), [currentUserDetails, setCurrentUser]);


    return (
        <userContext.Provider
            value={{userTab, loadUser, currentUser, selectedUser, selectedTab, setSelectedTab, loadSelectUser}}>
            {children}
        </userContext.Provider>
    )

}