package authentication.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import conf.SwaggerResourceProvider;

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
public class SwaggerRequestFilter extends AbstractGatewayFilterFactory {

//    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    /**
     * 当请求swagger接口文档的url与实际对应服务暴露出来的swagger文档接口不匹配时使用该过滤器修剪url
     */
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, SwaggerResourceProvider.API_URI)) {
                return chain.filter(exchange);
            }
//            int endIndex = path.lastIndexOf(SwaggerResourceProvider.API_URI);
//            String basePath = path.substring(0, endIndex);
            ServerHttpRequest newRequest = request.mutate().path(SwaggerResourceProvider.API_URI).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }


}
