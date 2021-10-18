import React, {useContext} from 'react';
import {Route} from 'react-router-dom';
import PathByPermission from "./PathByPermission"
import {apiContext} from "../store/ApiContext";
import history from './history'


const SetRoute = (props) => {

    const {PermitType} = useContext(apiContext);
    const {
        path,
        alternativePath,
        isPermit,
        component: Component,
        freeAccess,
        role
    } = {freeAccess: false, isPermit: PermitType.AUTHENTICATED, role: ['ROLE_ADMIN', 'ROLE_USER'], ...props}


    return (
        <Route path={path} history={history}>
            <PathByPermission alternativePath={alternativePath} role={role} isPermit={isPermit} freeAccess={freeAccess}>
                <Component/>
            </PathByPermission>
        </Route>

    )
}

export default SetRoute;