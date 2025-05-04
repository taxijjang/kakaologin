package com.taxijjang.kakaologin.application.port.out

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.taxijjang.kakaologin.domain.entity.port.out.AgreeTermsType
import com.taxijjang.kakaologin.domain.entity.port.out.SocialAgreeTerms
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import java.time.ZonedDateTime

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoApi")
interface KakaoApiClient {
    @GetMapping("/v2/user/me")
    fun getAccessTokenInfo(
        @RequestHeader("Authorization") accessToken: String,
        @RequestHeader("Content-type") contentType: String = "application/x-www-form-urlencoded;charset=utf-8"
    ): KakaoUserAttribute

    @GetMapping("/v2/user/service_terms")
    fun getTerms(
        @RequestHeader("Authorization") accessToken: String,
        @RequestHeader("Content-type") contentType: String = "application/x-www-form-urlencoded;charset=utf-8"
    ): KakaoUserTerms

}

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserTerms(
    @JsonProperty("id")
    val userId: String,

    @JsonProperty("service_terms")
    val allowedServiceTerms: List<KakaoAllowedServiceTerms> = emptyList(),
) {
    fun toSocialAgreeTerms(): List<SocialAgreeTerms> {
        val result = mutableListOf<SocialAgreeTerms>()
        allowedServiceTerms.map {
            val tag = when (it.tag) {
                "marketing" -> AgreeTermsType.MARKETING
                "privacy" -> AgreeTermsType.PRIVACY
                "terms" -> AgreeTermsType.TERMS
                else -> null
            }
            if (tag != null) {
                result.add(
                    SocialAgreeTerms(
                        type = tag,
                        agreedAt = it.agreedAt,
                    )
                )
            }
        }
        return result
    }
}

data class KakaoAllowedServiceTerms(
    val tag: String,
    // 사용자가 해당 서비스 약관에 마지막으로 동의한 시간
    @JsonProperty("agreed_at")
    val agreedAt: ZonedDateTime,
    // 서비스 약관의 동의 상태
    val agreed: Boolean,
    // 서비스 약관의 필수 동의 여부
    val required: Boolean,
    // 서비스 약관의 동의 철회 가능 여부
    val revocable: Boolean,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserAttribute(
    val id: String,
    @JsonProperty("connected_at")
    val connectedAt: String,
    val properties: KakaoUserAttributeProperties,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoUserAttributeKakaoAccount
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserAttributeProperties(
    val nickname: String,
    @JsonProperty("profile_image")
    val profileImage: String = "",
    @JsonProperty("thumbnail_image")
    val thumbnailImage: String = "",
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserAttributeKakaoAccount(
    // property중 에서 필수가 아닌 것들은 default value 선언

    // 사용자 동의 시 프로필 정보(닉네임/프로필 사진) 제공 가능
    @JsonProperty("profile_needs_agreement")
    val profileNeedsAgreement: Boolean = false,

    // 사용자 동의 시 닉네임 제공 기능
    @JsonProperty("profile_nickname_needs_agreement")
    val profileNicknameNeedsAgreement: Boolean = false,

    // 사용자 동의 시 프로필 사진 제공 가능
    @JsonProperty("profile_image_needs_agreement")
    val profileImageNeedsAgreement: Boolean = false,

    // 프로필 정보
    val profile: KakaoUserAttributeKakaoAccountProfile,

    // 사용자 동의 시 카카오계정 이름 제공 가능
    @JsonProperty("name_needs_agreement")
    val nameNeedsAgreement: Boolean = false,

    // 카카오계정 이름
    val name: String = "",

    // 사용자 동의 시 카카오계정 대표 이메일 제공 가능
    @JsonProperty("email_needs_agreement")
    val emailNeedsAgreement: Boolean = false,

    // 이메일 유효 여부
    @JsonProperty("is_email_valid")
    val isEmailValid: Boolean = false,

    // 이메일 인증 여부
    @JsonProperty("is_email_verified")
    val isEmailVerified: Boolean = false,

    // 카카오계정 대표 이메일
    val email: String,

    // 사용자 동의 시 연령대 제공 가능
    @JsonProperty("age_range_needs_agreement")
    val ageRangeNeedsAgreement: Boolean = false,

    // 연령대
    @JsonProperty("age_range")
    val ageRange: String = "",

    // 사용자 동의 시 출생 연도 제공 가능
    @JsonProperty("birthday_needs_agreement")
    val birthdayNeedsAgreement: Boolean = false,

    // 출생 연도(YYYY형식)
    val birthday: String = "",

    // 사용자 동의 시 성별 제공 가능
    @JsonProperty("birthday_type")
    val birthdayType: String = "",

    // 성병 - femail: 여성, male: 남성
    val gender: String = "none",

    // 사용자 동의 시 전화번호 제공 가능
    @JsonProperty("phone_number_needs_agreement")
    val phoneNumberNeedsAgreement: Boolean = false,

    @JsonProperty("phone_number")
    var phoneNumber: String? = null,

    // 사용자 동의 시 CI 참고 가능
    @JsonProperty("ci_needs_agreement")
    val ciNeedsAgreement: Boolean = false,

    // 연계정보
    val ci: String = "",

    // CI 발급 시각, UTC*
    @JsonProperty("ci_authenticated_at")
    val ciAuthenticatedAt: String = "",
) {
    init {
        this.phoneNumber = this.phoneNumber?.replace("-", "")?.replace(" ", "")?.replace("+82", "0")
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserAttributeKakaoAccountProfile(
    val nickname: String,
    @JsonProperty("thumbnail_image_url")
    val thumbnailImageUrl: String = "",
    @JsonProperty("profile_image_url")
    val profileImageUrl: String = "",
)

/**
 *  23년 7월 20일 기준 kakao response 형태
 * {
 *     "id": 2911147586,
 *     "connected_at": "2023-07-14T09:30:11Z",
 *     "synched_at": "2023-07-17T03:00:15Z",
 *     "properties": {
 *         "nickname": "김택윤",
 *         "profile_image": "http://k.kakaocdn.net/dn/djQ0ZH/btsiPjgMb4D/6S2HyHWI8HOitrNG4j7Ibk/img_640x640.jpg",
 *         "thumbnail_image": "http://k.kakaocdn.net/dn/djQ0ZH/btsiPjgMb4D/6S2HyHWI8HOitrNG4j7Ibk/img_110x110.jpg"
 *     },
 *     "kakao_account": {
 *         "profile_nickname_needs_agreement": false,
 *         "profile_image_needs_agreement": false,
 *         "profile": {
 *             "nickname": "김택윤",
 *             "thumbnail_image_url": "http://k.kakaocdn.net/dn/djQ0ZH/btsiPjgMb4D/6S2HyHWI8HOitrNG4j7Ibk/img_110x110.jpg",
 *             "profile_image_url": "http://k.kakaocdn.net/dn/djQ0ZH/btsiPjgMb4D/6S2HyHWI8HOitrNG4j7Ibk/img_640x640.jpg",
 *             "is_default_image": false
 *         },
 *         "has_email": true,
 *         "email_needs_agreement": false,
 *         "is_email_valid": true,
 *         "is_email_verified": true,
 *         "email": "gw9122@naver.com",
 *         "has_phone_number": true,
 *         "phone_number_needs_agreement": false,
 *         "phone_number": "+82 10-6311-0710"
 *     }
 * }
 */