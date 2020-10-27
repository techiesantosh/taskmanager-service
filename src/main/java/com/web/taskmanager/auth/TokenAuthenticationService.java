package com.web.taskmanager.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

/**
 * To return token and authenticate JWT token
 */
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 9_00_000; // 15 minutes
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    /**
     * @param auth
     * @return
     * @throws IOException
     */
    public static String addAuthentication(Authentication auth) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return JWT;

    }

    /**
     * @param request
     * @return
     */

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        String user = null;
        if (token != null) {
            // parse the token.

            try {
                user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject();


            } catch (Exception e) {

                throw e;

            }

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }
}
