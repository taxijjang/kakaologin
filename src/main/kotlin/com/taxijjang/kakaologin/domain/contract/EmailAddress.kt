package com.taxijjang.kakaologin.domain.contract

import com.taxijjang.kakaologin.domain.contract.exception.InvaildEmailAddressException

private val required = Regex(".@")

data class EmailAddress(
    var value: String,
) {

    init {
        if (!required.containsMatchIn(value)) {
            throw InvaildEmailAddressException("'@', '.'이 포함한 이메일 타입이 아닙니다")
        }
        this.value = value.lowercase()
    }
}
