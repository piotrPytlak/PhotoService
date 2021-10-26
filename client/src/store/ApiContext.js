import {createContext, useRef} from "react";

export const apiContext = createContext(null)

let header = {
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Origin': 'http://localhost:3000/',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Content-Type': 'application/json',

}

const serverUrl = 'http://localhost:8080/';

const PermitType = {
    AUTHENTICATED: false,
    NO_AUTHENTICATED: true
}


export default function ApiContext({children}) {

    const searchBuffor = useRef(undefined)

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


    const loginStatus = async () => {

        const body = {
            method: 'GET',
            headers: header,
            credentials: 'include'
        }

        const response = await fetch(
            serverUrl + 'check',
            body
        )

        if (response.ok) {
            return response.json();
        } else {
            return {authority: null};
        }
    }


    const searchBar = async (param) => {

        const urlSearchParams = new URLSearchParams({username: param})

        const body = {
            method: 'GET',
            header: header,
            credentials: 'include'
        }

        const response = await fetch(
            serverUrl + 'search?' + urlSearchParams,
            body
        )

        if (response.ok)
            return response.json()
        else
            throw new Error("Error!")

    }


    return (
        <apiContext.Provider value={{login, register, searchBar, PermitType, serverUrl, loginStatus}}>
            {children}
        </apiContext.Provider>
    )


}