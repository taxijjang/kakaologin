package com.taxijjang.kakaologin.domain.usecase

import com.taxijjang.kakaologin.domain.contract.AccountRead
import com.taxijjang.kakaologin.domain.port.out.ThirdPartyUserAttribute

interface SignInOrSignUpUseCase {
    fun bySocial(command: ThirdPartyUserAttribute): AccountRead
}