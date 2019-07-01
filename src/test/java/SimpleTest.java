import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import io.jsonwebtoken.Claims;
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

    @Test
    public void test1() {
        //logger.info("【SecurityOauth2Application】 getCurrentUser1 authenticaiton={}", JsonUtil.toJson(authentication));

//         String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "bearer ");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjE2OTM0MzksInVzZXJfbmFtZSI6IjEyMjIyIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9GU1VTRVIiXSwianRpIjoiODhiODYwMzAtNDM0Yy00NjdmLWI0ZmUtYzg0ODllNmQxOWEzIiwiY2xpZW50X2lkIjoiYW5kcm9pZCIsInNjb3BlIjpbInh4Il19.ut9uIN3QYv4ATlPJx9OLgcVEzAdEb3ZY46FOqqcPh1g";
        Claims claims = Jwts.parser().setSigningKey("hejian".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        Long exp = Long.valueOf(claims.get("exp").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = formatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(exp), ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))));
        logger.info("【SecurityOauth2Application】 getCurrentUser1 exp={}", exp);
        logger.info("【SecurityOauth2Application】 getCurrentUser1 datetime={}", datetime);
    }
}
