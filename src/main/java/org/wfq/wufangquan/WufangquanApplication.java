package org.wfq.wufangquan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Centripet
 */

@OpenAPIDefinition(
        info = @Info(
                title = "五方圈 API 文档",
                version = "1.0",
                description = "backend application interface"
        )
)
@SpringBootApplication
public class WufangquanApplication {

    public static void main(String[] args) {
        SpringApplication.run(WufangquanApplication.class, args);
    }

}
