package com.taxijjang.kakaologin.domain.entity

import jakarta.persistence.*
import java.time.ZonedDateTime
import java.util.*

@Entity
class AccountIdentifier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "account_id")
    val account: Account,

    @Enumerated(EnumType.STRING)
    val type: IdentifierType,

    val originValue: String,
) {
    @Column(name = "entity_key", nullable = false, columnDefinition = "BINARY(16)")
    val key: UUID = UUID.randomUUID()

    @Column(nullable = false, updatable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now()

    companion object {
        fun create(
            account: Account,
            type: IdentifierType,
            originValue: String,
        ): AccountIdentifier {
            return AccountIdentifier(
                account = account,
                type = type,
                originValue = originValue,
            )
        }
    }
}

enum class IdentifierType {
    EMAIL,
    PHONE,
    KAKAO,
}