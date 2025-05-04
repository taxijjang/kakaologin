package com.taxijjang.kakaologin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class KakaologinApplication

fun main(args: Array<String>) {
	runApplication<KakaologinApplication>(*args)
}
