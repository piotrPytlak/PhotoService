import * as React from "react";
import {useCallback, useContext, useEffect, useMemo, useState} from "react";
import {apiContext} from "../../store/ApiContext";
import {userContext} from "../../store/UserContext";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";
import {makeStyles} from "@material-ui/core";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import IconButton from "@mui/material/IconButton";
import {Button} from "@mui/material";
import {Editor} from "react-draft-wysiwyg";
import {EditorState} from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import EditIcon from "@mui/icons-material/Edit";
import ChangeAvatarDialog from "../dialog/ChangeAvatarDialog";
import ChangeBackgroundDialog from "../dialog/ChangeBackgroundDialog";


const styles = {

    box: {

        backgroundColor: "#faf7e5",
        //padding: "0px",
        borderRadius: "10px",
        display: "flex",
        justifyContent: "center",
        flexDirection: "column",
        fontFamily: "sans-serif",
        padding: "10px",
        boxShadow:
            "rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px",
    },

    container: {

        display: "grid",
        gridTemplateColumns: "calc(0.11 * 100vw) calc(0.11 * 100vw)",
        gridRow: "auto auto",
        gridColumnGap: "1.5vw",
        gridRowGap: "1.5vw",
    },

    upText: {
        fontSize: '0.8vw',
        margin: "2px",
        color: "#181818",
        fontWeight: "bold",
    },

    name: {
        textShadow:
            "-1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
        color: "white",
        fontSize: '2vw',
        margin: "0px",
    },

    username: {
        textShadow:
            "-1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
        color: "white",
        fontSize: '1.5vw',
        margin: "0px",
    },

    downText: {
        fontSize: '1.5vw',
        margin: "2px",
        color: "black",
        fontWeight: "bold",
    },
    containerBackground: {
        position: 'relative'
    },
    avatar: {
        width: 'calc(0.12 * 100vw)',
        height: 'calc(0.12 * 100vw)',
        marginRight: "15px",
        boxShadow:
            "rgb(14 30 37 / 64%) 0px 2px 9px 6px, rgb(14 30 37 / 78%) 0px 2px 16px 0px",
    },
    button: {
        right:'10px',
        bottom: '10px',
        backgroundColor: 'rgba(255,255,255,0.5)'
    },
    buttonAvatar: {
        position: 'absolut',
        width: '40px',
        height: '40px',
        left:'0px',
        bottom: '0px',
        right: '40px',
        display: 'flex',
        backgroundColor: 'rgba(255,255,255,0.5)'
    },
    avatarContainer: {
        position: 'relative',
        display: 'flex',
        alignItems: 'end',

    },
    buttonFollow: {
        fontSize: '0.75vw',
        width: 'calc(0.09 * 100vw)',
        height: 'calc(0.021 * 100vw)',
        fontWeight: "bold",
    }
};

export default function UserBackground() {

    const {selectedUser, currentUser} = useContext(userContext);
    const {serverUrl} = useContext(apiContext);
    const [changeAvatarIsOpen, setChangeAvatarIsOpen] = useState(false);
    const [changeBackgroundIsOpen, setChangeBackgroundIsOpen] = useState(false);

    const onClickChangeAvatar = useCallback(() => {
        setChangeAvatarIsOpen(true);
    },[setChangeAvatarIsOpen]);

    const onClickChangeBackground = useCallback(() => {
        setChangeBackgroundIsOpen(true);
    },[setChangeBackgroundIsOpen]);

    const styleBackground = useMemo(() => {
        return {
            backgroundImage:
                "url(" +
                serverUrl +
                "images/" +
                selectedUser?.backgroundPath?.split("\\").join("/") +
                ")",
            position: "static",
            maxWidth: "100vw",
            width: "100%",
            height: "calc(400vw / 18)",
            top: "0px",
            left: "0px",
            color: "transparent",
            backgroundSize: "cover",
            backgroundPosition: "50% 50%",
            backgroundRepeat: "none",
            zIndex: 1,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'end'
        };
    }, [selectedUser, serverUrl]);

    return (
        <>
            <div style={styles.containerBackground}>
                <div style={styleBackground}>
                    <div
                        style={{
                            width: '100%',
                            display: "flex",
                            padding: "0 10%",
                            minWidth: '312px',
                            justifyContent: "space-between",
                            alignItems: "center",
                            marginBottom: 'calc(0.01* 100vw)'
                        }}
                    >
                        <div
                            style={{
                                display: "flex",
                                flexDirection: "row",
                                alignItems: "center",
                            }}
                        >
                            <div style={styles.avatarContainer}>
                            <Avatar
                                alt="Remy Sharp"
                                src={serverUrl + "images/" + selectedUser?.avatarPath}
                                sx={styles.avatar}
                            />
                                {selectedUser && currentUser && selectedUser.userId === currentUser.userId &&
                                    <IconButton onClick={onClickChangeAvatar} style={styles.buttonAvatar}
                                                sx={{fill: 'rgba(255, 255, 255, 1)'}}>
                                        <EditIcon/>
                                    </IconButton>}
                            </div>
                            {changeAvatarIsOpen && <ChangeAvatarDialog setOption={setChangeAvatarIsOpen}/> }
                            <div
                                style={{
                                    display: "flex",
                                    flexDirection: "column",
                                    gap: "10px",
                                }}
                            >
                                <div style={{width: 'calc(0.2 * 100vw)'}}>
                                    <h2 style={styles.name}>
                                        {selectedUser?.firstName + " " + selectedUser?.lastName}
                                    </h2>
                                    <h3 style={styles.username}>
                                        {"@" + selectedUser?.username}
                                    </h3>
                                </div>
                                <Button sx={styles.buttonFollow} variant="contained">Follow</Button>
                            </div>
                        </div>
                        <div style={styles.container}>
                            <Paper style={styles.box}>
                                <p style={styles.upText}>FOLLOWERS</p>
                                <p style={styles.downText}>{selectedUser?.followers}</p>
                            </Paper>
                            <Paper style={styles.box}>
                                <p style={styles.upText}>FOLLOWING</p>
                                <p style={styles.downText}>{selectedUser?.following}</p>
                            </Paper>
                            <Paper style={styles.box}>
                                <p style={styles.upText}>PHOTOS</p>
                                <p style={styles.downText}>{selectedUser?.photoCount}</p>
                            </Paper>
                            <Paper style={styles.box}>
                                <p style={styles.upText}>ALBUMS</p>
                                <p style={styles.downText}>{selectedUser?.albumCount}</p>
                            </Paper>
                        </div>

                    </div>
                    {selectedUser && currentUser && selectedUser.userId === currentUser.userId &&
                        <IconButton onClick={onClickChangeBackground} style={styles.button}
                        sx={{fill: 'rgba(255, 255, 255, 1)'}}>
                        <EditIcon/>
                    </IconButton>}
                    {changeBackgroundIsOpen&& <ChangeBackgroundDialog setOption={setChangeBackgroundIsOpen}/> }
                </div>
            </div>
        </>
    );
}
