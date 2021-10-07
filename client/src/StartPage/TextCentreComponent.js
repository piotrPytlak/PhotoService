import {makeStyles} from "@mui/styles";
import Button from "@mui/material/Button";
import * as React from "react";

const textStyle = makeStyles({
    flex:
        {
            top: 0,
            display: "flex",
            flexDirection: "column",
            position: "absolute",
            justifyContent: "center",
            alignItems: "center",
            width: "100%",
            height: "100%",
            zIndex: 1
        },
    text: {

        position: 'relative',
        top: '-15%',
        textAlign: 'center',
        fontFamily: 'inherit',
        textShadow: ' 0px 0px 3px black'

    },
    lineOne: {
        fontSize: '56px',
        color: 'white'
    },

    lineTwo: {
        fontSize: '30px',
        color: 'white'
    }


})

const style = {
    buttonSignIn: {
        background: '#fff',
        color: '#000',
        marginTop: '5px',
        marginBottom: '5px',
        textTransform: "none",
        minHeight: '64px',
        minWidth: '200px',
        borderRadius: '10px',
        fontSize: '24px',
        fontWeight: 'bold'
    }
}

export function TextCentreComponent() {

    const classes = textStyle();

    return (<>

            <div className={classes.flex}>
                <div className={classes.text}>
                    <h1 className={classes.lineOne}>
                        Find your inspiration.
                    </h1>


                    <h3 className={classes.lineTwo}>
                        Join the PhotoArt community, home to tens of billions of <br/> photos and 2 million groups. </h3>
                    <Button style={style.buttonSignIn}>
                        Start now!
                    </Button>

                </div>

            </div>
        </>
    )


}