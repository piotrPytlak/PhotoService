import React, {useContext} from "react";
import ReactDOM from "react-dom";
import classes from './sideStyle.module.css'
import {Divider, Avatar, Grid, Paper} from "@material-ui/core";
import Bar from "../Bar";
import {BarChart} from './BarChart'
import {apiContext} from "../../store/ApiContext";
import {galleryContext} from "../../store/GalleryContext";

const imgLink =
    "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";


export function DescribeSection() {

    const {photoInformation} = useContext(galleryContext)


    return (
        <Paper classes={{root: classes.describeSection}}>
            <h1>{photoInformation?.title || "No title!"}</h1>
            <Paper style={{padding: "0px 20px"}}>

                <Grid container wrap="nowrap" spacing={2}>

                    <p style={{textAlign: "left"}}>
                        {
                            photoInformation?.describe || "No describe..."
                        }
                    </p>

                </Grid>
            </Paper>
            <BarChart/>
        </Paper>
    );
}
