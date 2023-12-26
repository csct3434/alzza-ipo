package com.alzzaipo.notification.adapter.out.persistence.email;

import com.alzzaipo.common.Uid;
import com.alzzaipo.common.exception.CustomException;
import com.alzzaipo.member.adapter.out.persistence.member.MemberJpaEntity;
import com.alzzaipo.member.adapter.out.persistence.member.MemberRepository;
import com.alzzaipo.notification.application.port.out.email.ChangeNotificationEmailPort;
import com.alzzaipo.notification.application.port.out.email.CheckMemberSubscriptionExistsPort;
import com.alzzaipo.notification.application.port.out.email.CheckNotificationEmailAvailablePort;
import com.alzzaipo.notification.application.port.out.email.DeleteEmailNotificationPort;
import com.alzzaipo.notification.application.port.out.email.FindNotificationEmailPort;
import com.alzzaipo.notification.application.port.out.email.RegisterEmailNotificationPort;
import com.alzzaipo.notification.domain.email.EmailNotification;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@RequiredArgsConstructor
public class EmailNotificationPersistenceAdapter implements FindNotificationEmailPort,
	RegisterEmailNotificationPort,
	ChangeNotificationEmailPort,
	DeleteEmailNotificationPort,
	CheckNotificationEmailAvailablePort,
	CheckMemberSubscriptionExistsPort {

	private final MemberRepository memberRepository;
	private final EmailNotificationRepository emailNotificationRepository;

	@Override
	public Optional<String> findNotificationEmail(Uid memberUID) {
		return emailNotificationRepository.findByMemberJpaEntityUid(memberUID.get())
			.map(EmailNotificationJpaEntity::getEmail);
	}

	@Override
	public void register(EmailNotification emailNotification) {
		MemberJpaEntity memberJpaEntity = memberRepository.findEntityById(emailNotification.getMemberUID().get());
		EmailNotificationJpaEntity emailNotificationJpaEntity = toJpaEntity(emailNotification, memberJpaEntity);
		emailNotificationRepository.save(emailNotificationJpaEntity);
	}

	@Override
	public void changeEmail(Long memberId, String email) {
		EmailNotificationJpaEntity entity = emailNotificationRepository.findByMemberJpaEntityUid(memberId)
			.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "이메일 알림 조회 실패"));
		entity.changeEmail(email);
	}

	@Override
	public void delete(Uid memberUID) {
		EmailNotificationJpaEntity entity = emailNotificationRepository.findByMemberJpaEntityUid(memberUID.get())
			.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "이메일 알림 조회 실패"));
		emailNotificationRepository.delete(entity);
	}

	@Override
	public boolean checkEmailAvailable(String email) {
		return emailNotificationRepository.existsByEmail(email);
	}

	@Override
	public boolean checkSubscription(Uid memberId) {
		return emailNotificationRepository.existsByMemberJpaEntityUid(memberId.get());
	}

	private EmailNotificationJpaEntity toJpaEntity(EmailNotification domainEntity, MemberJpaEntity memberJpaEntity) {
		return new EmailNotificationJpaEntity(domainEntity.getEmail().get(), memberJpaEntity);
	}
}
