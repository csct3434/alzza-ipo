package com.alzzaipo.hexagonal.member.application.port.in.account.local;

import com.alzzaipo.hexagonal.member.domain.LocalAccount.LocalAccountId;

public interface CheckLocalAccountIdAvailabilityQuery {

    boolean checkLocalAccountIdAvailability(LocalAccountId localAccountId);
}