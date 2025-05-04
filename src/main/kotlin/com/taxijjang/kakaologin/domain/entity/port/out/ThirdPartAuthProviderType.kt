package com.taxijjang.kakaologin.domain.entity.port.out

enum class ThirdPartyAuthProviderType(val code: String) {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google"),
    TENANT("tenant"),
    ;

    companion object {
        fun codeOf(code: String): ThirdPartyAuthProviderType {
            return values().first { it.code == code }
        }
    }
}
