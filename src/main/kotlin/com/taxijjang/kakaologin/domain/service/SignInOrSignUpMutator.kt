package com.taxijjang.kakaologin.domain.service

import com.taxijjang.kakaologin.domain.contract.AccountRead
import com.taxijjang.kakaologin.domain.entity.Account
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyUserAttribute
import com.taxijjang.kakaologin.domain.repository.AccountRepository
import com.taxijjang.kakaologin.domain.usecase.SignInOrSignUpUseCase

open class SignInOrSignUpMutator(
    private val repository: AccountRepository,
) : SignInOrSignUpUseCase {
    override fun bySocial(command: ThirdPartyUserAttribute): AccountRead {
        repository.findByIdentifier(
            type = command.provider.toDomain(),
            value = command.id,
        )?.let {
            return AccountRead.from(
                account = it,
            )
        }

        val entity = Account.registerBySocial(
            displayName = command.name,
            email = command.email,
            image = command.profileImage,
            socialId = command.id,
        )
        repository.save(entity)
        return AccountRead.from(
            account = entity,
        )
    }
}