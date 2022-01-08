import * as React from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import {useContext} from "react";
import {userContext} from "../../store/UserContext";
import {useHistory, useParams} from "react-router-dom";

const urlPatternDict = {
    0: 'aboutMe',
    1: 'photos',
    2: 'albums'
}

const userTab = {
    aboutMe: 0,
    photos: 1,
    albums: 2,
};

export default function TabBar() {

    const history = useHistory();
    const {userId} = useParams();
    const selectedTab = userTab[history?.location?.pathname?.split('/')[1]] || 0;



    const handleChange = (event, newValue) => {
        history.push(`/${urlPatternDict[newValue]}/user/${userId}`);
    };

    return (
        <Box sx={{width: '100%', bgcolor: 'background.paper'}}>
            <Tabs value={selectedTab} onChange={handleChange} centered>
                <Tab label="About me"/>
                <Tab label="Photos"/>
                <Tab label="Albums"/>
            </Tabs>
        </Box>
    );
}