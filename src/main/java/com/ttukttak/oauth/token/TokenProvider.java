package com.ttukttak.oauth.token;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

	public String getJwtFromHeader(String token) {
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}
		return null;
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("???????????? ?????? JWT ??????");
			this.jwtMsg = "???????????? ?????? JWT ??????";
		} catch (MalformedJwtException ex) {
			logger.error("???????????? ?????? JWT ??????");
			this.jwtMsg = "???????????? ?????? JWT ??????";
		} catch (ExpiredJwtException ex) {
			logger.error("????????? JWT ??????");
			this.jwtMsg = "????????? JWT ??????";
		} catch (UnsupportedJwtException ex) {
			logger.error("???????????? ?????? JWT ??????");
			this.jwtMsg = "???????????? ?????? JWT ??????";
		} catch (IllegalArgumentException ex) {
			logger.error("???????????? JWT");
			this.jwtMsg = "???????????? JWT";
		}
		return false;
	}
}
