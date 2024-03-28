package KGUcapstone.OutDecision.domain.user.dto;

import lombok.Getter;

public class MemberRequestDTO {
    @Getter
    public static class UpdateMemberDTO{
        String name;
        String nickname;
        String phone;
    }
}
