package com.taxijjang.kakaologin.application.controller

import com.taxijjang.kakaologin.application.port.out.KakaoApiClient
import com.taxijjang.kakaologin.application.port.out.KakaoUserAttribute
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val kakaoApiClient: KakaoApiClient,
){
    @GetMapping("/kakao")
    fun getKakao(
        @RequestParam accessToken: String,
    ): KakaoUserAttribute {
        return kakaoApiClient.getAccessTokenInfo(
            accessToken = "Bearer ${accessToken}"
        )
    }
}