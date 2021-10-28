import * as React from 'react';
import {styled, alpha} from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import {makeStyles} from "@material-ui/core";
import Button from "@mui/material/Button";
import PhotoArt from '../../images/logo/logo_white.png'
import SearchBar from "../SearchBar/SearchBar";
import {useCallback, useState} from "react";


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
        marginLeft: '-30px',
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
        justifyContent: 'space-between',
        padding: 0,
        margin: 0
    },

    buttonLink: {
        textDecoration: 'none'
    }
}

const useStyle = makeStyles({
    toolBar: {
        display: 'flex',
        width: '100%',
        flexDirection: 'row',
        justifyContent: 'space-between',
        padding: 0,
        margin: 0
    },
    buttonsPanel: {
        display: 'flex',
        flexDirection: 'row',
        marginLeft: '30px',
        marginRight: '120px',
        alignItems: 'center'
    },
    searchBox: {
        margin: "0 40px",
        width: "100%",
        position: "relative"
    },
    searchResults: {
        padding: "64px 10px 10px 10px",
        backgroundColor: "rgb(241,241,241)",
        position: "absolute",
        top: "-8px",
        borderRadius: 10,
        width: "100%",
        zIndex: 300,
        left: "-10px",
        boxShadow: "0 0 5px black"
    }
});


const Search = styled('div')(({theme}) => ({
    position: 'relative',
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
        backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    width: '100%',
    borderRadius: '15px',
}));

const SearchIconWrapper = styled('div')(({theme}) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({theme}) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        // vertical padding + font size from searchIcon
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        minWidth: '200px',
        width: '100%',
        maxWidth: '1000px',
        [theme.breakpoints.up('md')]: {
            width: '100%',
        },
    },
}));

export default function Bar() {

    const classes = useStyle();
    const [searchParam, setSearchParam] = useState('')
    const handleInput = useCallback((event) => {


        setSearchParam(event.target.value)
    }, [setSearchParam])

    const x = searchParam.length >= 3;

    return (

        <AppBar elevation={0} position='absolute' style={style.appBar}>
            <Toolbar style={style.toolBar}>

                <a href='home'> <img style={{maxHeight: '40px', marginLeft: '35px'}} src={PhotoArt}
                                     alt={'Logo'}/></a>

                <div className={classes.searchBox}>
                    <Search style={{
                        zIndex: "400",
                        border: x ? "1px solid #aaa" : undefined,
                        backgroundColor: x ? "#ddd" : undefined,
                        color: x ? "black" : undefined
                    }}>

                        <SearchIconWrapper>
                            <SearchIcon/>
                        </SearchIconWrapper>
                        <StyledInputBase
                            style={{width:'100%'}}
                            onChange={handleInput}
                            placeholder="Search…"
                            inputProps={{'aria-label': 'search'}}
                        />
                    </Search>
                    {searchParam.length >= 3 && <div className={classes.searchResults}>
                        <SearchBar param={searchParam}/>
                    </div>}
                </div>

                <div className={classes.buttonsPanel}>
                    <a href='login' style={style.buttonLink}> <Button style={style.buttonLogIn}>
                        Log In
                    </Button> </a>
                    <a href='register' style={style.buttonLink}> <Button style={style.buttonSignIn}>
                        Sign Up
                    </Button> </a>
                </div>
            </Toolbar>
        </AppBar>

    );
}
