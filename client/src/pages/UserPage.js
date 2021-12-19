import Bar from '../components/Bar.js'
import Background from '../components/UserPage/Background.js'
import UserBackground from "../components/UserPage/UserBackground";
import {useContext, useEffect} from "react";
import {userContext} from "../store/UserContext";
import {useParams} from "react-router-dom";
import TabBar from "../components/UserPage/TabBar";
import TabBarAboutMe from "../components/UserPage/TabBarAboutMe";
import TabBarPhotos from "../components/UserPage/TabBarPhotos";


export function UserPage() {

    const {userId} = useParams();
    const {loadSelectUser} = useContext(userContext)

    useEffect(() => {
        loadSelectUser(userId)
    }, [userId,loadSelectUser])


    return (
        <>
            <Background>
                <Bar/>
                <UserBackground/>
                <TabBar/>
                <TabBarAboutMe/>
                <TabBarPhotos/>
            </Background>


        </>)
}