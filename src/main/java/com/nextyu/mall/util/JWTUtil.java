package com.nextyu.mall.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public final class JWTUtil {

    private static final String SECRET = "sdfaksdf7986786&^&*^Df";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final Map<String, Object> HEADER_CLAIMS = new HashMap<>();
    static {
        HEADER_CLAIMS.put("typ", "JWT");
        HEADER_CLAIMS.put("alg", "HS256");
    }


    public static String getToken(Long userId) {
        String token = JWT.create().withHeader(HEADER_CLAIMS)
                .withIssuer("summer").withSubject("").withExpiresAt(new DateTime().plusWeeks(1).toDate())
                .withClaim("userId", userId)
                .sign(ALGORITHM);
        return token;
    }

    public static Long getUserId(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM)
                .withIssuer("summer")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asLong();
    }

}
