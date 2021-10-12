import {StartPageComponent} from "./Components/StartPage/StartPageComponent"
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {LogInPageComponent} from "./Components/LogInPage/LogInPageComponent";

export function AppContext() {

    return (<Router>
            <Switch>
                <Route path="/test">
                    <StartPageComponent/>
                </Route>

                <Route path="/testt">
                    <LogInPageComponent/>
                </Route>
            </Switch>
        </Router>

    );
}

