package com.alzzaipo.portfolio.application.port.out;

import com.alzzaipo.common.Uid;
import com.alzzaipo.portfolio.domain.Portfolio;

import java.util.Optional;

public interface FindPortfolioPort {

    Optional<Portfolio> findPortfolio(Uid portfolioUID);
}