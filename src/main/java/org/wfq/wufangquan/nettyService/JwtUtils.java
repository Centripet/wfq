package org.wfq.wufangquan.nettyService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.wfq.wufangquan.entity.JwtPayload;
import org.wfq.wufangquan.service.JwtService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static final String SECRET_KEY = "your-secret-key"; // 建议存配置文件
    private final JwtService jwtService;

    public String getUserIdFromToken(String token) {
        try {
            System.out.println(token);
            JwtPayload payload = jwtService.extractPayload(token);
            System.out.println(payload);
            return payload.getUser_id();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException("验证失败", e);
        }
    }
}