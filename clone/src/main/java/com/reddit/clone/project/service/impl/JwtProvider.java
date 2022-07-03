package com.reddit.clone.project.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.reddit.clone.project.exception.SpringRedditException;
import com.reddit.clone.project.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtProvider implements JwtService {

	private KeyStore keyStore; 
	@PostConstruct
    public void init() throws SpringRedditException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "password".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occurred while loading keystore");
        }

    }
	@Override
	public String generateToken(Authentication authentication) throws SpringRedditException {
		 org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
	        return Jwts.builder()
	                .setSubject(principal.getUsername())
	                .signWith(SignatureAlgorithm.RS512, getPrivateKey())
	                .compact();
	}
	private PrivateKey getPrivateKey() throws SpringRedditException {
		 try {
	            return (PrivateKey) keyStore.getKey("springblog", "password".toCharArray());
	        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
	            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
	        }
	}

}
