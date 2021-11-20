import * as React from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import {useContext} from "react";
import {userContext} from "../../store/UserContext";

export default function TabBar() {

    const {selectedTab, setSelectedTab} = useContext(userContext)


    const handleChange = (event, newValue) => {
        setSelectedTab(newValue)
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