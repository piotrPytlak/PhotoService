import {createTheme} from "@mui/material";
import {
    Box,
    Container,
    CssBaseline,
    Link,
    TextField,
    Typography
} from "@material-ui/core";
import {ThemeProvider} from "@emotion/react";
import Button from "@mui/material/Button";
import * as React from "react";
import {Alert, Autocomplete} from "@material-ui/lab";
import {useCallback, useContext, useRef, useState} from "react";
import {apiContext} from "../../network/ApiContext";
import validator from "validator/es";


const style = {

    loginPanelMain: {
        top: 0,
        width: '100%',
        height: '100%',
        display: 'flex',
        zIndex: 2,
        position: 'absolute',
        alignItems: 'center',
        flexDirection: 'column',
        justifyContent: 'center'
    },
    sx: {
        width: '300px'
    },

    loginPanel: {
        display: 'flex',
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '5px',
        flexDirection: 'column',
        alignItems: 'center',
        paddingBottom: '5px'
    },
    logIn: {
        display: 'flex',
        justifyContent: 'flex-start',
        flexDirection: 'column',
        flexFlow: 'column wrap',
        flexWrap: 'wrap',
        alignContent: 'center'
    },
    p: {
        fontSize: '17px',
        color: 'black',
        textDecoration: 'underline'
    },

    link: {
        display: 'flex',
        justifyContent: 'center'
    },

    signUpButton: {
        backgroundColor: 'white',
        marginTop: '25px'
    }
}


const theme = createTheme();
const genders = ['male', 'female'];

export default function SignUp() {


    const [error, setError] = useState(false)
    const {register} = useContext(apiContext)
    const [value, setValue] = useState('');
    const [inputValue, setInputValue] = useState('');

    const registerData = useRef({
        email: null,
        userName: null,
        firstName: null,
        lastName: null,
        password: null,
        age: null,
        gender: null
    })


    const getInputAttribute = useCallback((attribute) => registerData.current[attribute].lastChild.firstChild.value, [registerData])

    const handleSubmit = (event) => {
        event.preventDefault();

        //Empty fields check
        Object.keys(registerData.current)
            .forEach(x => registerData.current[x].firstChild.style.color = null);


        const errorValidList = Object.keys(registerData.current).filter(
            x => !!!registerData.current[x].lastChild.firstChild.value);

        if (!validator.isEmail(registerData.current.email.lastChild.firstChild.value)) errorValidList.push('email')
        if (!validator.isStrongPassword(registerData.current.password.lastChild.firstChild.value)) errorValidList.push('password')


        errorValidList.forEach(x => registerData.current[x].firstChild.style.color = 'red')

        if (!errorValidList.length)
            register({
                email: getInputAttribute('email'),
                username: getInputAttribute('userName'),
                firstName: getInputAttribute('firstName'),
                lastName: getInputAttribute('lastName'),
                password: btoa(getInputAttribute('password')),
                age: getInputAttribute('age'),
                gender: getInputAttribute('gender')
            })
                .then(response => {
                    if (response.status !== 200) setError(true);
                })


    }


    const changeGenderHandle = useCallback((event, newValue) => {
        setValue(newValue)
    }, [setValue])

    const changeValueHandle = useCallback((event, newInputValue) => {
        setInputValue(newInputValue)
    }, [setInputValue])

    const inputRenderHandle = useCallback((params) =>
        (<TextField required ref={input => registerData.current.gender = input}
                    {...params} label="Gender"/>), [])

    const chekAge = useCallback((event) => {
        event.target.value = parseInt(event.target.value) <= 100 && parseInt(event.target.value) >= 15 ? event.target.value : '';
    }, [])


    return (
        <div style={style.loginPanelMain}>
            <ThemeProvider theme={theme}>
                <Container component="main" maxWidth="xs" style={style.loginPanel}>
                    <CssBaseline/>
                    <Box
                        sx={{
                            marginTop: 8,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                        }}
                    >
                        <div style={style.logIn}>
                            <Typography component="h1" variant="h5">
                                <b>Sign up to PhotoArt</b>

                            </Typography>
                        </div>
                        <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                            <TextField ref={input => registerData.current.email = input}
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="email"
                                       label="Email Address"
                                       name="email"
                                       autoFocus
                            />
                            <TextField ref={input => registerData.current.userName = input}
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="username"
                                       label="Username"
                                       name="username"
                                       autoFocus
                            />
                            <Autocomplete
                                value={value}
                                onChange={changeGenderHandle}
                                inputValue={inputValue}
                                onInputChange={changeValueHandle}
                                id="controllable-gender"
                                options={genders}
                                // defaultValue={genders[0]}
                                sx={style.sx}
                                renderInput={inputRenderHandle}
                            />

                            <TextField ref={input => registerData.current.firstName = input}
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="firstName"
                                       label="First Name"
                                       name="firstname"
                                       autoFocus
                            />
                            <TextField ref={input => registerData.current.lastName = input}
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="lastname"
                                       label="Last Name"
                                       name="lastname"
                                       autoFocus
                            />
                            <TextField ref={input => registerData.current.password = input}
                                       type="password"
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="password"
                                       label="Password"
                                       name="password"
                                       autoFocus
                            />
                            <TextField ref={input => registerData.current.age = input}

                                       InputProps={{
                                           inputProps: {
                                               max: 100, min: 15
                                           }
                                       }}
                                       type="number"
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="age"
                                       label="Age"
                                       name="age"
                                       autoFocus
                                       onBlur={chekAge}
                            />
                            <Button style={style.signUpButton}
                                    type="file"
                                    size="large"
                                    fullWidth
                                    variant="contained"
                                    sx={{mt: 3, mb: 2}}
                            >
                                Sing up
                            </Button>


                            <Link href="http://localhost:3000/login" variant="body2" style={style.link}>
                                <p style={style.p}> {"Already a PhotoArt member? Log in here."}</p>
                            </Link>


                        </Box>
                    </Box>
                </Container>
            </ThemeProvider>


            {!!error && (() => {
                setTimeout(() => {
                    setError(false)
                }, 3000);
                return (<Alert variant="filled" severity="error" style={{marginTop: '30px'}}>
                    User with this email already exist!
                </Alert>)
            })()}


        </div>
    )
        ;
}