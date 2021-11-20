import Bar from '../components/Bar.js'
import Background from '../components/UserPage/Background.js'
import UserBackground from "../components/UserPage/UserBackground";
import {useContext, useEffect} from "react";
import {userContext} from "../store/UserContext";
import {useParams} from "react-router-dom";
import TabBar from "../components/UserPage/TabBar";
import TabBarElements from "../components/UserPage/TabBarElements";


export function UserPage() {

    const {userId} = useParams();
    const {loadSelectUser} = useContext(userContext)

    useEffect(() => {
        loadSelectUser(userId)
    }, [userId])


    return (
        <>
            <Background>
                <Bar/>
                <UserBackground/>
                <TabBar/>
                <TabBarElements/>
            </Background>


        </>)
}