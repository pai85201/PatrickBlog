package com.paitrick.blog.common.security;
import com.paitrick.blog.common.config.AppProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
@Service public class JwtService{
  private final AppProperties p;
  public JwtService(AppProperties p){
    this.p=p;
  }
  private SecretKey key(){
    return Keys.hmacShaKeyFor(p.getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
  }
  public String create(String username){
    var n=new Date();
    return Jwts.builder().subject(username).issuedAt(n).expiration(new Date(n.getTime()+p.getJwt().getExpirationHours()*3600000)).signWith(key()).compact();
  }
  public String subject(String token){
    return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
  }
}

