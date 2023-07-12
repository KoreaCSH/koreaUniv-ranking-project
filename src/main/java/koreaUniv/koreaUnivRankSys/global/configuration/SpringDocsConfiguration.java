package koreaUniv.koreaUnivRankSys.global.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocsConfiguration {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("EduGrow API")
                .version("v1")
                .description("모르는 부분 있으면 카톡 주세요!")
                .contact(new Contact()
                        .name("Choi, Seung Heon")
                        .url("https://github.com/KoreaCSH"));


        return new OpenAPI().info(info);
    }

}
