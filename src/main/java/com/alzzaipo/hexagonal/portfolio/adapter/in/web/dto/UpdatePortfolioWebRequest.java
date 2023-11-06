package com.alzzaipo.hexagonal.portfolio.adapter.in.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePortfolioWebRequest {

    private Long uid;
    private int stockCode;
    private int sharesCnt;
    private Long profit;
    private String agents;
    private String memo;

}
