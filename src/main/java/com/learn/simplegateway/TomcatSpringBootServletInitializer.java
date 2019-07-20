package com.learn.simplegateway;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 类描述：
 * 创建人：何健
 * 创建时间：2019-03-14 15:40
 * 修改人：何健
 * 修改时间：2019-03-14 15:40
 * 修改备注：
 *
 * @author hejian
 */
public class TomcatSpringBootServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SimpleGatewayApplication.class);
    }
}
