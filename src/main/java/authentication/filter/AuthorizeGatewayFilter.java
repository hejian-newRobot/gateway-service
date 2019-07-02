package authentication.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Optional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：auth请求直接放行 ，其他请求则验证请求头部是否有Authorization信息 没有凭证信息查看请求参数是否有token 没则直接响应请求 有就直接放行
 * 创建人：hejian
 * 创建时间：2019/6/19 10:46
 * 修改人：hejian
 * 修改时间：2019/6/19 10:46
 * 修改备注：
 *
 * @author hejian
 */
@Component
@ConfigurationProperties("filter.gateway.authorize")
public class AuthorizeGatewayFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeGatewayFilter.class);

    /**
     * token Key
     */
    private static final String AUTHORIZATION = "Authorization";

    /**
     * access_token key
     */
    private static final String ACCESS_TOKEN = "access_token";

    /**
     * a string list that need to skip currently filter
     */
    private String[] ignoredSubSequenceOfUrl;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("send method {} request to {}", request.getMethod(), request.getURI().toString());
        //将会跳转到OAuth2认证服务 无需提前验证 是否存在AUTHORIZATION
        //其余所有访问别的服务的请求将需要携带AUTHORIZATION请求头或者是请求参数
        Object referer = request.getHeaders().get("Referer");
        if (checkIgnoredSequences(ignoredSubSequenceOfUrl,
                Optional.ofNullable(referer).isPresent()
                        ? referer.toString()
                        : StringUtils.EMPTY)) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZATION);
        //从header中取token为null
        if (token == null) {
            logger.warn("Authorization token from header is empty");
            token = request.getQueryParams().getFirst(ACCESS_TOKEN);
        }
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token)) {
            String msg = "token is empty ,please check request header";
            logger.warn(msg);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] value = msg.getBytes();
            DataBuffer dataBuffer = response.bufferFactory().wrap(value);
            return response.writeWith(Flux.just(dataBuffer));
        }
        logger.info("Authorization token is not empty");

        logger.info("Authorization token is ok");
        return chain.filter(exchange);
    }

    private boolean checkIgnoredSequences(String[] ignoredSequences, String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        String[] strs = url.split("\\?");
        String urlPath = strs.length > 0 ? strs[0] : null;
        if (StringUtils.isEmpty(urlPath)) {
            return false;
        }
        boolean present = Optional.ofNullable(ignoredSequences).isPresent();
        if (!present || ignoredSequences.length == 0) {
            return false;
        }
        return Arrays.stream(ignoredSequences).anyMatch(urlPath::contains);
    }

    public final String[] getIgnoredSubSequenceOfUrl() {
        return ignoredSubSequenceOfUrl;
    }

    public final void setIgnoredSubSequenceOfUrl(String[] ignoredSubSequenceOfUrl) {
        this.ignoredSubSequenceOfUrl = ignoredSubSequenceOfUrl;
    }
}
