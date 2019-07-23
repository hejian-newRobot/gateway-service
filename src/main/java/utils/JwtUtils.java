package utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * 项目名称：gateway-server
 * 包名称:utils
 * 类描述：jwt的工具类
 * 创建人：hejian
 * 创建时间：2019/7/22 18:56
 * 修改人：hejian
 * 修改时间：2019/7/22 18:56
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class JwtUtils {

    public static ZonedDateTime getExpired(@NonNull String jwtToken) throws Exception {
        Objects.requireNonNull(jwtToken);
        Claims claims = getClaims(jwtToken);
        long exp = Long.parseLong(claims.get("exp").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(exp),
                ZoneOffset.UTC));
        return LocalDate.parse(datetime, formatter)
                .atStartOfDay(ZoneId.systemDefault());
    }

    private static Claims getClaims(@NonNull String jwtToken) throws Exception {
        Objects.requireNonNull(jwtToken);
        return Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private static PublicKey getPublicKey() throws CertificateException {
        String certificate = ClassPathFileUtils.getStringFromTxt("certificate.txt");
        return CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certificate.getBytes()))
                .getPublicKey();
    }

}
