package com.uit.tourism_article_management.infrastructure.token;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.uit.tourism_article_management.application.port.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenService implements TokenService {
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(Map<String, Object> claims) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .issuer(this.issuer)
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)));

        claims.forEach(claimsBuilder::claim);

        Payload payload = new Payload(claimsBuilder.build().toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(this.secret));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> introspect(String token) {
        try {
            return this.verify(token);
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> verify(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(this.secret);

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!verified)
            throw new JOSEException("JWT verification failed");
        if (expiryDate.before(new Date()))
            throw new JOSEException("JWT expired");

        return signedJWT.getJWTClaimsSet().getClaims();
    }
}
