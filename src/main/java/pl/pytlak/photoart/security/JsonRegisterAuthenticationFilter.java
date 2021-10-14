package pl.pytlak.photoart.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JsonRegisterAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MappingJackson2HttpMessageConverter httpMessageConverter;

    public JsonRegisterAuthenticationFilter() {
        setFilterProcessesUrl("/register");
    }


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String body = request.getReader().lines().reduce((sum, x) -> sum + x).orElse("");
        RegisterRequest registerRequest = objectMapper.readValue(body, RegisterRequest.class);
        Optional<User> registerUser = authenticationService.register(registerRequest);


        UsernamePasswordAuthenticationToken token = registerUser.map(user -> new UsernamePasswordAuthenticationToken(
                registerRequest.getEmail(),
                registerRequest.getPassword())).orElse(new UsernamePasswordAuthenticationToken(null, null));
        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);


    }

}
