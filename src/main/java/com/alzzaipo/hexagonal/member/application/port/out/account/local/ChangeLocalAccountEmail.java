package com.alzzaipo.hexagonal.member.application.port.out.account.local;

import com.alzzaipo.hexagonal.common.Email;
import com.alzzaipo.hexagonal.member.domain.LocalAccount.LocalAccountId;

public interface ChangeLocalAccountEmail {

    void changeLocalAccountEmail(LocalAccountId localAccountId, Email email);
}