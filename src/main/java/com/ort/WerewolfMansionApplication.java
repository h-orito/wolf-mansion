package com.ort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.ort.dbflute.allcommon.DBFluteBeansJavaConfig;
import com.ort.fw.config.WerewolfMansionWebMvcConfigurer;
import com.ort.fw.config.WerewolfMansionWebSecurityConfig;

@SpringBootApplication
@Import({ DBFluteBeansJavaConfig.class, WerewolfMansionWebMvcConfigurer.class, WerewolfMansionWebSecurityConfig.class })
public class WerewolfMansionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WerewolfMansionApplication.class, args);
    }
}
