package authentication.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/2 13:58
 * 修改人：hejian
 * 修改时间：2019/7/2 13:58
 * 修改备注：
 *
 * @author hejian
 */
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    /**
     * 废弃的过滤指令：
     * ServerHttpRequest request = exchange.getRequest();
     * String path = request.getURI().getPath();
     * if (!StringUtils.endsWithIgnoreCase(path, SwaggerResourceProvider.API_URI)) {
     * return chain.filter(exchange);
     * }
     * String basePath = path.substring(0, path.lastIndexOf(SwaggerResourceProvider.API_URI));
     * ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
     * ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
     */
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> chain.filter(exchange);
    }


}
