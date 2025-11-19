package app.emporioDaVila.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import app.emporioDaVila.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceGenerator {

    private final JwtProperties jwtProperties; // injeta as propriedades

    public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;

    @Autowired
    public JwtServiceGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public Map<String, Object> gerarPayload(Usuario usuario){
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("userEmail", usuario.getEmail());
        payloadData.put("id", usuario.getId().toString());
        payloadData.put("isAdmin", usuario.getAdmin());
        return payloadData;
    }

    public String generateToken(Usuario usuario) {

        Map<String, Object> payloadData = this.gerarPayload(usuario);

        return Jwts.builder()
                .setClaims(payloadData)
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * jwtProperties.getExpiration()))
                .signWith(getSigningKey(), ALGORITMO_ASSINATURA)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
