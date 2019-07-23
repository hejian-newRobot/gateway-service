package authentication.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.security.cert.CertificateException;
import java.time.ZonedDateTime;

import io.jsonwebtoken.ExpiredJwtException;
import utils.HttpResponseUtils;
import utils.JwtUtils;

/**
 * 项目名称：gateway-server
 * 包名称:authentication.filter
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/22 18:28
 * 修改人：hejian
 * 修改时间：2019/7/22 18:28
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class ExistedServiceAuthorizationFilter extends AbstractGatewayFilterFactory {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ExistedServiceAuthorizationFilter.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String accessToken = AuthorizeGatewayFilter.getAccessToken(request);
            ZonedDateTime expired;
            try {
                expired = JwtUtils.getExpired(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
                String message = e.getMessage();
                message = message == null ? StringUtils.EMPTY : message;
                if (e instanceof ExpiredJwtException
                        && message.contains("expired")) {
                    logger.debug("access_token过期了");
                }
                if (e instanceof CertificateException) {
                    logger.error("证书解析异常！！严重问题");
                }
                return HttpResponseUtils.getMonoWithUnauthorized(exchange);
            }
            logger.info("access_token 有效期：" + expired);
            return chain.filter(exchange);
        };
    }
}
