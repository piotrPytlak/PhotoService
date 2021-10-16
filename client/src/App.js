import ApiContext from "./store/ApiContext";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";

import {LogInPage} from "./pages/LogInPage";
import {SignUpPage} from "./pages/SignUpPage";
import {HomePage} from "./pages/HomePage";

export function App() {

    return (
        <ApiContext>
            <Router>
                <Switch>
                    <Route path="/home">
                        <HomePage/>
                    </Route>

                    <Route path="/login">
                        <LogInPage/>
                    </Route>

                    <Route path="/signup">
                        <SignUpPage/>
                    </Route>
                </Switch>
            </Router>
        </ApiContext>
    )
}



