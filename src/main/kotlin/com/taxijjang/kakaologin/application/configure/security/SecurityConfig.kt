package com.taxijjang.kakaologin.application.configure.security

import com.taxijjang.kakaologin.application.configure.filter.KakaoAuthenticationFilter
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyLoginServicePort
import com.taxijjang.kakaologin.domain.repository.AccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val kakaoUserPort: ThirdPartyLoginServicePort,
    private val accountRepository: AccountRepository,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/account/**").authenticated()
//                it.requestMatchers("/swagger-ui/**").permitAll()
//                it.requestMatchers("/auth/kakao").permitAll()
                it.anyRequest().permitAll()
//                it.anyRequest().permitAll()
            }
            .addFilterBefore(
                KakaoAuthenticationFilter(
                    kakaoUserPort = kakaoUserPort,
                    accountRepository = accountRepository,
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
