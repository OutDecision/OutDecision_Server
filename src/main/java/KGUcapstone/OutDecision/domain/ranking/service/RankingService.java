package KGUcapstone.OutDecision.domain.ranking.service;

import KGUcapstone.OutDecision.domain.ranking.dto.RankingResponseDTO.RankingListDTO;
import KGUcapstone.OutDecision.domain.ranking.dto.RankingResponseDTO.RankingResultDTO;

import java.util.List;

public interface RankingService {
    List<RankingResultDTO> getTop100Rankings();
    RankingListDTO rankingListDTO(List<RankingResultDTO> ranking);
    RankingResultDTO memberRankingDTO(Long memberId);
}
