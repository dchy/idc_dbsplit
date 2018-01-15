package hyby.td.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by 11019 on 17.10.27.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hyby.td.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact=new Contact("项目底层",
                "http://www.rxjy.com","dingcy@rxjy.com");
        return new ApiInfoBuilder()
                .title("各部门数据库层面拆分")
                .description("此api提供项目底层在各部门数据库层面拆分所用到的接口;type=2(设计已签)")
                .termsOfServiceUrl("http://www.baidu.com")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
