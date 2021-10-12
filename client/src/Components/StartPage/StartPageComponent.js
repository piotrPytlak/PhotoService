import BarComponent from './BarComponent.js'
import {Slides} from './Slides.js'
import {TextCentreComponent} from './TextCentreComponent'

export function StartPageComponent(props) {

    return (
        <>
            <BarComponent/>
            <Slides/>
            <TextCentreComponent/>
        </>
    )
}