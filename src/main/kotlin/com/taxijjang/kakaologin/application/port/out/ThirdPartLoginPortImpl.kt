package com.taxijjang.kakaologin.application.port.out

import com.taxijjang.kakaologin.domain.port.out.ThirdPartyAuthProviderType
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyLoginServicePort
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyUserAttribute
import org.springframework.stereotype.Component

@Component
class ThirdPartLoginPortImpl(
    val kakaoApiClient: KakaoApiClient,
) : ThirdPartyLoginServicePort {
    override fun getUserByToken(provider: ThirdPartyAuthProviderType, accessToken: String): ThirdPartyUserAttribute {
        return when (provider) {
            ThirdPartyAuthProviderType.KAKAO -> kakaoApiClient.getAccessTokenInfo(accessToken).toAttribute()
            else -> throw IllegalArgumentException("지원하지 않는 소셜 로그인 제공자입니다.")
        }
    }
}


