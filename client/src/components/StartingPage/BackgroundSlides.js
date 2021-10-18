import React from 'react';
import {Fade} from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'
import {makeStyles} from "@mui/styles";


function importAll(r) {
    return r.keys().map(r);
}

const images = importAll(require.context('../../images/background/', false, /\.(png|jpe?g|svg)$/)).map(x => x.default);


const backgroundPhotos = makeStyles({
    backgroundStyles:
        {
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


export default function BackgroundSlides() {

    const classes = backgroundPhotos();

    return (
        <div className="slide-container">
            <div style={{height: '100vh'}}>
                <Fade>
                    {images.map((fadeImage, index) => (
                        <div key={index} className={classes.backgroundStyles}
                             style={{backgroundImage: "url(" + fadeImage + ")"}}/>
                    ))}
                </Fade>
            </div>
        </div>
    )
}

