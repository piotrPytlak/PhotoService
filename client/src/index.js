import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {App} from './App.js';
import ApiContext from "./store/ApiContext";

ReactDOM.render(
    <React.StrictMode>
        <ApiContext>
            <App/>
        </ApiContext>
    </React.StrictMode>,
    document.getElementById('root')
);
