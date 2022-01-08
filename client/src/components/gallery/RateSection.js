import {Divider, Paper, Rating} from "@mui/material";
import classes from "./sideStyle.module.css";

import React, {useCallback, useContext, useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import CameraAltIcon from '@mui/icons-material/CameraAlt';
import IsoIcon from '@mui/icons-material/Iso';
import {apiContext} from "../../store/ApiContext";
import {useParams} from "react-router-dom";
import CheckIcon from '@mui/icons-material/Check';
import {galleryContext} from "../../store/GalleryContext";

const style = {
    rateButton: {
        marginTop: '40px'
    }
}


export default function RateSection() {

    const {photoId} = useParams();
    const {putRate} = useContext(apiContext);
    const {photoInformation, load} = useContext(galleryContext)
    const [isSubmitted, setIsSubmitted] = useState(false);

    const [rate, setRate] = useState({
        1: {
            name: 'Creativity',
            rate: null
        },
        2: {
            name: 'Lighting',
            rate: null
        },
        3: {
            name: 'Originality',
            rate: null
        },
        4: {
            name: 'Quality',
            rate: null
        },
        5: {
            name: 'Artistic Impressions',
            rate: null
        },
    });

    const onSubmit = useCallback(() => {

        const rateList = Object.entries(rate).map(x => {

                return {
                    id: x[0],
                    value: x[1]?.rate,
                    rateCategory: x[1]?.name
                };
            }
        )

        putRate(photoId, rateList)
            .then(httpCode => {
                if (httpCode === 200) {
                    setIsSubmitted(true);
                    load();
                }
            });

    }, [rate, photoId, putRate, setIsSubmitted]);


    const onRateHandler = (id, event, value) => {

        rate[id].rate = value;
        setRate({...rate});


    }

    useEffect(() => {
        setRate({
            1: {
                name: 'Creativity',
                rate: null
            },
            2: {
                name: 'Lighting',
                rate: null
            },
            3: {
                name: 'Originality',
                rate: null
            },
            4: {
                name: 'Quality',
                rate: null
            },
            5: {
                name: 'Artistic Impressions',
                rate: null
            }
        });
        setIsSubmitted(false);
    },[photoId])


    return (
        <Paper classes={{root: classes.rateSection}}>
            <h1>Rating</h1>
            <div className={classes.categories}>

                <h3 className={classes.hTag}>Creativity</h3>
                <Rating name="half-rating" value={rate[1].rate} onChange={onRateHandler.bind(this, 1)}
                        precision={0.5}/>


                <h3 className={classes.hTag}>Lighting</h3>
                <Rating name="half-rating" value={rate[2].rate} onChange={onRateHandler.bind(this, 2)}
                        precision={0.5}/>


                <h3 className={classes.hTag}>Originality</h3>
                <Rating name="half-rating" value={rate[3].rate} onChange={onRateHandler.bind(this, 3)}
                        precision={0.5}/>


                <h3 className={classes.hTag}>Quality</h3>
                <Rating name="half-rating" value={rate[4].rate} onChange={onRateHandler.bind(this, 4)}
                        precision={0.5}/>


                <h3 className={classes.hTag}>Artistic Impressions</h3>
                <Rating name="half-rating" value={rate[5].rate} onChange={onRateHandler.bind(this, 5)}
                        precision={0.5}/>


                <Button style={style.rateButton} onClick={onSubmit} disabled={isSubmitted}
                        startIcon={isSubmitted ? <CheckIcon/> : <></>}
                        variant="outlined"
                        size="medium">
                    Send rate
                </Button>
            </div>

            <Divider variant="fullWidth" style={{margin: "60px 0"}}/>
            <div className={classes.categories}>
                <CameraAltIcon fontSize={'large'}/>
                <p>{(!!photoInformation?.camera && !!photoInformation?.model) ? (photoInformation?.camera + " " + photoInformation?.model) : "No information!"}</p>
                <IsoIcon classes={{root: classes.isoIcon}} fontSize={'large'}/>
                <p>{`ISO: ${photoInformation?.iso || "No information!"}`}</p>
            </div>
        </Paper>
    )
}
