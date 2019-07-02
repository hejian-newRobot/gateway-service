package conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:conf
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/2 14:13
 * 修改人：hejian
 * 修改时间：2019/7/2 14:13
 * 修改备注：
 *
 * @author hejian
 */
@Component
public class RouterSwaggerConfiguration {

    private final SwaggerResourceHandler swaggerResourceHandler;
    private final SwaggerSecurityHandler swaggerSecurityHandler;
    private final SwaggerUiHandler swaggerUiHandler;

    @Autowired
    public RouterSwaggerConfiguration(SwaggerResourceHandler swaggerResourceHandler,
                                      SwaggerSecurityHandler swaggerSecurityHandler,
                                      SwaggerUiHandler swaggerUiHandler) {
        this.swaggerResourceHandler = swaggerResourceHandler;
        this.swaggerSecurityHandler = swaggerSecurityHandler;
        this.swaggerUiHandler = swaggerUiHandler;
    }

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/swagger-resources")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);

    }
}
