package com.taxijjang.kakaologin.domain.port.out

interface ThirdPartyLoginServicePort {
    fun getUserByToken(
        provider: ThirdPartyAuthProviderType,
        accessToken: String,
    ): ThirdPartyUserAttribute
}