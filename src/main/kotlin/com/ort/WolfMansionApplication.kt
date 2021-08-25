package com.ort

import com.ort.app.fw.config.WolfMansionWebMvcConfigurer
import com.ort.dbflute.allcommon.DBFluteBeansJavaConfig
import com.ort.fw.config.WolfMansionWebSecurityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    DBFluteBeansJavaConfig::class,
    WolfMansionWebMvcConfigurer::class,
    WolfMansionWebSecurityConfig::class
)
class WolfMansionApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder? {
        return application.sources(WolfMansionApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<WolfMansionApplication>(*args)
}
