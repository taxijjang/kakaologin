package com.taxijjang.kakaologin.application.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.taxijjang.kakaologin.domain.entity.account.Account
import com.taxijjang.kakaologin.domain.entity.account.IdentifierType
import com.taxijjang.kakaologin.domain.entity.account.QAccount.account
import com.taxijjang.kakaologin.domain.entity.account.QAccountIdentifier.accountIdentifier
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
        return queryFactory
            .selectFrom(account)
            .leftJoin(account.identifiers, accountIdentifier).fetchJoin() // ← 여기도!
            .where(account.key.eq(key))
            .fetchOne() ?: throw NotFoundAccount(key)
    }


    override fun findByKey(key: UUID): Account? {
        return queryFactory.selectFrom(account)
            .where(account.key.eq(key))
            .fetchOne()
    }

    override fun findByIdentifier(type: IdentifierType, value: String): Account? {
        return queryFactory
            .select(account) // ✅ account를 select
            .from(accountIdentifier)
            .join(accountIdentifier.account, account)
            .where(
                accountIdentifier.type.eq(type)
                    .and(accountIdentifier.originValue.eq(value))
            )
            .fetchOne()
    }
}

interface JpaAccountRepository : JpaRepository<Account, Long> {
    fun save(entity: Account): Account
}