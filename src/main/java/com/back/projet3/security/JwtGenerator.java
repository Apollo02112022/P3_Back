package com.back.projet3.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Component permet à spring de detecter notre class custom
 *            et on pourra donc l'injecter ou l'on en a besoin grâce
 *            à @Autowired
 */
@Component
public class JwtGenerator {

  // ADD_ROLE_TO_USER_AND_TOKEN
  @Autowired
  CustomUserDetails customUserDetails;

  public String generateToken(String pseudo, Number id) {

    // claims.put("role", role); // add role claim
    // Map<String, String> claims = new HashMap<String, String>();
    // claims.put("role", "user");
    // ADD_ROLE_TO_USER_AND_TOKEN
    // UserDetails userDetails = customUserDetails.loadUserByUsername(username);
    Claims claims = Jwts.claims().setSubject(pseudo);
    claims.put("userId", id);
    claims.put("pseudo",pseudo);
    Date currentDate = new Date();
    // 86,400,000 = 24H en millliseconds
    Date expireDate = new Date(currentDate.getTime() + 86400000);
    
    System.out.println("@@@@@@@@@@@@@@@@@@@  Date de génération de Token   " + expireDate.toString());

    // Implémentation à récupérer d'internet
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, "secret") 
        .compact();
    return token;
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
      return true;
    } catch (Exception err) {
      throw new AuthenticationCredentialsNotFoundException("Le token n'est pas bon ou est expiré");
    }
  }

  public String getUserNameFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey("secret")
        .parseClaimsJws(token)
        .getBody();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@" + claims.getSubject());
    return claims.getSubject();
  }

}