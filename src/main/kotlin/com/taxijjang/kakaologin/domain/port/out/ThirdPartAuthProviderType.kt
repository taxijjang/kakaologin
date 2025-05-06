package com.taxijjang.kakaologin.domain.port.out

import com.taxijjang.kakaologin.domain.entity.account.IdentifierType

enum class ThirdPartyAuthProviderType(val code: String) {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google"),
    ;

    companion object {
        fun codeOf(code: String): ThirdPartyAuthProviderType {
            return values().first { it.code == code }
        }
    }

    fun toDomain(): IdentifierType {
        return when (this) {
            KAKAO -> IdentifierType.KAKAO
            else -> throw IllegalArgumentException("Unsupported provider type: $this")
        }
    }
}
