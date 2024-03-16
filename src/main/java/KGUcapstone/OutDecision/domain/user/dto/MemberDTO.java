package KGUcapstone.OutDecision.domain.user.dto;

import lombok.Data;

@Data
public class MemberDTO {

    private String name;
    private String email;
    private String accessToken;
    private String nickname;
    private String phone;
}
