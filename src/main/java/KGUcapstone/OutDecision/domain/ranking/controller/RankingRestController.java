package KGUcapstone.OutDecision.domain.ranking.controller;

import KGUcapstone.OutDecision.domain.ranking.dto.RankingResponseDTO;
import KGUcapstone.OutDecision.domain.ranking.service.RankingService;
import KGUcapstone.OutDecision.global.error.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static KGUcapstone.OutDecision.domain.ranking.dto.RankingResponseDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingRestController {
    private final RankingService rankingService;

    @GetMapping("")
    @Operation(summary = "포인트랭킹 조회 API", description = "포인트랭킹 100위까지 조회합니다.")
    public ApiResponse<RankingListDTO> getAllRanking() {
        List<RankingResultDTO> getRankingList = rankingService.getTop100Rankings();
        return ApiResponse.onSuccess(rankingService.rankingListDTO(getRankingList));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "포인트랭킹 조회(개인) API", description = "사용자의 포인트랭킹 순위를 조회합니다.")
    public ApiResponse<RankingResultDTO> getMemberRanking(@PathVariable("memberId") Long memberId) {
        RankingResultDTO memberRankingDTO = rankingService.memberRankingDTO(memberId);
        return ApiResponse.onSuccess(memberRankingDTO);
    }
}
