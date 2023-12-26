package com.alzzaipo.member.adapter.out.persistence.account.social;

import com.alzzaipo.common.LoginType;
import com.alzzaipo.common.Uid;
import com.alzzaipo.common.email.domain.Email;
import com.alzzaipo.common.exception.CustomException;
import com.alzzaipo.member.adapter.out.persistence.member.MemberJpaEntity;
import com.alzzaipo.member.adapter.out.persistence.member.MemberRepository;
import com.alzzaipo.member.application.port.out.account.social.DeleteSocialAccountPort;
import com.alzzaipo.member.application.port.out.account.social.FindSocialAccountByLoginTypePort;
import com.alzzaipo.member.application.port.out.account.social.FindSocialAccountPort;
import com.alzzaipo.member.application.port.out.account.social.RegisterSocialAccountPort;
import com.alzzaipo.member.application.port.out.dto.FindSocialAccountCommand;
import com.alzzaipo.member.application.port.out.member.FindMemberSocialAccountsPort;
import com.alzzaipo.member.domain.account.social.SocialAccount;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@RequiredArgsConstructor
public class SocialAccountPersistenceAdapter implements RegisterSocialAccountPort,
	FindSocialAccountPort,
	FindMemberSocialAccountsPort,
	FindSocialAccountByLoginTypePort,
	DeleteSocialAccountPort {

	private final MemberRepository memberRepository;
	private final SocialAccountRepository socialAccountRepository;

	@Override
	public void registerSocialAccount(SocialAccount socialAccount) {
		MemberJpaEntity memberJpaEntity = memberRepository.findEntityById(socialAccount.getMemberUID().get());
		SocialAccountJpaEntity socialAccountJpaEntity = toJpaEntity(memberJpaEntity, socialAccount);
		socialAccountRepository.save(socialAccountJpaEntity);
	}

	@Override
	public Optional<SocialAccount> findSocialAccount(FindSocialAccountCommand command) {
		return socialAccountRepository.findByLoginTypeAndEmail(command.getLoginType().name(), command.getEmail().get())
			.map(this::toDomainEntity);
	}

	@Override
	public List<SocialAccount> findMemberSocialAccounts(Uid memberUID) {
		return socialAccountRepository.findByMemberUID(memberUID.get())
			.stream()
			.map(this::toDomainEntity)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<SocialAccount> findSocialAccountByLoginType(Uid memberUID, LoginType loginType) {
		return socialAccountRepository.findByLoginType(memberUID.get(), loginType.name())
			.map(this::toDomainEntity);
	}

	@Override
	public void deleteSocialAccount(Uid memberUID, LoginType loginType) {
		SocialAccountJpaEntity socialAccountJpaEntity =
			socialAccountRepository.findByLoginType(memberUID.get(), loginType.name())
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "계정 조회 실패"));

		socialAccountRepository.delete(socialAccountJpaEntity);
	}

	private SocialAccountJpaEntity toJpaEntity(MemberJpaEntity memberJpaEntity, SocialAccount socialAccount) {
		return new SocialAccountJpaEntity(socialAccount.getEmail().get(), socialAccount.getLoginType().name(),
			memberJpaEntity);
	}

	private SocialAccount toDomainEntity(SocialAccountJpaEntity socialAccountJpaEntity) {
		Uid memberUID = new Uid(socialAccountJpaEntity.getMemberJpaEntity().getUid());
		Email email = new Email(socialAccountJpaEntity.getEmail());
		LoginType loginType = LoginType.valueOf(socialAccountJpaEntity.getLoginType());
		return new SocialAccount(memberUID, email, loginType);
	}
}