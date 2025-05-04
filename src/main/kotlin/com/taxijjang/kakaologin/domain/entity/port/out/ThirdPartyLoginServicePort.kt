package com.taxijjang.kakaologin.domain.entity.port.out

import com.taxijjang.kakaologin.domain.contract.EmailAddress

interface ThirdPartyLoginServicePort {
    fun getUserByToken(tenantId: String, provider: ThirdPartyAuthProviderType, accessToken: String): ThirdPartyUserAttribute
}