import {useContext, useEffect, useState} from "react";
import {apiContext} from "../store/ApiContext";
import {Redirect} from "react-router-dom";

export default function PathByPermission(props) {

    const {children, alternativePath, isPermit, freeAccess, role} = props;
    const [isPermission, setIsPermission] = useState(undefined);
    const {loginStatus} = useContext(apiContext);


    useEffect(
        () => !freeAccess && loginStatus().then(
            x => isPermit ^ role.includes(x.authority) ? setIsPermission(true) : setIsPermission(false)
        )
        , [loginStatus, alternativePath, freeAccess, isPermit, role])


    return (
        <>
            {(!!isPermission || freeAccess) && children}
            {(isPermission === false && !freeAccess) && <Redirect to={alternativePath}/>}

        </>
    )
}
