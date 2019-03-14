package com.web.taskmanager.auth;

import static java.util.Collections.emptyList;

import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.repository.ApplicationUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenAuthenticationService {

  static final long EXPIRATIONTIME = 864_000_000; // 10 days
  static final String SECRET = "ThisIsASecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";
  @Autowired
  public UserDetailsServiceImpl userDetailsService;


  public static void addAuthentication(HttpServletResponse res, Authentication auth) throws IOException {
    String JWT = Jwts.builder()
        .setSubject(auth.getName())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    ApplicationUser user =(ApplicationUser) auth.getPrincipal();
    res.getWriter().print(user);
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    String user=null;
    if (token != null) {
      // parse the token.

      try {
         user = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody()
            .getSubject();


      }catch (Exception e){

        throw e;

      }

      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;
    }
   return null;
  }
}
