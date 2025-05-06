package com.taxijjang.kakaologin.domain.entity.account

import com.taxijjang.kakaologin.domain.contract.EmailAddress
import jakarta.persistence.*
import java.time.ZonedDateTime
import java.util.*

@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = true)
    val displayName: String? = null,

    @Column(nullable = true, columnDefinition = "VARCHAR(255)")
    @Convert(converter = EmailConverter::class)
    val email: EmailAddress? = null,

    @Column(nullable = true)
    val image: String? = null,

) {
    @Column(name = "entity_key", nullable = false, columnDefinition = "BINARY(16)")
    val key: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: AccountStatus = AccountStatus.ACTIVE

    @OneToMany(
        mappedBy = "account",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
    )
    private val identifiers: MutableSet<AccountIdentifier> = mutableSetOf()

    @Column(nullable = false, updatable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now()

    @Column(nullable = true)
    val updatedAt: ZonedDateTime? = null


    fun getIdentifier(type: IdentifierType): AccountIdentifier? =
        identifiers.firstOrNull { it.type == type }


    companion object {
        fun registerBySocial(
            displayName: String? = null,
            email: EmailAddress? = null,
            image: String? = null,
            socialId: String,
        ): Account {
            val entity = Account(
                displayName = displayName,
                email = email,
                image = image,
            )

            // TODO: 현재는 카카오 밖에 음슴
            val identifier = AccountIdentifier.create(
                account = entity,
                type = IdentifierType.KAKAO,
                originValue = socialId,
            )

            entity.identifiers.add(identifier)
            return entity
        }
    }

}

enum class AccountStatus {
    ACTIVE,
    WITHDRAWAL,
}

class EmailConverter : AttributeConverter<EmailAddress?, String?> {
    override fun convertToDatabaseColumn(attribute: EmailAddress?): String? {
        if (attribute == null)
            return null
        return attribute.value

    }

    override fun convertToEntityAttribute(dbData: String?): EmailAddress? {
        if (dbData == null)
            return null
        return EmailAddress(dbData)
    }
}