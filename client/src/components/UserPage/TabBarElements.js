import * as React from "react";
import {Paper} from "@mui/material";
import {useContext} from "react";
import {userContext} from "../../store/UserContext";

export default function TabBarElements() {

    const {userTab, selectedTab, selectedUser} = useContext(userContext)


    return (
        <>
            {selectedTab === userTab.aboutMe &&
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                height: 'calc(100vh - 392px)'
            }}>
                <Paper style={{width: '85%', height: '95%'}}>
                    {selectedUser?.aboutMe}
                </Paper>

            </div>

            }
        </>
    )
}