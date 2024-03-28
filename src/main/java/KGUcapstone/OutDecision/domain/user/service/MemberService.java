package KGUcapstone.OutDecision.domain.user.service;

import KGUcapstone.OutDecision.domain.user.dto.MemberRequestDTO;

import static KGUcapstone.OutDecision.domain.user.dto.MemberResponseDTO.*;

public interface MemberService {
    MemberDTO getMemberById(Long memberId);
    MemberDTO updateMemberInfo(Long memberId, MemberRequestDTO.UpdateMemberDTO request);
}
