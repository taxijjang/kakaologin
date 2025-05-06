package com.taxijjang.kakaologin.domain.contract

import com.taxijjang.kakaologin.domain.entity.account.Account
import com.taxijjang.kakaologin.domain.entity.account.AccountStatus
import java.time.ZonedDateTime
import java.util.*

data class AccountRead(
    val key: UUID,
    val displayName: String?,
    val email: EmailAddress?,
    val image: String?,
    val status: AccountStatus,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime?,
){
    companion object{
        fun from(account: Account): AccountRead {
            return AccountRead(
                key = account.key,
                displayName = account.displayName,
                email = account.email,
                image = account.image,
                status = account.status,
                createdAt = account.createdAt,
                updatedAt = account.updatedAt,
            )
        }
    }
}