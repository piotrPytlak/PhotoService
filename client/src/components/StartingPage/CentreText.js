import Button from "@mui/material/Button";
import * as React from "react";

import classes from "./CentreText.module.css";

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


export default function CentreText() {


    return (<>

            <div className={classes.flex}>
                <div className={classes.text}>
                    <h1 className={classes.lineOne}>
                        Find your inspiration.
                    </h1>

                    <h3 className={classes.lineTwo}>
                        Join the PhotoArt community, home to tens of billions of <br/> photos and 2 million groups.
                    </h3>

                    <a href='register' className={classes.buttonLink}>
                        <Button style={style.buttonSignIn}>
                            Start now!
                        </Button>

                    </a>
                </div>
            </div>
        </>
    )


}