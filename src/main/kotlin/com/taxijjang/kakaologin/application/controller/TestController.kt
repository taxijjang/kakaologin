package com.taxijjang.kakaologin.application.controller

import com.taxijjang.kakaologin.application.configure.filter.CustomPrincipal
import com.taxijjang.kakaologin.application.port.out.KakaoApiClient
import com.taxijjang.kakaologin.application.port.out.KakaoUserAttribute
import com.taxijjang.kakaologin.domain.contract.AccountRead
import com.taxijjang.kakaologin.domain.repository.AccountRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class TestController(
    private val accountRepository: AccountRepository,
) {
    @GetMapping("/me")
    fun getKakao(
        @AuthenticationPrincipal principal: CustomPrincipal
    ): AccountRead {
        val account = accountRepository.findByKey(principal.key)
            ?: throw IllegalArgumentException("Account not found")
        return AccountRead.from(account)

    }
}