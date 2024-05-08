package com.library.service;

import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.library.model.AllUsers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final String SECRET_KEY ="ewjdiwjdiwjdwijdwufduwndiwnwijewjdiwjdiwjdwijdwufduwndiwnwijewjdiwjdiwjdwijdwufduwndiwnwijewjdiwjdiwjdwijdwufduwndiwnwij";
	
	public <T> T extractClaim(String token,Function<Claims,T>resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
		
	}
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	//validate token
	public boolean isValid(String token,UserDetails user) {
		String username = extractUsername(token);
		return username.equals(user.getUsername()) && !isTokenExpired(token);
	}
	
	
private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
	return extractExpiration(token).before(new Date());
	}
//	
	
	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	private Claims extractAllClaims(String token) {
		
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String generateToken(AllUsers user) {
		String token = Jwts
				.builder()
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 24*60*60*1000))
				.signWith(getSigninKey())
				.compact();
		
			return token;
	}
	
	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		System.out.println(Keys.hmacShaKeyFor(keyBytes));
		return Keys.hmacShaKeyFor(keyBytes);
		
	}
}
