import * as React from "react";
import {Paper} from "@mui/material";
import {useContext} from "react";
import {userContext} from "../../store/UserContext";

export default function TabBarAboutMe() {

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
                <Paper style={{width : '85%',padding: '0px 15px 0px 15px', marginTop: '10px', minHeight: 'calc(100vh - 410px)' }}
                       sx={{width: 500, height: 450}}>
                    {selectedUser?.aboutMe}
                </Paper>

            </div>

            }
        </>
    )
}