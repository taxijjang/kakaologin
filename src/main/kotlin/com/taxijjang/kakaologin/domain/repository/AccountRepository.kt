package com.taxijjang.kakaologin.domain.repository

import com.taxijjang.kakaologin.domain.entity.account.Account
import com.taxijjang.kakaologin.domain.entity.account.IdentifierType
import java.util.UUID

interface AccountRepository {
    fun save(entity: Account): Account
    fun getByKey(key: UUID): Account
    fun findByKey(key: UUID): Account?
    fun findByIdentifier(
        type: IdentifierType,
        value: String,
    ): Account?
}