package com.taxijjang.kakaologin.domain.contract.exception

open class DomainException(
    val code: String?,
    val data: Map<String, Any?>?,
    message: String?,
    cause: Throwable? = null,
) : RuntimeException(message, cause)