import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {App} from './App.js';
import ApiContext from "./store/ApiContext";
import UserContext from "./store/UserContext";
import {EmoticonInterpreter} from './components/Interpreter/EmoticonInterpreterContext'

ReactDOM.render(
    <React.StrictMode>
        <ApiContext>
            <EmoticonInterpreter>
            <UserContext>
                <App/>
            </UserContext>
            </EmoticonInterpreter>
        </ApiContext>
    </React.StrictMode>,
    document.getElementById('root')
);
