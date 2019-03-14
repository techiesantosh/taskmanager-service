package com.web.taskmanager.filter;

import com.web.taskmanager.auth.TokenAuthenticationService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request,
      ServletResponse response,
      FilterChain filterChain)
      throws IOException, ServletException {
    try {
      Authentication authentication = TokenAuthenticationService
          .getAuthentication((HttpServletRequest)request);

      SecurityContextHolder.getContext()
          .setAuthentication(authentication);


    }catch (Exception e){
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request,response);
  }
}
