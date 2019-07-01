package authentication.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/6/28 15:02
 * 修改人：hejian
 * 修改时间：2019/6/28 15:02
 * 修改备注：
 *
 * @author hejian
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RateLimitByIpGatewayFilterTests {

    @Autowired
    private RateLimitByIpGatewayFilter rateLimitByIpGatewayFilter;

    @Test
    public void testProperties() {
        int capacity = rateLimitByIpGatewayFilter.getCapacity();
        int refillTokens = rateLimitByIpGatewayFilter.getRefillTokens();
        long refillDuration = rateLimitByIpGatewayFilter.getRefillDuration();
        System.out.printf("capacity = %d", capacity);
    }
}
