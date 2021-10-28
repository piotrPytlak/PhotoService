import {apiContext} from "./store/ApiContext"
import {BrowserRouter as Router, Switch} from "react-router-dom"

import {LogInPage} from "./pages/LogInPage"
import {SignUpPage} from "./pages/SignUpPage"
import HomePage from "./pages/HomePage"
import SetRoute from "./router/SetRoute";
import {useContext} from "react";
import {DashboardPage} from "./pages/DashboardPage";


export function App() {
    const {PermitType} = useContext(apiContext);

    return (

        <Router>
            <Switch>
                <SetRoute path="/home" freeAccess={true} component={HomePage}/>
                <SetRoute path="/login" alternativePath={"/register"}
                          isPermit={PermitType.NO_AUTHENTICATED}
                          component={LogInPage}/>
                <SetRoute path="/register" alternativePath={"/home"}
                          isPermit={PermitType.NO_AUTHENTICATED}
                          component={SignUpPage}/>
                <SetRoute path="/dashboard"
                          alternativePath={"/login"}
                          component={DashboardPage}/>

            </Switch>
        </Router>


    )
}