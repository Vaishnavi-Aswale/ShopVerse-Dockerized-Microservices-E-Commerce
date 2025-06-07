package net.javaguides.api_gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.api_gateway.util.JwtUtil;
import net.javaguides.common_lib.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;  // Sử dụng để serialize ApiResponse

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // Kiểm tra xem request có chứa cookie không
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.containsKey(HttpHeaders.COOKIE)) {
                    return this.onError(exchange, "Missing cookies", HttpStatus.UNAUTHORIZED);
                }

                // Lấy token từ cookie
                String token = extractTokenFromCookies(exchange.getRequest());
                if (token == null) {
                    return this.onError(exchange, "Missing or invalid token in cookies", HttpStatus.UNAUTHORIZED);
                }

                try {
                    // Kiểm tra tính hợp lệ của token
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    return this.onError(exchange, "Unauthorized access", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }


    private String extractTokenFromCookies(ServerHttpRequest request) {
        return request.getCookies().getFirst("token") != null ?
                request.getCookies().getFirst("token").getValue() : null;
    }

    // Hàm để trả về phản hồi lỗi tùy chỉnh
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiResponse<String> apiResponse = new ApiResponse<>(errorMessage, httpStatus.value());
        try {
            // Chuyển ApiResponse thành JSON
            byte[] bytes = objectMapper.writeValueAsString(apiResponse).getBytes(StandardCharsets.UTF_8);

            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            // Xử lý nếu gặp lỗi trong quá trình serialize
            return Mono.error(e);
        }
    }

    public static class Config {
    }
}
