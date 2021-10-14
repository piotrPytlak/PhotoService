import {StartPageComponent} from "./Components/StartPage/StartPageComponent"
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {LogInPageComponent} from "./Components/LogInPage/LogInPageComponent";
import {SignUpPageComponent} from "./Components/SignUpPage/SignUpPageComponent";
import ApiContext from "./network/ApiContext";

export function AppContent() {

    return (
        <ApiContext>
            <Router>
                <Switch>
                    <Route path="/home">
                        <StartPageComponent/>
                    </Route>

                    <Route path="/login">
                        <LogInPageComponent/>
                    </Route>

                    <Route path="/signup">
                        <SignUpPageComponent/>
                    </Route>
                </Switch>
            </Router>
        </ApiContext>


    );
}

