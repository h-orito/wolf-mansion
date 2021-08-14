package com.ort;

import com.ort.dbflute.allcommon.DBFluteBeansJavaConfig;
import com.ort.fw.config.WolfMansionWebMvcConfigurer;
import com.ort.fw.config.WolfMansionWebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DBFluteBeansJavaConfig.class, WolfMansionWebMvcConfigurer.class, WolfMansionWebSecurityConfig.class})
public class WolfMansionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WolfMansionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WolfMansionApplication.class);
    }
}
