import {Paper} from "@mui/material";

import * as React from "react";
import {useCallback, useContext, useEffect, useMemo, useState} from "react";
import {userContext} from "../../store/UserContext";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import {Editor} from "react-draft-wysiwyg";
import {EditorState} from "draft-js";
import {convertToHTML} from 'draft-convert';
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

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
        height: "calc(100vh - 392px)",
    },
    paper: {
        width: "85%",
        padding: "0px 15px 0px 15px",
        marginTop: "10px",
        minHeight: "calc(100vh - 410px)",
    },

    editor: {
        border: "1px solid black",
        padding: '2px',
        minHeight: '400px'
    }

}

export default function TabBarAboutMe() {
    const {userTab, selectedTab, selectedUser} = useContext(userContext);


    const [mode, setMode] = useState(state.unMode);
    const [editorState, setEditorState] = useState(() =>
        EditorState.createEmpty()
    );

    // useEffect(() => {
    //     convertToHTML(editorState.getCurrentContent()), editorState.getCurrentInlineStyle())
    // }, [editorState]);

    function myBlockStyleFn(contentBlock) {
        const type = contentBlock.getType();
        if (type === 'blockquote') {
            return 'superFancyBlockquote';
        }
    }

    return (
        <>
            <div style={style.container}>
                <Paper
                    style={style.paper}
                    sx={{width: 500, height: 450}}
                >
                    <div>
                        <h1>About me</h1>
                        {mode === state.mode ?
                            <div style={style.editor}>
                                <Editor
                                    blockStyleFn={myBlockStyleFn}
                                    editorState={editorState}
                                    onEditorStateChange={setEditorState}/>
                            </div> : <></>
                        }

                    </div>
                </Paper>
            </div>
        </>
    );
}
