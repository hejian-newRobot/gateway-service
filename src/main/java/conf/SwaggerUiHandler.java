package conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Optional;

import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:conf
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/2 14:40
 * 修改人：hejian
 * 修改时间：2019/7/2 14:40
 * 修改备注：
 *
 * @author hejian
 */
@Component
public class SwaggerUiHandler implements HandlerFunction<ServerResponse> {

    private final UiConfiguration uiConfiguration;


    public SwaggerUiHandler(@Autowired(required = false) UiConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(
                        Optional.ofNullable(uiConfiguration)
                                .orElse(UiConfigurationBuilder.builder().build())));
    }
}
