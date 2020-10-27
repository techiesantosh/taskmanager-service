package com.web.taskmanager.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.taskmanager.auth.TokenAuthenticationService;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.JsonResponse;
import com.web.taskmanager.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Filter to authenticate credential's and return JWT token on success
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    /**
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ApplicationUser creds = objectMapper
                .readValue(req.getInputStream(), ApplicationUser.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    /**
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String jwt = TokenAuthenticationService
                .addAuthentication(auth);

        ApplicationUser user = (ApplicationUser) auth.getPrincipal();
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        JsonResponse jsonResponse = new JsonResponse("200", "SUCCESS", userDTO);

        ObjectMapper mapper = new ObjectMapper();

        res.setContentType("application/json");
        res.getWriter().print(mapper.writeValueAsString(jsonResponse));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    /**
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        JsonResponse jsonResponse = new JsonResponse("403", "ERROR", failed.getMessage());
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(mapper.writeValueAsString(jsonResponse));
    }
}
