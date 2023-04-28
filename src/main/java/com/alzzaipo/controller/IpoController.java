package com.alzzaipo.controller;

import com.alzzaipo.service.IpoService;
import com.alzzaipo.domain.dto.IpoAnalyzeRequestDto;
import com.alzzaipo.domain.dto.IpoAnalyzeResponseDto;
import com.alzzaipo.domain.dto.IpoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/ipo")
@RestController
public class IpoController {

    private final IpoService ipoService;

    // 데이터베이스에 등록된 공모주 목록을 반환합니다
    @GetMapping("/list")
    public ResponseEntity<List<IpoListDto>> getIpoList() {
        List<IpoListDto> ipoList = ipoService.getIpoList();
        return ResponseEntity.ok(ipoList);
    }

    // 공모주 분석 결과를 반환합니다
    @PostMapping("/analyze")
    public ResponseEntity<IpoAnalyzeResponseDto> getIpoAnalyzeResult(@RequestBody IpoAnalyzeRequestDto ipoAnalyzeRequestDto) {
        // 분석 조건에 부합하는 공모주들의 (평균 수익률, 공모주 목록)을 담은 분석결과 DTO를 가져옵니다
        IpoAnalyzeResponseDto analyzeResponseDto = ipoService.analyze(ipoAnalyzeRequestDto);

        return ResponseEntity.ok(analyzeResponseDto);
    }
}