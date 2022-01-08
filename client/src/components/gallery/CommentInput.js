import React, {useCallback, useContext, useRef, useState} from 'react'
import TextField from '@material-ui/core/TextField';
import {makeStyles} from "@material-ui/core/styles";
import SendIcon from '@mui/icons-material/Send';
import Picker from 'emoji-picker-react';

import InsertEmoticonIcon from '@mui/icons-material/InsertEmoticon';
import IconButton from "@material-ui/core/IconButton";
import {ClickAwayListener} from "@material-ui/core";
import {EmoticonInterpreterContext} from "../Interpreter/EmoticonInterpreterContext";
import {apiContext} from "../../store/ApiContext";
import {useParams} from "react-router-dom";
import {galleryContext} from './../../store/GalleryContext';

const style = {
    picker: {
        position: 'absolute',
        bottom: '40px',
        right: '-100px'
    }
};
const useStyles = makeStyles((theme) =>
        ({
                wrapForm: {
                    marginTop: '20px',
                    display: "flex",
                    width: "95%",
                    margin: `${theme.spacing(0)} auto`,
                    alignItems: 'center'
                },
                wrapText: {
                    width: "100%"
                },
                sendButton: {
                    bottom: '3px'
                },
                button: {
                    position: 'relative',
                    bottom: '3px',
                    borderRadius: '0%',
                    marginRight: '7px',
                },
            }
        )
    )
;


export function CommentInput() {
    const classes = useStyles();
    const [openEmoticonFrame, setOpenEmoticonFrame] = useState(false);
    const inputRef = useRef();
    const {comments, setComments} = useContext(galleryContext);
    const {emojiInterpret} = useContext(EmoticonInterpreterContext);
    const {addComment} = useContext(apiContext)
    //  const {currentUser} = useContext(userContext)
    const {photoId} = useParams()


    // const getInputComment = useCallback((event) => commentData.current.commentText = event.target.value, [commentData])
    //

    const handleSendComment = (event) => {
        event.preventDefault()

        if (inputRef.current.value.length >= 1)
            addComment({
                commentText: inputRef.current.value,
                photoId: photoId
            })
                .then(async response => {
                    if (response.ok) {
                        inputRef.current.value = "";
                        setComments([...comments, await response.json()]);
                    }
                })
        else console.log("za krokie")

    }


    const emojiHandler = useCallback((event, emojiObject) => {
        inputRef.current.value = inputRef.current.value + emojiObject.emoji;
    }, [])

    const interpreterHandler = useCallback((event) => {
        event.target.value = emojiInterpret(event.target.value);
    }, [emojiInterpret]);


    const onClickAwayEmoticonFrameHandler = useCallback(() => {
        setOpenEmoticonFrame(false);
    }, [setOpenEmoticonFrame])

    const onClickButtonEmoticonHandler = useCallback(() => {
        setOpenEmoticonFrame(true);
    }, [setOpenEmoticonFrame])


    const renderPicker = () => (
        <ClickAwayListener onClickAway={onClickAwayEmoticonFrameHandler}>
            <div>
                <Picker onEmojiClick={emojiHandler} pickerStyle={style.picker}/>
            </div>
        </ClickAwayListener>
    )

    return (
        <>
            <div className={classes.wrapForm}>
                <TextField
                    inputRef={inputRef}
                    label='enter your comment'
                    onChange={interpreterHandler}
                    className={classes.wrapText}

                />
                <IconButton onClick={onClickButtonEmoticonHandler} size={'large'}
                            className={classes.button} sx={{color: 'black'}}>
                    {!!openEmoticonFrame && renderPicker()}
                    <InsertEmoticonIcon sx={{fill: 'black'}}/>
                </IconButton>

                <IconButton onClick={handleSendComment} size={'large'} className={classes.button}>
                    <SendIcon sx={{fill: 'black'}}/>
                </IconButton>


            </div>
        </>
    )
}