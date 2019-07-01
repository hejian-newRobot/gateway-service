package authentication.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import reactor.core.publisher.Mono;

/**
 * 项目名称：SimpleSpringCloudGateway
 * 包名称:authentication.filter
 * 类描述：限流过滤器 针对ip进行限流 Bucket 采用内存保存
 * 创建人：hejian
 * 创建时间：2019/6/28 12:03
 * 修改人：hejian
 * 修改时间：2019/6/28 12:03
 * 修改备注：
 *
 * @author hejian
 */
@ConfigurationProperties(prefix = "rate-limit.bucket")
public class RateLimitByIpGatewayFilter implements GlobalFilter, Ordered {

    /**
     * token bucket capacity
     */
    private int capacity;
    /**
     * a number that is amount of tokens at a time
     */
    private int refillTokens;
    /**
     * the period within {@code tokens} will be fully regenerated
     * unit : second
     */
    private long refillDuration;

    private final Logger logger = LoggerFactory.getLogger(RateLimitByIpGatewayFilter.class);

    private static final Map<String, Bucket> CACHE = new ConcurrentHashMap<>();


    private Bucket createNewBucket() {
        Refill refill = Refill.greedy(refillTokens, Duration.ofSeconds(refillDuration));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public int getOrder() {
        return -1000;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
        Bucket bucket = CACHE.computeIfAbsent(ip, k -> createNewBucket());

        logger.debug("IP: " + ip + "，TokenBucket Available Tokens: " + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }

    public final int getCapacity() {
        return capacity;
    }

    public final void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public final int getRefillTokens() {
        return refillTokens;
    }

    public final void setRefillTokens(int refillTokens) {
        this.refillTokens = refillTokens;
    }

    public final long getRefillDuration() {
        return refillDuration;
    }

    public final void setRefillDuration(long refillDuration) {
        this.refillDuration = refillDuration;
    }
}