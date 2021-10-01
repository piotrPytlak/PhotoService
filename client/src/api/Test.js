import React, { useRef, useState } from 'react';
import { Container } from '@material-ui/core';
import { Input } from "semantic-ui-react";
import { Button } from "semantic-ui-react";

let header = {
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Origin': 'http://localhost:3000/',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Content-Type': 'application/json'

};


export function Test() {

    const cridentials = useRef({
        login: '',
        password: ''
    });

    const album = useRef({
        name: '',
        description: ''
    });


    const handleInputAlbumName = (event) => {
        album.current.name = event.target.value;
    }

    const handleInputAlbumDescription = (event) => {
        album.current.description = event.target.value;
    }


    const handleInputLogin = (event) => {
        cridentials.current.login = event.target.value;
    }

    const handleInputPassword = (event) => {
        cridentials.current.password = event.target.value;
    }

    const handleSubmmitButton = (event) => {
        const jsonData = {

            email: cridentials.current.login,
            password: cridentials.current.password
        };

        fetch(
            'http://localhost:8080/login',
            {
                method: 'POST',
                headers: header,
                credentials: 'include',
                body: JSON.stringify(jsonData)
            }
        )
            .then((response) => response.json())
            .then((result) => {
                console.log('Success:', result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });

    }


    const handleSubmmitButtonAlbum = (event) => {
        const jsonData = {

            "name": album.current.name,
            "description": album.current.description
        };

        fetch(
            'http://localhost:8080/album',
            {
                method: 'POST',
                headers: header,
                credentials: 'include',
                body: JSON.stringify(jsonData),
            }
        )
            .then((response) => response.json())
            .then((result) => {
                console.log('Success:', result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });

    }

    const [selectedFile, setSelectedFile] = useState();


    const changeHandler = (event) => {

        setSelectedFile(event.target.files[0]);
    };


    const handleSubmission = () => {
        const formData = new FormData();

        formData.append("title", "tytul");
        formData.append("albumId", 1);
        formData.append("img", selectedFile);

        console.log(selectedFile);
        delete header['Content-Type'];

        fetch(
            'http://localhost:8080/upload',
            {
                method: 'POST',
                headers: header,
                credentials: 'include',
                body: formData,
            }
        )
            .then((response) => response.json())
            .then((result) => {
                console.log('Success:', result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };


    return (
        <>


            <Container>
                <h1>Logowanie</h1>
                <div>
                    <p>Login:</p>
                    <Input onChange={handleInputLogin} placeholder="Wprowadź login" />

                </div>
                <div>
                    <p>Hasło:</p>
                    <Input type="password" onChange={handleInputPassword} placeholder="Wprowadź hasło" />
                </div>
                <div>
                    <Button onClick={handleSubmmitButton}>Zaloguj</Button>
                </div>
            </Container>

            <Container>
                <div>

                    <input type="file" onChange={changeHandler} />

                    <div>
                        <button onClick={handleSubmission}>Submit</button>
                    </div>
                </div>
            </Container>

            <Container>
                <h1>Album</h1>
                <div>
                    <p>Name:</p>
                    <Input onChange={handleInputAlbumName} placeholder="Wprowadź nazwe" />

                </div>
                <div>
                    <p>Description:</p>
                    <Input onChange={handleInputAlbumDescription} placeholder="Wprowadź opis" />
                </div>
                <div>
                    <Button onClick={handleSubmmitButtonAlbum}>Submit</Button>
                </div>
            </Container>
        </>
    )

}