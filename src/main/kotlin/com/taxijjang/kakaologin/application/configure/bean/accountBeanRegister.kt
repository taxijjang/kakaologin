package com.taxijjang.kakaologin.application.configure.bean

import com.taxijjang.kakaologin.domain.repository.AccountRepository
import com.taxijjang.kakaologin.domain.service.SignInOrSignUpMutator
import org.springframework.stereotype.Service

@Service
class SignInOrSignUpService(
    repository: AccountRepository
): SignInOrSignUpMutator(
    repository = repository,
)