package com.linmoblog.server.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author : [TongJing]--------GitHub：<a href="https://github.com/defings">...</a>
 * @version : [v1.0]
 * @description : TODO Knife4j接口文档
 * @createTime : [2024/4/6 14:50]
 * @updateUser : [TongJing]
 * @updateTime : [2024/4/6 14:50]
 * @updateRemark : [说明本次修改内容]
 */
@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Memory Blog接口文档")
                        // 接口文档简介
                        .description("一个风格可爱的个人主题博客，欢迎你来体验 Memory 的魅力！")
                        // 接口文档版本
                        .version("1.0版本")
                        // 开发者联系方式
                        .contact(new Contact().name("LinMo && 瞳井")
                                .email("2896311434@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("后台接口文档")
                        .url("lin-mo-blog.vercel.app"));
    }
}
