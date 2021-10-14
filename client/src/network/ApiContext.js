import {createContext} from "react";

export const apiContext = createContext(null)

let header = {
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Origin': 'http://localhost:3000/',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Content-Type': 'application/json',

}

const serverUrl = 'http://localhost:8080/'


export default function ApiContext({children}) {

    const login = async (email, password) => {

        const credentials = {
            email: email,
            password: password,
        }

        const body = {
            method: 'POST',
            headers: header,
            credentials: 'include',
            body: btoa(JSON.stringify(credentials)),
        }

        const response = await fetch(
            serverUrl + 'login',
            body,
        )
        if (response.ok) {
            console.log("OK")
            return response.json()
        } else {
            console.log("ERROR")
            return response.status
        }

    }


    const register = async (registerObject) => {
        const body = {
            method: 'POST',
            headers: header,
            credentials: 'include',
            body: JSON.stringify(registerObject),
        }

        return await fetch(
            serverUrl + 'register',
            body,
        )


    }


    return (
        <apiContext.Provider value={{login, register, serverUrl}}>
            {children}
        </apiContext.Provider>
    )


}