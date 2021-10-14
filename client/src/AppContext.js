import {StartPageComponent} from "./Components/StartPage/StartPageComponent"
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {LogInPageComponent} from "./Components/LogInPage/LogInPageComponent";

export function AppContext() {

    return (<Router>
            <Switch>
                <Route path="/home">
                    <StartPageComponent/>
                </Route>

                <Route path="/login">
                    <LogInPageComponent/>
                </Route>
            </Switch>
        </Router>

    );
}

