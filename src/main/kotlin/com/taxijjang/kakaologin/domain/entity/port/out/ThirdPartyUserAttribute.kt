package com.taxijjang.kakaologin.domain.entity.port.out

import com.taxijjang.kakaologin.domain.contract.EmailAddress
import java.time.ZonedDateTime

data class ThirdPartyUserAttribute(
    val id: String,
    val provider: ThirdPartyAuthProviderType,
    val email: EmailAddress,
    val name: String,
    val profileImage: String? = null,

    // ex) +821063110710
    val phoneNumber: String? = null,

    // ex) none, female, male
    val gender: String? = null,

    val agreeTerms: List<SocialAgreeTerms> = emptyList(),
    val isVerifiedEmail: Boolean = true,
)

data class AccountInfoData(
    val fieldName: String,
    val value: String,
    val certificationResult: String? = null,
)

enum class AgreeTermsType(val value: String) {
    TERMS("terms"),
    PRIVACY("privacy"),
    MARKETING("marketing"),
}

data class SocialAgreeTerms(
    val type: AgreeTermsType,
    val agreedAt: ZonedDateTime,
)