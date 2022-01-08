import * as React from 'react';
import {useCallback, useContext, useEffect, useRef, useState} from 'react';
import {alpha, styled} from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import {ClickAwayListener, makeStyles} from "@material-ui/core";
import PhotoArt from '../images/logo/logo_white.png'
import SearchBar from "./SearchBar/SearchBar";
import UploadIcon from '@mui/icons-material/Upload';
import NotificationsIcon from '@mui/icons-material/Notifications';
import LogoutIcon from '@mui/icons-material/Logout';
import {Avatar, Box, IconButton} from "@mui/material";
import {apiContext} from "../store/ApiContext";
import {useLocation} from "react-router-dom";
import Button from "@mui/material/Button";
import {userContext} from "../store/UserContext";
import {useHistory} from "react-router-dom";

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
        background: 'black'

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
        position: "relative",
        display: 'flex'
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


export default function Bar(props) {

    const classes = useStyle()
    const searchInput = useRef()
    const [searchParam, setSearchParam] = useState('')
    const {serverUrl, logout} = useContext(apiContext)
    const {currentUser, loadUser} = useContext(userContext)
    const {user} = props;
    const currentPath = useLocation()


    const history = useHistory();


    useEffect(() => {
        currentPath.pathname.startsWith('/user') && loadUser();
    }, [currentPath, loadUser])

    const handleLogout = (event) => {
        event.preventDefault()
        logout().then(response => {
            if (response.status === 204) {
                history.push("/home")
            }

        })
    }

    const handleInput = useCallback((event) => {

        setSearchParam(event.target.value)

    }, [setSearchParam])


    const onClickAwayInput = useCallback(() => {
            setSearchParam('')
            searchInput.current.value = ''
        }, [setSearchParam, searchInput]
    )

    const x = searchParam.length >= 3; // do not touch!
    const searchElement = () => (
        <>
            <ClickAwayListener onClickAway={onClickAwayInput}>
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
                            inputRef={searchInput}
                            style={{width: '100%'}}
                            onChange={handleInput}
                            placeholder="Search…"
                            inputProps={{'aria-label': 'search'}}

                        />
                    </Search>

                    {searchParam.length >= 3 &&
                    <div className={classes.searchResults}>
                        <SearchBar param={searchParam}/>
                    </div>}
                </div>
            </ClickAwayListener>
        </>
    )


    const renderBarTools = () => {
        if (currentPath.pathname.startsWith('/home'))
            return (
                <>
                    {searchElement()}
                    <div className={classes.buttonsPanel}>
                        <a href='login' style={style.buttonLink}> <Button style={style.buttonLogIn}>
                            Log In
                        </Button> </a>
                        <a href='register' style={style.buttonLink}> <Button style={style.buttonSignIn}>
                            Sign Up
                        </Button> </a>
                    </div>
                </>
            )
        else if (currentPath.pathname.startsWith('/user'))
            return (
                <>
                    {searchElement()}
                    <div style={{display: 'flex', alignItems: 'center', marginRight: '60px'}}>
                        <Box>
                            <IconButton size={"large"} color={"inherit"}>
                                <UploadIcon fontSize={"large"}/>
                            </IconButton>
                        </Box>
                        <Box mr="30px">
                            <IconButton size={"large"} color={"inherit"}>
                                <NotificationsIcon fontSize={"large"}/>
                            </IconButton>
                        </Box>
                        <IconButton>
                            <Avatar sx={style.listElement}
                                    src={serverUrl + "images/" + currentUser?.avatarPath}/>
                        </IconButton>
                        <Box>
                            <IconButton size={"large"} color={"inherit"}>
                                <LogoutIcon fontSize={"large"} onClick={handleLogout}/>
                            </IconButton>
                        </Box>
                    </div>
                </>

            )
        else
            return (<> </>)


    }


    return (

        <AppBar elevation={0} position={currentPath.pathname.startsWith("/user") ? 'static' : 'absolute'}
                style={{backgroundColor: currentPath.pathname.startsWith("/user") ? "black" : "rgba(0,0,0,0.5)"}}>
            <Toolbar style={style.toolBar}>

                <a href='home'> <img style={{maxHeight: '40px', marginLeft: '35px'}} src={PhotoArt}
                                     alt={'Logo'}/></a>
                {renderBarTools()}
            </Toolbar>
        </AppBar>

    );
}

