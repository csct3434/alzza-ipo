package com.alzzaipo.notification.adapter.out.persistence.criterion;

import com.alzzaipo.common.BaseTimeEntity;
import com.alzzaipo.member.adapter.out.persistence.member.MemberJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NotificationCriterionJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "notification_criterion_uid", unique = true, nullable = false)
    private Long notificationCriterionUID;

    @Column(name = "min_competition_rate", nullable = false,
            columnDefinition = "INT CHECK (min_competition_rate >= 0 AND min_competition_rate <= 10000)")
    private int minCompetitionRate;

    @Column(name = "min_lockup_rate", nullable = false,
            columnDefinition = "INT CHECK (min_lockup_rate >= 0 AND min_lockup_rate <= 100)")
    private int minLockupRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uid", nullable = false)
    private MemberJpaEntity memberJpaEntity;

    public NotificationCriterionJpaEntity(Long notificationCriterionUID, int minCompetitionRate, int minLockupRate, MemberJpaEntity memberJpaEntity) {
        this.notificationCriterionUID = notificationCriterionUID;
        this.minCompetitionRate = minCompetitionRate;
        this.minLockupRate = minLockupRate;
        this.memberJpaEntity = memberJpaEntity;
    }

    public void changeMinCompetitionRate(int minCompetitionRate) {
        this.minCompetitionRate = minCompetitionRate;
    }

    public void changeMinLockupRate(int minLockupRate) {
        this.minLockupRate = minLockupRate;
    }
}