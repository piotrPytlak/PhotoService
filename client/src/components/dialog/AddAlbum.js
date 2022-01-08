import {
    Button,
    Dialog,
    DialogTitle,
    TextField,
    Typography,
} from "@mui/material";
import React, {
    useCallback,
    useContext,
    useEffect,
    useRef,
    useState,
} from "react";

import {apiContext} from "../../store/ApiContext";
import {Alert} from "@material-ui/lab";
import {useHistory} from "react-router-dom";

const style = {
    textField: {
        width: "80%",
        marginBottom: "10px",
    },

    textDiv: {
        display: "flex",
        justifyContent: "flex-start",
        flexDirection: "column",
        alignItems: "center",
    },
    dialog: {
        minHeight: "80vh",
        maxHeight: "80vh",
    },
};
const message = {
    200: <>Added album</>,
    409: <>Album with this name exists</>
}

export default function AddAlbum(props) {
    const {onClose} = props;
    const {addAlbum} = useContext(apiContext);
    const [response, setResponse] = useState({responseCode: undefined})
    const history = useHistory()

    const albumObject = useRef({
        name: "",
        description: "",
    });

    const nameRef = useRef();
    const descriptionRef = useRef();

    const submmitHandler = (event) => {

        event.preventDefault();

        addAlbum({
            name: nameRef.current.value,
            description: descriptionRef.current.value,
        }).then(code => {
                setResponse({responseCode: code});
                setTimeout(() => onClose(), 2000);

            }
        )

        ;
    };

    const onNameHandler = useCallback(
        (event) => (albumObject.current.name = event.target.value),
        [albumObject]
    );
    const onDescriptionHandler = useCallback(
        (event) => (albumObject.current.description = event.target.value),
        [albumObject]
    );

    return (
        <>
            <Dialog open={true} onClose={onClose} fullWidth={true}>
                <DialogTitle titleStyle={{textAlign: "center"}}>
                    <Typography variant="h5" align="center">
                        Add album
                    </Typography>
                </DialogTitle>

                <div
                    style={{
                        display: "flex",
                        justifyContent: "flex-start",
                        flexDirection: "column",
                        alignItems: "center",
                    }}
                >
                    <TextField
                        onChange={onNameHandler}
                        inputRef={nameRef}
                        id="filled-basic"
                        label="Name"
                        variant="filled"
                        style={{width: "80%", marginBottom: "10px"}}
                    />
                    <TextField
                        onChange={onDescriptionHandler}
                        inputRef={descriptionRef}
                        id="filled-basic"
                        label="Description"
                        variant="filled"
                        style={{width: "80%", marginBottom: "10px"}}
                    />
                </div>
                {!!response.responseCode &&
                <Alert variant="filled" severity={
                    response.responseCode === 200 ? "success" : "error"
                } style={{marginTop: '30px'}}>
                    {message[response.responseCode]}
                </Alert>
                }
                <Button
                    onClick={submmitHandler}
                    variant="contained"
                    style={{backgroundColor: "black"}}
                >
                    Submit
                </Button>
            </Dialog>
        </>
    );
}
