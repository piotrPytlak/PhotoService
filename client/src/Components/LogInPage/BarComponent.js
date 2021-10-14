import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import PhotoArt from '../../images/PhotoArt-logos_white.png'


const style = {
    signInButton: {
        color: '#fff',
        marginRight: '150px'
    },

    signUpButton: {
        color: '#000',
        marginRight: '15px'
    },

    buttonLogIn: {
        color: '#fff',
        marginLeft: '30px',
        marginRight: '25px',
        marginTop: '5px',
        marginBottom: '5px',
        textTransform: "none",
        fontSize: '17px',
        minHeight: '15px',
        maxHeight: '30px',
        width: 'auto',
        minWidth: '70px',
        borderRadius: '10px',

    },
    buttonSignIn: {
        background: '#fff',
        color: '#000',
        marginTop: '5px',
        marginBottom: '5px',
        textTransform: "none",
        fontSize: '17px',
        minHeight: '15px',
        maxHeight: '30px',
        width: 'auto',
        minWidth: '80px',
        borderRadius: '10px'
    },
    appBar: {
        background: 'rgba(0,0,0,0.3)'

    },
    toolBar: {
        display: 'flex',
        width: '100%',
        flexDirection: 'row',
        justifyContent: 'left',
        padding: 0,
        margin: 0
    },

    logo: {
        maxHeight: '40px',
        marginLeft: '35px',
    }
}


export default function PrimarySearchAppBar() {

    //add link to logo img localhost:3000/home

    return (

        <AppBar elevation={0} position='absolute' style={style.appBar}>
            <Toolbar style={style.toolBar}>
                <a href='home'><img style={style.logo} alt={'logo.png'} src={PhotoArt}/></a>
            </Toolbar>
        </AppBar>

    );
}
