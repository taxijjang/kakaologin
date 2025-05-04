package com.taxijjang.kakaologin.application.port.out

import com.taxijjang.kakaologin.domain.entity.port.out.ThirdPartyAuthProviderType
import com.taxijjang.kakaologin.domain.entity.port.out.ThirdPartyLoginServicePort
import com.taxijjang.kakaologin.domain.entity.port.out.ThirdPartyUserAttribute
import org.springframework.stereotype.Component

@Component
class ThirdPartLoginPortImpl: ThirdPartyLoginServicePort {
    override fun getUserByToken(tenantId: String, provider: ThirdPartyAuthProviderType, accessToken: String): ThirdPartyUserAttribute {
        return when (provider) {
            ThirdPartyAuthProviderType.KAKAO -> TODO()
            else -> throw IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다.")
        }
    }
}


