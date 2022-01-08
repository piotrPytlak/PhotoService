import * as React from "react";
import {useContext, useEffect, useMemo, useState} from "react";
import {apiContext} from "../../store/ApiContext";
import {userContext} from "../../store/UserContext";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";
import {makeStyles} from "@material-ui/core";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import IconButton from "@mui/material/IconButton";
import {Button} from "@mui/material";
import { Editor } from "react-draft-wysiwyg";
import { EditorState } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";


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
        height: "100px",
        display: "grid",
        gridTemplateColumns: "130px 130px",
        gridRow: "auto auto",
        gridColumnGap: "20px",
        gridRowGap: "20px",
    },

    upText: {
        margin: "2px",
        color: "#181818",
        fontWeight: "bold",
    },

    name: {
        textShadow:
            "-1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
        color: "white",
        margin: "0px",
    },

    username: {
        textShadow:
            "-1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
        color: "white",
        margin: "0px",
    },

    downText: {
        margin: "2px",
        color: "black",
        fontWeight: "bold",
    },
    avatar: {
        width: 200,
        height: 200,
        marginRight: "15px",
        boxShadow:
            "rgba(14, 30, 37, 0.12) 0px 2px 4px 0px, rgba(14, 30, 37, 0.32) 0px 2px 16px 0px",
    },
};

export default function UserBackground() {
    const {selectedUser} = useContext(userContext);
    const {serverUrl} = useContext(apiContext);

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
            height: "280px",
            top: "0px",
            left: "0px",
            color: "transparent",
            backgroundSize: "cover",
            backgroundPosition: "50% 50%",
            backgroundRepeat: "none",
            zIndex: 1,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
        };
    }, [selectedUser, serverUrl]);

    return (
        <>
            <div style={styleBackground}>
                <div
                    style={{
                        width: '100%',

                        display: "flex",
                        padding: "0 10%",
                        minWidth: '702px',
                        justifyContent: "space-between",
                        alignItems: "center",
                    }}
                >
                    <div
                        style={{
                            display: "flex",
                            flexDirection: "row",
                            alignItems: "center",
                        }}
                    >
                        <Avatar
                            alt="Remy Sharp"
                            src={serverUrl + "images/" + selectedUser?.avatarPath}
                            sx={styles.avatar}
                        />

                        <div
                            style={{
                                display: "flex",
                                flexDirection: "column",
                                gap: "10px",
                            }}
                        >
                            <div>
                                <h2 style={styles.name}>
                                    {selectedUser?.firstName + " " + selectedUser?.lastName}
                                </h2>
                                <h3 style={styles.username}>
                                    {"@" + selectedUser?.username}
                                </h3>
                            </div>
                            <Button variant="contained">Follow</Button>
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
            </div>
        </>
    );
}
