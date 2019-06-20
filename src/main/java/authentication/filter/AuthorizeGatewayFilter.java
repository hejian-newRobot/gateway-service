package authentication.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：请求头部是否有Authorization信息 没有凭证信息则直接响应请求
 * 创建人：hejian
 * 创建时间：2019/6/19 10:46
 * 修改人：hejian
 * 修改时间：2019/6/19 10:46
 * 修改备注：
 *
 * @author hejian
 */
@Component
public class AuthorizeGatewayFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(AuthorizeGatewayFilter.class);

    /**
     * token Key
     */
    private static final String AUTHORIZATION = "Authorization";


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("send method {} request to {}", request.getMethod(), request.getURI().toString());
        //将会跳转到OAuth2认证服务 无需提前验证 是否存在AUTHORIZATION
        //其余所有访问别的服务的请求将需要携带AUTHORIZATION请求头或者是请求参数
        if (request.getURI().toString().contains("/oauth2")) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZATION);
        //从header中取token为null
        if (token == null) {
            logger.warn("Authorization token from header is empty");
            token = request.getQueryParams().getFirst(AUTHORIZATION);
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
        logger.info("Authorization token is ok");
        return chain.filter(exchange);
    }
}
