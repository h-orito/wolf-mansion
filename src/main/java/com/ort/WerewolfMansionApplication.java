package com.ort;

import com.ort.dbflute.allcommon.DBFluteBeansJavaConfig;
import com.ort.fw.config.WerewolfMansionWebMvcConfigurer;
import com.ort.fw.config.WerewolfMansionWebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DBFluteBeansJavaConfig.class, WerewolfMansionWebMvcConfigurer.class, WerewolfMansionWebSecurityConfig.class})
public class WerewolfMansionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WerewolfMansionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WerewolfMansionApplication.class);
    }
}
