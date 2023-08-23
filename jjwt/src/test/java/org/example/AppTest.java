package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for simple App.
 */

@SpringBootTest
public class AppTest 
{

    //创建token
    @Test
    public void testCreateToken(){
        //擦黄健jwtBuilder对象（定义payload里边的公共属性、加密算法、secret）
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"8888"}
                .setId("8888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxx"}
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret1");
        String token = jwtBuilder.compact();
        System.out.println(token);

        String[] split = token.split("\\.");
        //header
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        //payload
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密，因为有secret
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    //解析token
    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY5Mjc5MjM1N30.CCocyUg2lldfCWviPgIS4o_2NoJ8XqdHKSrJ6deZwsg";
        Claims claims = Jwts.parser()
                .setSigningKey("secret1")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id " + claims.getId());
        System.out.println("subject " + claims.getSubject());
        System.out.println("issuedAt " + claims.getIssuedAt());
    }




    //创建token [有过期时间]
    @Test
    public void testCreateTokenHasExp(){
        long now = System.currentTimeMillis();
        long exp = now + 60*1000;       // 1min后过期
        //擦黄健jwtBuilder对象（定义payload里边的公共属性、加密算法、secret）
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"8888"}
                .setId("8888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxx"}
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret1")
                //设置过期时间
                .setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        System.out.println(token);

        String[] split = token.split("\\.");
        //header
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        //payload
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密，因为有secret
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    //解析token[有过期时间]
    //失效的话会报错
    @Test
    public void testParseTokenHasExp() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY5Mjc5NDQwMCwiZXhwIjoxNjkyNzk0NDYwfQ.SpNUhReemEp5dWyR318n-eyTi_b3D4Xzys8M3BzFRxw";
        Claims claims = Jwts.parser()
                .setSigningKey("secret1")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id " + claims.getId());
        System.out.println("subject " + claims.getSubject());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("签发时间 " + simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间 " + simpleDateFormat.format(claims.getExpiration()));
        System.out.println("当前时间 " + simpleDateFormat.format(new Date()));
    }

    //创建token[自定义申明]
    @Test
    public void testCreateTokenByClaim(){
        //擦黄健jwtBuilder对象（定义payload里边的公共属性、加密算法、secret）
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"8888"}
                .setId("8888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxx"}
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret1")
                //自定义申明
                .claim("key1", "value1")
                .claim("key2", "value2");
                //传入map
//                .addClaims(map)
        String token = jwtBuilder.compact();
        System.out.println(token);

        String[] split = token.split("\\.");
        //header
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        //payload
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密，因为有secret
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    //解析token[自定义申明]
    @Test
    public void testParseTokenByClaim() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTY5Mjc5NTE0OCwia2V5MSI6InZhbHVlMSIsImtleTIiOiJ2YWx1ZTIifQ.gQdIFbAcs0DeAQhsttpaBB3L1A6vbcLTFxOr2raYQlc";
        Claims claims = Jwts.parser()
                .setSigningKey("secret1")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id " + claims.getId());
        System.out.println("subject " + claims.getSubject());
        System.out.println("issuedAt " + claims.getIssuedAt());
        System.out.println("key1 "+ claims.get("key1"));
        System.out.println("key2 "+ claims.get("key2"));
    }




}
