package com.ttukttak.oauth.token;

import java.util.Date;

import javax.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ttukttak.common.config.AppProperties;
import com.ttukttak.oauth.entity.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.Setter;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private AppProperties appProperties;
	@Getter
	@Setter
	private String jwtMsg;

	public TokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public String createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		return Jwts.builder()
			.setSubject(Long.toString(userPrincipal.getId()))
			.setIssuedAt(new Date())
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(appProperties.getAuth().getTokenSecret())
			.parseClaimsJws(token)
			.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) throws AuthException {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			this.jwtMsg = "유효하지 않은 JWT 서명";
			throw new AuthException("유효하지 않은 JWT 서명");
		} catch (MalformedJwtException ex) {
			this.jwtMsg = "유효하지 않은 JWT 토큰";
			throw new AuthException("유효하지 않은 JWT 토큰");
		} catch (ExpiredJwtException ex) {
			this.jwtMsg = "만료된 JWT 토큰";
			throw new AuthException("만료된 JWT 토큰");
		} catch (UnsupportedJwtException ex) {
			this.jwtMsg = "지원하지 않는 JWT 토큰";
			throw new AuthException("지원하지 않는 JWT 토큰");
		} catch (IllegalArgumentException ex) {
			this.jwtMsg = "비어있는 JWT";
			throw new AuthException("비어있는 JWT");
		}
	}
}
