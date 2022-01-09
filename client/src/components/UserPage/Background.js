import * as React from "react";
import {makeStyles} from "@mui/styles";
import {IconButton} from "@material-ui/core";
import EditIcon from "@mui/icons-material/Edit";


const backgroundPhotos = makeStyles({
    backgroundStyles:
        {
            backgroundColor: '#d6d6d6',
            position: 'static',
            maxWidth: '100vw',
            width: '100%',
            height: 'auto',
            top: '0px',
            left: '0px',
            minHeight: '865px',
            color: 'transparent',
            backgroundSize: 'cover',
            backgroundPosition: '50% 50%',
            backgroundRepeat: 'none',
            zIndex: 1

        }
})


export default function Background({children}) {

    const classes = backgroundPhotos();

    return (
        <>
            <div className={classes.backgroundStyles}>
                {
                    children
                }
            </div>
        </>

    )
}

