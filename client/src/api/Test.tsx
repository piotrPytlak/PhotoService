import React, { useState } from 'react';


let header = {
    //'Content-Type': 'application/json',
    'Access-Control-Allow-Credentials': 'true',
    'Access-Control-Allow-Origin': 'http://localhost:3000/',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS'

};

      

export function Test() {

    const [selectedFile, setSelectedFile] = useState<File>();

    const changeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedFile(event.target.files?.item(0) || undefined);
    };

    const handleSubmission = () => {
        const formData = new FormData();


        if (selectedFile) {
            formData.append('img', selectedFile);
        }


        fetch(
            'http://localhost:8080/test',
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
        <div>
            <input type="file" name="file" onChange={changeHandler} />

            <div>
                <button onClick={handleSubmission}>Submit</button>
            </div>
        </div>
    )

}