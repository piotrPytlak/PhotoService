import * as React from "react";
import {useContext, useMemo} from "react";
import {apiContext} from "../../store/ApiContext";
import {userContext} from "../../store/UserContext";
import {makeStyles} from "@material-ui/core/styles";


const style = makeStyles({})


export default function UserBackground() {

    const {selectedUser} = useContext(userContext)
    const {serverUrl, userDetails} = useContext(apiContext)
    const styleBackground = useMemo(() => {
        return ({
            backgroundImage: "url(" + serverUrl + "images/" + selectedUser?.backgroundPath.split("\\").join("/") + ")",
            position: 'static',
            maxWidth: '100vw',
            width: '100%',
            height: '280px',
            top: '0px',
            left: '0px',
            color: 'transparent',
            backgroundSize: 'cover',
            backgroundPosition: '50% 50%',
            backgroundRepeat: 'none',
            zIndex: 1
        });
    }, [selectedUser])


    return (
        <>
            <div style={styleBackground}/>

        </>)
}
