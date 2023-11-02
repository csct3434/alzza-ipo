package com.alzzaipo.hexagonal.email.application.port.out;

import com.alzzaipo.hexagonal.email.domain.Email;
import com.alzzaipo.hexagonal.email.domain.EmailVerificationCode;

public interface SaveEmailVerificationHistoryPort {

    void saveEmailVerificationHistory(Email email, EmailVerificationCode emailVerificationCode);
}