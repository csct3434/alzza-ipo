package com.alzzaipo.member.application.port.out.dto;

import com.alzzaipo.common.Email;
import com.alzzaipo.common.Uid;
import com.alzzaipo.member.domain.account.local.LocalAccountId;
import lombok.Getter;

@Getter
public class SecureLocalAccount {

    private final Uid memberUID;
    private final LocalAccountId accountId;
    private final String encryptedAccountPassword;
    private final Email email;

    public SecureLocalAccount(Uid memberUID, LocalAccountId accountId, String encryptedAccountPassword, Email email) {
        this.memberUID = memberUID;
        this.accountId = accountId;
        this.encryptedAccountPassword = encryptedAccountPassword;
        this.email = email;
    }
}