package com.taxijjang.kakaologin.domain.contract.exception

class InvaildEmailAddressException (
    message: String?
    ) : DomainException(
    code = "account.email.invalid",
    message = message,
    data = emptyMap(),
    )