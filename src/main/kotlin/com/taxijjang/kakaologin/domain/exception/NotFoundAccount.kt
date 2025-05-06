package com.taxijjang.kakaologin.domain.exception

import com.taxijjang.kakaologin.domain.contract.exception.DomainException
import java.util.*

class NotFoundAccount(
    key: UUID,
) : DomainException(
    code = "account.not.found",
    message = "해당 유저를 찾을 수 없습니다.",
    data = mapOf(
        "invalidKey" to key.toString(),
    )
)