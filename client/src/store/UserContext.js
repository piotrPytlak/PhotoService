import { createContext, useCallback, useContext, useState } from "react";
import { apiContext } from "./ApiContext";
import {useHistory, useLocation} from "react-router-dom";

export const userContext = createContext(null);



export default function UserContext({ children }) {
  const [currentUser, setCurrentUser] = useState(undefined);
  const { currentUserDetails, userDetails } = useContext(apiContext);
  const [selectedUser, setSelectedUser] = useState(undefined);

  const loadSelectUser = useCallback(
    (userId) =>
      currentUser?.id === userId
        ? setSelectedUser(currentUser)
        : userDetails(userId).then((x) => {
            setSelectedUser(x);
          }),
    [setSelectedUser, currentUser, userDetails]
  );

  const loadUser = useCallback(
    async () =>
      currentUserDetails().then((x) => {
        setCurrentUser(x);
        return x;
      }),
    [currentUserDetails, setCurrentUser]
  );

  return (
    <userContext.Provider
      value={{
        loadUser,
        currentUser,
        selectedUser,
          setSelectedUser,
        loadSelectUser,
      }}
    >
      {children}
    </userContext.Provider>
  );
}
