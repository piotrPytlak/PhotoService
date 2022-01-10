import Bar from '../components/Bar.js'
import Background from '../components/UserPage/Background.js'
import UserBackground from "../components/UserPage/UserBackground";
import {useContext, useEffect} from "react";
import {userContext} from "../store/UserContext";
import {useParams} from "react-router-dom";
import TabBar from "../components/UserPage/TabBar";
import TabBarAboutMe from "../components/UserPage/TabBarAboutMe";
import TabBarPhotos from "../components/UserPage/TabBarPhotos";
import SetRoute from "../router/SetRoute";
import TabBarAlbums from "../components/UserPage/TabBarAlbums";
import TabBarAlbumPhotos from "../components/UserPage/TabBarAlbumPhotos";


export function UserPage() {

    const {userId} = useParams();
    const {loadSelectUser} = useContext(userContext)

    useEffect(() => {
        loadSelectUser(userId)
    }, [userId, loadSelectUser])


    return (
        <>
            <Background>
                <Bar/>
                <UserBackground/>
                <TabBar/>
                <SetRoute path="/aboutMe/user/:userId"
                          alternativePath={"/login"}
                          component={TabBarAboutMe}/>

                <SetRoute path="/photos/user/:userId"
                          alternativePath={"/login"}
                          component={TabBarPhotos}/>

                <SetRoute path="/albums/user/:userId"
                          alternativePath={"/login"}
                          component={TabBarAlbums}/>

                <SetRoute path="/album/user/:userId/:albumId"
                          alternativePath={"/login"}
                          component={TabBarAlbumPhotos}/>
            </Background>


        </>)
}