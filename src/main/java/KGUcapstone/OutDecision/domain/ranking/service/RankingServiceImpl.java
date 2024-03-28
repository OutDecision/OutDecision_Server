package KGUcapstone.OutDecision.domain.ranking.service;

import KGUcapstone.OutDecision.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static KGUcapstone.OutDecision.domain.ranking.dto.RankingResponseDTO.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingServiceImpl implements RankingService {
    private final MemberRepository memberRepository;

    @Override
    public List<RankingResultDTO> getTop100Rankings() {
        List<RankingResultDTO> rankings = sortedForRanking();

        // 공동 순위 설정
        int rank = 1;
        int prevPoint = Integer.MAX_VALUE;
        int sameRankCount = 0;
        for (RankingResultDTO ranking : rankings) {
            if (ranking.getPoint() != prevPoint) {
                rank += sameRankCount;
                sameRankCount = 1;
                prevPoint = ranking.getPoint();
            } else {
                sameRankCount++;
            }
            ranking.setRank(rank);
        }

        return rankings.stream()
                .filter(r -> r.getRank() <= 100) // 100위까지 필터링
                .collect(Collectors.toList());
    }

    @Override
    public RankingListDTO rankingListDTO(List<RankingResultDTO> ranking) {
        List<RankingResultDTO> rankingResultDTOList = ranking.stream()
                .collect(Collectors.toList());

        return RankingListDTO.builder()
                .RankingList(rankingResultDTOList)
                .listSize(rankingResultDTOList.size())
                .build();
    }

    @Override
    public RankingResultDTO memberRankingDTO(Long memberId) {
        List<RankingResultDTO> rankings = sortedForRanking();

        // 공동 순위 설정
        int rank = 1;
        int prevPoint = Integer.MAX_VALUE;
        int sameRankCount = 0;
        int idx = 0;
        for (RankingResultDTO ranking : rankings) {
            if (ranking.getPoint() != prevPoint) {
                rank += sameRankCount;
                sameRankCount = 1;
                prevPoint = ranking.getPoint();
            } else {
                sameRankCount++;
            }
            ranking.setRank(rank);
            if (ranking.getMemberId().equals(memberId)) {
                idx =  rankings.indexOf(ranking);
                break;
            }
        }
        return rankings.get(idx);
    }

    // 랭킹 정렬
    private List<RankingResultDTO> sortedForRanking() {
        return memberRepository.findAll()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getPoint(), a.getPoint()))
                .map(member -> {
                    RankingResultDTO dto = new RankingResultDTO();
                    dto.setNickname(member.getNickname());
                    dto.setMemberId(member.getId());
                    dto.setJoinDate(member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // createdAt을 사용하여 가입일을 문자열로 변환하여 저장
                    dto.setPoint(member.getPoint());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
