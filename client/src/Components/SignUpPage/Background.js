import * as React from "react";
import {makeStyles} from "@mui/styles";
import Background from "../../images/background/signup/signup.jpg";


const backgroundPhotos = makeStyles({
    backgroundStyles:
        {
            backgroundImage: "url(" + Background + ")",
            position: 'static',
            width: '100vw',
            height: '100vh',
            top: '0px',
            left: '0px',
            color: 'transparent',
            backgroundSize: 'cover',
            backgroundPosition: '50% 50%',
            backgroundRepeat: 'none',
            zIndex: 1

        }
})


export function BackgroundImage() {

    const classes = backgroundPhotos();

    return <div className={classes.backgroundStyles}/>
}

