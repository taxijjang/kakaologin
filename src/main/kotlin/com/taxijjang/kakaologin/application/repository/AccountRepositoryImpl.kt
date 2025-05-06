package com.taxijjang.kakaologin.application.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.taxijjang.kakaologin.domain.entity.Account
import com.taxijjang.kakaologin.domain.entity.IdentifierType
import com.taxijjang.kakaologin.domain.entity.QAccount.account
import com.taxijjang.kakaologin.domain.entity.QAccountIdentifier.accountIdentifier
import com.taxijjang.kakaologin.domain.exception.NotFoundAccount
import com.taxijjang.kakaologin.domain.repository.AccountRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val jpaRepository: JpaAccountRepository,
) : AccountRepository {
    override fun save(entity: Account): Account {
        jpaRepository.save(entity)
        return entity
    }

    override fun getByKey(key: UUID): Account {
        return queryFactory.selectFrom(account)
            .where(account.key.eq(key))
            .fetchOne() ?: throw NotFoundAccount(key)
    }

    override fun findByKey(key: UUID): Account? {
        return queryFactory.selectFrom(account)
            .where(account.key.eq(key))
            .fetchOne()
    }

    override fun findByIdentifier(type: IdentifierType, value: String): Account? {
        return queryFactory.selectFrom(accountIdentifier)
            .where(
                accountIdentifier.type.eq(type).and(
                    accountIdentifier.originValue.eq(value)
                )
            ).fetchOne().let {
                it?.account
            }
    }
}

interface JpaAccountRepository : JpaRepository<Account, Long> {
    fun save(entity: Account): Account
}