package com.sample.board.web.security

import com.sample.board.domain.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


/**
 * Spring Security設定
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val loginService: LoginService
) : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {

        // セキュリティ設定を無視するリクエスト設定
        web.ignoring()
            // 静的リソースに対するアクセスはセキュリティ設定を無視する
            .antMatchers("/css/**", "/images/**", "/js/**", "/plugins/**", "/unify/**")
    }

    override fun configure(http: HttpSecurity) {

        // 認可の設定
        http.authorizeRequests()
            // ログイン、セキュリティ認証、エラー画面以外は認証が必要
            .antMatchers("/login/**", "/security/**", "/api/**", "/error/**", "/user/**").permitAll()
            //.mvcMatchers("/maintenance/**").hasAuthority("ADMIN")
            //.antMatchers("/maintenance/").hasRole("ADMIN")	// ADMIN権限がないとアクセスできないURL
            .anyRequest().authenticated()        // /js、/css以外へのアクセスに対しては認証を要求
            .and()
            // ログイン設定
            .formLogin()
            .loginPage("/login")    // 認証元のログイン画面
            .loginProcessingUrl("/security/login")            // 認証URL
            .usernameParameter("userId")            // ユーザ名のリクエストパラメータ名
            .passwordParameter("password")            // パスワードのリクエストパラメータ名
            .defaultSuccessUrl("/board", true)        // 認証成功時の遷移先URL
            .failureUrl("/error/403")    // 認証失敗時の遷移先URL
            .permitAll()
            .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        // ログアウト設定
        http.logout()
            // 認証URL
            .logoutRequestMatcher(AntPathRequestMatcher("/security/logout"))
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<LoginService>(loginService)
            .passwordEncoder(BCryptPasswordEncoder())
    }
}