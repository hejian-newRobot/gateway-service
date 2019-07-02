package authentication.filter;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：access_token 黑名单 全局过滤器
 * 创建人：hejian
 * 创建时间：2019/6/27 18:36
 * 修改人：hejian
 * 修改时间：2019/6/27 18:36
 * 修改备注：
 *
 * @author hejian
 */
public class AccessTokenBlackTable implements Job {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenBlackTable.class);

    private static final List<String> BLACK_TABLE = new ArrayList<>();

    public static boolean has(String token) {
        if (StringUtils.isEmpty(token)) {
            logger.debug("invalidate method : token is empty");
            return false;
        }
        return BLACK_TABLE.stream()
                .anyMatch(blackTable -> blackTable.equals(token));
    }

    public static boolean invalidate(String token) {
        if (StringUtils.isEmpty(token)) {
            logger.debug("invalidate method : token is empty");
            return false;
        }
        if (has(token)) {
            logger.debug("token has at token black table");
            return false;
        }

        return BLACK_TABLE.add(token);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        byte[] signingKey = "hejian".getBytes(StandardCharsets.UTF_8);
        List<String> removeList = BLACK_TABLE.stream().filter(token -> {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token);
            //解析失败返回null 没有解析成功说明jwt失效 失效则是需要被移除的token
            return claimsJws == null;
        }).collect(Collectors.toList());
        BLACK_TABLE.removeAll(removeList);
    }
}
