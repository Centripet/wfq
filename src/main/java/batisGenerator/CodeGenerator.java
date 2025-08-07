package batisGenerator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:43307/wufangquan?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8",
                        "root",
                        "W2316195243"
                )
                .globalConfig(builder -> {
                    builder.author("Centripet")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java/batisGenerator")
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent("batisGenerator")
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("w_project_task")
                            .addTablePrefix("t_", "tbl_")
                            .entityBuilder()
                            .enableColumnConstant() // 添加注解保留字段名
                            .naming(NamingStrategy.underline_to_camel)
                            .naming(NamingStrategy.no_change); // 禁止字段名转驼峰
                })
                .execute();
    }
}