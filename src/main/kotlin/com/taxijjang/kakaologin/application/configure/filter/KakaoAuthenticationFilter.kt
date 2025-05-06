package com.taxijjang.kakaologin.application.configure.filter

import com.taxijjang.kakaologin.domain.exception.NotFoundAccount
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyAuthProviderType
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyLoginServicePort
import com.taxijjang.kakaologin.domain.repository.AccountRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class KakaoAuthenticationFilter(
    private val kakaoUserPort: ThirdPartyLoginServicePort,
    private val accountRepository: AccountRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")

        if (!token.isNullOrBlank()) {
            try {
                // 1. 카카오에서 유저 정보 조회
                val userInfo = kakaoUserPort.getUserByToken(
                    provider = ThirdPartyAuthProviderType.KAKAO,
                    accessToken = token,
                )

                // 2. 내부 계정 매핑
                val account = accountRepository.findByIdentifier(
                    type = userInfo.provider.toDomain(),
                    value = userInfo.id
                ) ?: throw NotFoundAccount()

                // 3. 인증 객체 설정
                val principal = CustomPrincipal(account.key)

                val authentication = UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    emptyList()
                )

                SecurityContextHolder.getContext().authentication = authentication


            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패: ${e.message}")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}

data class CustomPrincipal(
    val key: UUID,
)