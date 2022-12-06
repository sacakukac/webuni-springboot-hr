package hu.webuni.hr.saca.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

	private static final String AUTH = "auth";
	private static final String ISSUER = "HrApp";
	private Algorithm alg = Algorithm.HMAC256("mysecret");
	
	public String createJwtToken(UserDetails principal) {
		return JWT.create()
			.withSubject(principal.getUsername())
			.withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
			.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
			.withIssuer(ISSUER)
			.sign(Algorithm.HMAC256("mysecret"));
		
	}

	public UserDetails parseJwt(String jwtToken) {
		
		DecodedJWT decodedJwt = JWT.require(alg)
			.withIssuer(ISSUER)
			.build()
			.verify(jwtToken);
		
		return new User(decodedJwt.getSubject(), "dummy",     //dummy: mindegy, már ellenőríztük, később már nincs rá szükség
				decodedJwt.getClaim(AUTH).asList(String.class)
				.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));  
	}

	
}
