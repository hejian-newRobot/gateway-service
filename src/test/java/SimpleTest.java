import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:PACKAGE_NAME
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/6/27 11:39
 * 修改人：hejian
 * 修改时间：2019/6/27 11:39
 * 修改备注：
 *
 * @author hejian
 */
@RunWith(JUnit4ClassRunner.class)
public class SimpleTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @Value("${security.jwt.publicKey}")
    private String publicKey = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDzzCCAregAwIBAgIEfmlPDTANBgkqhkiG9w0BAQsFADCBlzEQMA4GA1UEBhMH\n" +
            "VW5rbm93bjESMBAGA1UECAwJ5bm/5Lic55yBMQ8wDQYDVQQHDAblub/lt54xJzAl\n" +
            "BgNVBAoMHuWvjOWVhuenkeaKgOiCoeS7veaciemZkOWFrOWPuDEnMCUGA1UECwwe\n" +
            "5a+M5ZWG56eR5oqA6IKh5Lu95pyJ6ZmQ5YWs5Y+4MQwwCgYDVQQDDAPkvZUwHhcN\n" +
            "MTkwNzIyMTUyMzQ4WhcNMTkxMDIwMTUyMzQ4WjCBlzEQMA4GA1UEBhMHVW5rbm93\n" +
            "bjESMBAGA1UECAwJ5bm/5Lic55yBMQ8wDQYDVQQHDAblub/lt54xJzAlBgNVBAoM\n" +
            "HuWvjOWVhuenkeaKgOiCoeS7veaciemZkOWFrOWPuDEnMCUGA1UECwwe5a+M5ZWG\n" +
            "56eR5oqA6IKh5Lu95pyJ6ZmQ5YWs5Y+4MQwwCgYDVQQDDAPkvZUwggEiMA0GCSqG\n" +
            "SIb3DQEBAQUAA4IBDwAwggEKAoIBAQDT7TqFu2BbHwg6UUgNXn3JsTOh8fwC80VY\n" +
            "yioZpDRVG6A/dt7G44A4kuM5GqeZwbJdbejt91FkIY2Fla13Y1dEd6nBi9CsJJd3\n" +
            "D/dYcDen/+ivt31ieNddvZszSnkend3/tUrB5vL98kflXlujaDeVrcBtwuP6siiA\n" +
            "0k3C+P1NrtFlxG9h9Dt4ZzvfiSUexD75Bny3/Q4bJIFk2ISfbf1MUgNA72cZZgXW\n" +
            "SW9HH9bN5B2ur5bynbDlpXAoeE+xNbKZDd35nYVZ2yNm026TaIWJTkdYfzJ8UBOk\n" +
            "iT3+C7Sbqh/BtqyEZGNOIkaD+XTDEBZsBzEYMDX/TC9gQLSLKbz1AgMBAAGjITAf\n" +
            "MB0GA1UdDgQWBBSydo5Ekr7kC3S3HkccHII2dpV3wTANBgkqhkiG9w0BAQsFAAOC\n" +
            "AQEAX1h2lFQowSV7OhS6eZ8S/5mQM+dMPCawXwGXrZyNueHCf17HPQ5+gyNGC00W\n" +
            "f4vBcrZUZ8pBakeN3FqxocfUR7oTLZ3MkpnnflYf3BfTk3Kq9i8jKypqO67ZM8SY\n" +
            "N6Ef5c9XIhSiIq+dhN6jSMclBc5OFqDz2aUnVn8Tn9a6yoOddlrLzpvgJ/3c6v3f\n" +
            "s+QwEsgObDqa5TAsSo9JsyXOQV61qtS87pVHc40eSO0JmEsMBVIoAcr1lEwHDxzd\n" +
            "EZQjeGTnF05FsH3/tC8/K33LWvkuDqpH2P5Djtt5AT4nyKu3ewdRA2yJ1+GJKVMG\n" +
            "68SWK/WJ96ektKpW7jUcyOF3Ag==\n" +
            "-----END CERTIFICATE-----";

    @Test
    public void test1() {
        //logger.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JsonUtil.toJson(authentication));

//         String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "bearer ");
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjM5MDE0NjQsInVzZXJfbmFtZSI6IjE4ODgiLCJhdXRob3JpdGllcyI6WyJST0xFX1dCVVNFUiJdLCJqdGkiOiI5ZGI2MTEwNi05YTViLTRjOTYtOWNhMS0yZGE2ODBhNWEwM2YiLCJjbGllbnRfaWQiOiJhbmRyb2lkIiwic2NvcGUiOlsieHgiXX0.H1jJpGt7EH6apcWF08HoxV0Z7BwLfAy7zNaW-tof5xaq5zqPxVbrq2H4mwcK4g3BpGbEQkgdT384M2z33cFqMsyRAAl7a8HQuuDDCD7RcXprSLPZI3lMA29Gzbj5f73JETWYtvQAkiq8Bx4GKZFRdDB-hHk1imHIry2Sd0qPt11eeY7OQbZrjuqjUVtFozgHUJMUX0nayP_6YAOL6eA1wqJbfkwKP1Cy9YtMjHYbHITpiXv8H1ILGtq5E7u07v5GW7zA8LPlmUOmOr_YTR6emVqD1hbD35oR6xe8hs_FQK856ZqCyQWIeZPc6Lb6_XkIffkj1KH3s1apn8rtl7l9yg";
        Claims claims;
        try {
            PublicKey publicKey = CertificateFactory.getInstance("X.509")
                    .generateCertificate(new ByteArrayInputStream(this.publicKey.getBytes()))
                    .getPublicKey();
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            String message = e.getMessage();
            message = message == null ? StringUtils.EMPTY : message;
            if (e instanceof ExpiredJwtException
                    && message.contains("expired")) {
                System.out.println("hahah");
            }
            return;
        }
        assert claims != null;
        Long exp = Long.valueOf(claims.get("exp").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(exp), ZoneOffset.UTC));
        logger.info("【SecurityOauth2Application】 getCurrentUser1 exp={}", exp);
        logger.info("【SecurityOauth2Application】 getCurrentUser1 datetime={}", datetime);
    }
}
