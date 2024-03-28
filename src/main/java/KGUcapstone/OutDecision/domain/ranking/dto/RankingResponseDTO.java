package KGUcapstone.OutDecision.domain.ranking.dto;

import lombok.*;

import java.util.List;

public class RankingResponseDTO {
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankingResultDTO{
        Integer rank;
        Long memberId;
        String nickname;
        String joinDate;
        Integer point;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankingListDTO{
        List<RankingResultDTO> RankingList;
        Integer listSize;
    }
}
