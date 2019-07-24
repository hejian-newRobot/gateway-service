package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 项目名称：gateway-server
 * 包名称:utils
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/22 19:15
 * 修改人：hejian
 * 修改时间：2019/7/22 19:15
 * 修改备注：
 *
 * @author hejian
 */
public class HttpResponseUtils {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtils.class);

    public static Mono<Void> getMonoWithUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String msg = "token is empty or unauthorized,please check request header or access_token arg";
        logger.warn(msg);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] value = msg.getBytes();
        DataBuffer dataBuffer = response.bufferFactory().wrap(value);
        return response.writeWith(Flux.just(dataBuffer));
    }
}
