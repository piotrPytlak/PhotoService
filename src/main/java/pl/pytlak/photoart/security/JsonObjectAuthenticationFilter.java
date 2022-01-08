package pl.pytlak.photoart.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pytlak.photoart.dto.request.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final ObjectMapper objectMapper;

    public  JsonObjectAuthenticationFilter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            String encodedBody = request.getReader().lines().reduce((x, y) -> x + y).orElse("");
            byte[] decodedBody = Base64.getDecoder().decode(encodedBody);
            String body = new String(decodedBody);
            LoginRequest authReq = objectMapper.readValue(body, LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authReq.getEmail(),
                    authReq.getPassword());

            setDetails(request, token);
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
