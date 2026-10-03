package com.conversa.userservice.service;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.security.core.userdetails.UserDetails; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final long accessSeconds; private final long refreshSeconds;
 public JwtService(@Value("${security.jwt.secret}") String secret,@Value("${security.jwt.access-expiration-seconds:900}") long accessSeconds,@Value("${security.jwt.refresh-expiration-seconds:604800}") long refreshSeconds){
  if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.accessSeconds=accessSeconds; this.refreshSeconds=refreshSeconds;
 }
 public String accessToken(UserDetails user){return create(user.getUsername(),accessSeconds,"access");}
 public String refreshToken(UserDetails user){return create(user.getUsername(),refreshSeconds,"refresh");}
 private String create(String subject,long seconds,String type){Instant now=Instant.now();return Jwts.builder().subject(subject).claim("type",type).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(seconds))).signWith(key).compact();}
 public String username(String token){return parse(token).getPayload().getSubject();}
 public boolean isRefreshToken(String token){return "refresh".equals(parse(token).getPayload().get("type",String.class));}
 public boolean isValid(String token,UserDetails user){try{return username(token).equals(user.getUsername())&&!parse(token).getPayload().getExpiration().before(new Date());}catch(JwtException|IllegalArgumentException e){return false;}}
 private Jws<Claims> parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);}
}