package com.taxijjang.kakaologin.application.controller

import com.taxijjang.kakaologin.domain.contract.AccountRead
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyAuthProviderType
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyLoginServicePort
import com.taxijjang.kakaologin.domain.usecase.SignInOrSignUpUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val thridPartyLoginServicePort: ThirdPartyLoginServicePort,
    private val signInOrSignUpUseCase: SignInOrSignUpUseCase,
) {

    @PostMapping("/kakao")
    fun signInWithKakao(@RequestBody request: KakaoLoginRequest): ResponseEntity<AccountRead> {
        val userInfo = thridPartyLoginServicePort.getUserByToken(
            provider = ThirdPartyAuthProviderType.KAKAO,
            accessToken = "Bearer ${request.accessToken}",
        )
        val account = signInOrSignUpUseCase.bySocial(userInfo)
        return ResponseEntity.ok(account)
    }
}

data class KakaoLoginRequest(
    val accessToken: String,
)
