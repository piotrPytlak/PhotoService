import {Paper} from "@mui/material";

import * as React from "react";
import {useCallback, useContext, useEffect, useMemo, useState} from "react";
import {userContext} from "../../store/UserContext";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import {Editor} from "react-draft-wysiwyg";
import {EditorState} from "draft-js";
import {convertToHTML, convertFromHTML} from 'draft-convert';
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import {useParams} from "react-router-dom";
import {apiContext} from "../../store/ApiContext";
import EditIcon from '@mui/icons-material/Edit';
import {IconButton} from "@material-ui/core";
import Button from "@mui/material/Button";
import ReactHtmlParser from 'react-html-parser';

const HOTKEYS = {
    'mod+b': 'bold',
    'mod+i': 'italic',
    'mod+u': 'underline',
    'mod+`': 'code',
}

const state = {
    mode: 1,
    unMode: 2
}

const style = {
    container: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
    },
    paper: {
        width: "85%",
        padding: "0px 15px",
        marginTop: "10px",
        marginBottom: "10px",
        minHeight: "calc(100vh - 410px)",
    },

    editor: {
        border: "1px solid black",
        padding: '2px',
        minHeight: '400px'
    },
    titleContainer: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
    },

    aboutMeSection: {
        margin: '15px 25px'
    },

    buttonContainer: {
        display: 'flex',
        justifyContent: 'end',
        margin: '10px'
    },

    button: {
        width: '50px',
        height: '50px'
    }

}

export default function TabBarAboutMe(callback, deps) {

    const {selectedUser, setSelectedUser, currentUser} = useContext(userContext);
    const contentHTML = selectedUser?.aboutMe;
    const [mode, setMode] = useState(state.unMode);
    const [useInformation, setUserInformation] = useState(undefined);
    const {editAboutMe, userDetails} = useContext(apiContext);

    const [editorState, setEditorState] = useState(() => EditorState.createWithContent(convertFromHTML(contentHTML ? contentHTML : '')));
    const {userId} = useParams();

    useEffect(() => setEditorState(() => EditorState.createWithContent(convertFromHTML(contentHTML ? contentHTML : '')))
        , [setEditorState, selectedUser]);


    function myBlockStyleFn(contentBlock) {
        const type = contentBlock.getType();
        if (type === 'blockquote') {
            return 'superFancyBlockquote';
        }
    }


    const handleChangeMode = useCallback(() => {
        setMode(state.mode);
    }, [setMode])

    const handleEdit = useCallback(() => {
        editAboutMe(convertToHTML(editorState.getCurrentContent()))
            .then(content => {
                if (selectedUser) {
                    selectedUser.aboutMe = content?.aboutMe;
                    setSelectedUser({...selectedUser})
                }
                setMode(state.unMode);
            })
    }, [editAboutMe, editorState, selectedUser, setSelectedUser])

    return (
        <>
            <div style={style.container}>
                <Paper
                    style={style.paper}
                    sx={{width: 500, height: 450}}
                >
                    <div style={style.aboutMeSection}>
                        <div style={style.titleContainer}>
                            <h1>About me</h1>
                            {selectedUser && currentUser && selectedUser.userId === currentUser.userId &&
                                <IconButton onClick={handleChangeMode}
                                            style={style.button}
                                            sx={{color: 'rgba(255, 255, 255, 0.54)'}}>
                                    <EditIcon/>
                                </IconButton>}
                        </div>

                        {mode === state.mode ?
                            <>
                                <div style={style.editor}>
                                    <Editor
                                        blockStyleFn={myBlockStyleFn}
                                        editorState={editorState}
                                        onEditorStateChange={setEditorState}/>

                                </div>
                                <div style={style.buttonContainer} onClick={handleEdit}>
                                    <Button variant="contained">Confirm</Button>
                                </div>
                            </>
                            : <>{ReactHtmlParser(contentHTML)}</>
                        }

                    </div>
                </Paper>
            </div>
        </>
    );
}
