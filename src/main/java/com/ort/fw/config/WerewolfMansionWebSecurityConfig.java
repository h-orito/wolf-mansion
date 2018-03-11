package com.ort.fw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ort.fw.security.UserInfoService;

public class WerewolfMansionWebSecurityConfig extends WebSecurityConfigurerAdapter {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private UserInfoService userInfoService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // アクセス権限の設定
                // '/admin/'で始まるURLには、'ADMIN'ロールのみアクセス可
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                // staticディレクトリにある、'/css/','fonts','/js/'は制限なし
                // 一時的に全て許可
                .antMatchers("/app/**", "/lib/**", "/**")
                .permitAll()
                // 他は制限なし
                .anyRequest()
                .authenticated()
                .and()
                // ログイン処理の設定
                .formLogin()
                // ログイン処理のURL
                .loginProcessingUrl("/login")
                .loginPage("/")
                // ログイン成功時の遷移先URL
                .defaultSuccessUrl("/")
                // ログイン失敗時の遷移先URL
                .failureUrl("/login?error=true")
                // usernameのパラメタ名
                .usernameParameter("userId")
                // passwordのパラメタ名
                .passwordParameter("password")
                .permitAll()
                .and()
                // ログアウト処理の設定
                .logout()
                // ログアウト処理のURL
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // ログアウト成功時の遷移先URL
                .logoutSuccessUrl("/")
                // ログアウト時に削除するクッキー名
                .deleteCookies("JSESSIONID")
                // ログアウト時のセッション破棄を有効化
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .rememberMe()
                .key("X7kmptSvar");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //        auth.userDetailsService(userInfoService);
        auth.authenticationProvider(createAuthProvider());
    }

    private AuthenticationProvider createAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userInfoService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
}
