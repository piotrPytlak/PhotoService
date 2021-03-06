import { createTheme } from "@mui/material";
import {
  Box,
  Checkbox,
  Container,
  CssBaseline,
  FormControlLabel,
  Link,
  TextField,
  Typography,
} from "@material-ui/core";
import { ThemeProvider } from "@emotion/react";
import Button from "@mui/material/Button";
import * as React from "react";
import { useContext, useRef, useState } from "react";
import { apiContext } from "../../store/ApiContext";
import { userContext } from "../../store/UserContext";
import { Redirect } from "react-router-dom";

const style = {
  loginPanelMain: {
    top: 0,
    width: "100%",
    height: "100%",
    display: "flex",
    zIndex: 2,
    position: "absolute",
    alignItems: "center",
    flexDirection: "column",
    justifyContent: "center",
  },

  loginPanel: {
    display: "flex",
    backgroundColor: "white",
    padding: "20px",
    borderRadius: "5px",
    flexDirection: "column",
    alignItems: "center",
    paddingBottom: "5px",
  },
  logIn: {
    display: "flex",
    justifyContent: "flex-start",
    flexDirection: "column",
    flexFlow: "column wrap",
    flexWrap: "wrap",
    alignContent: "center",
  },
  p: {
    fontSize: "17px",
    color: "black",
    textDecoration: "underline",
  },

  link: {
    display: "flex",
    justifyContent: "center",
  },

  logInButton: {
    backgroundColor: "#fea796",
  },
};

const theme = createTheme();

export default function LoginForm() {
  const { login } = useContext(apiContext);
  const refEmail = useRef();
  const refPassword = useRef();
  const { loadUser, currentUser } = useContext(userContext);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    login(refEmail.current.value, refPassword.current.value).then((status) => {
      status === 200 &&
        loadUser().then(() => {
          setIsAuthenticated(true);
        });
    });
  };

  return (
    <>
      <div style={style.loginPanelMain}>
        <ThemeProvider theme={theme}>
          <Container component="main" maxWidth="xs" style={style.loginPanel}>
            <CssBaseline />
            <Box
              sx={{
                marginTop: 8,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <div style={style.logIn}>
                <Typography component="h1" variant="h5">
                  <b>Log in to PhotoArt</b>
                </Typography>
              </div>
              <Box
                component="form"
                onSubmit={handleSubmit}
                noValidate
                sx={{ mt: 1 }}
              >
                <TextField
                  inputRef={refEmail}
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  autoFocus
                />
                <TextField
                  inputRef={refPassword}
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                />
                <FormControlLabel
                  control={<Checkbox value="remember" color="primary" />}
                  label="Remember me"
                />
                <Button
                  style={style.logInButton}
                  type="file"
                  size="large"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                >
                  Log in
                </Button>

                <Link
                  href="http://localhost:3000/register"
                  variant="body2"
                  style={style.link}
                >
                  <p style={style.p}> {"Don't have an account? Sign Up!"}</p>
                </Link>
              </Box>
            </Box>
          </Container>
        </ThemeProvider>
      </div>

      {!!isAuthenticated && <Redirect to={`/aboutMe/user/${currentUser.userId}`} />}
    </>
  );
}
