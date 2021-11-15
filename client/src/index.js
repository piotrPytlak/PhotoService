import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {App} from './App.js';
import ApiContext from "./store/ApiContext";
import UserContext from "./store/UserContext";

ReactDOM.render(
    <React.StrictMode>
        <ApiContext>
            <UserContext>
                <App/>
            </UserContext>
        </ApiContext>
    </React.StrictMode>,
    document.getElementById('root')
);
