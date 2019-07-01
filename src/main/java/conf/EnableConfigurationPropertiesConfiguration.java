package conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import authentication.filter.RateLimitByIpGatewayFilter;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:conf
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/6/28 14:29
 * 修改人：hejian
 * 修改时间：2019/6/28 14:29
 * 修改备注：
 *
 * @author hejian
 */
@Configuration
public class EnableConfigurationPropertiesConfiguration {

    @Bean
    public RateLimitByIpGatewayFilter rateLimitByIpGatewayFilter() {
        return new RateLimitByIpGatewayFilter();
    }
}
