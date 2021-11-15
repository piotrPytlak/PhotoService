import Bar from '../components/Bar'
import BackgroundSlides from '../components/StartingPage/BackgroundSlides'
import CentreText from '../components/StartingPage/CentreText'
import {withRouter} from "react-router-dom";


function HomePage({history}) {

    history.listen((location, action) => {
        // location is an object like window.location
        console.log(action, location.pathname, location.state)
    });
    return (
        <>
            <Bar/>
            <BackgroundSlides/>
            <CentreText/>

        </>
    )
}
export default withRouter(HomePage)