package com.nd2k.server.security;


import com.nd2k.server.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.secret}")
    private String jwtKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/appKeystore.jks");
            keyStore.load(resourceAsStream, jwtKey.toCharArray());
        } catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException ex) {
            throw new BusinessException("Keystore Exception", "A problem occurred while loading the keystore", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("appKeystore", jwtKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new BusinessException("Keystore Exception", "A problem occurred while retrieving private key from keystore", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateJwtToken(String jwtFromRequest) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwtFromRequest);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("appKeystore").getPublicKey();
        } catch (KeyStoreException ex) {
            throw new BusinessException("Keystore Exeption", "A problem occurred while retrieving public key from keystore", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getEmailFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
