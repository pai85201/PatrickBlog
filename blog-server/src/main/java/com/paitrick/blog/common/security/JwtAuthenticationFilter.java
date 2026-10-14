package com.paitrick.blog.common.security;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
@Component public class JwtAuthenticationFilter extends OncePerRequestFilter{
  private final JwtService jwt;
  public JwtAuthenticationFilter(JwtService jwt){
    this.jwt=jwt;
  }
  protected void doFilterInternal(HttpServletRequest r,HttpServletResponse s,FilterChain c)throws ServletException,java.io.IOException{
    var h=r.getHeader("Authorization");
    if(h!=null&&h.startsWith("Bearer "))try{
      var u=jwt.subject(h.substring(7));
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u,null,List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }
    catch(Exception ignored){
    }
    c.doFilter(r,s);
  }
}

