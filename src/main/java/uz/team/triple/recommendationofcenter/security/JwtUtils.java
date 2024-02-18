package uz.team.triple.recommendationofcenter.security;

import org.springframework.stereotype.Service;

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Date;
//
@Service
public class JwtUtils {
//
//    public String generateAccessToken(@NonNull String username) {
//        Date date = new Date();
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(date)
//                .setIssuer("")
//                .setExpiration(new Date(date.getTime() + 1000 * 60 * 2))
//                .signWith(signKey(), SignatureAlgorithm.HS384)
//                .compact();
//    }
//
//    public boolean isTokenValid(@NonNull String token) {
//        try {
//            Claims claims = getClaims(token);
//            Date expiration = claims.getExpiration();
//            return expiration.after(new Date());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    private Claims getClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(signKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key signKey() {
//        byte[] bytes = Decoders.BASE64.decode(generateSecretKey());
//        return Keys.hmacShaKeyFor(bytes);
//    }
//
//    private String generateSecretKey() {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(256);
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] keyBytes = secretKey.getEncoded();
//            return bytesToHex(keyBytes);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String bytesToHex(byte[] bytes) {
//        StringBuilder result = new StringBuilder();
//        for (byte b : bytes) {
//            result.append(String.format("%02X", b));
//        }
//        return result.toString();
//    }

}
