package KGUcapstone.OutDecision.domain.user.service;

import KGUcapstone.OutDecision.domain.user.domain.Member;
import KGUcapstone.OutDecision.domain.user.dto.PasswordRequestDTO.UpdatePasswordDTO;
import KGUcapstone.OutDecision.domain.user.dto.PasswordResponseDTO.UpdatePasswordResultDTO;
import KGUcapstone.OutDecision.domain.user.repository.MemberRepository;
import KGUcapstone.OutDecision.global.error.handler.MemberHandler;
import KGUcapstone.OutDecision.global.error.handler.PasswordHandler;
import KGUcapstone.OutDecision.global.error.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements PasswordService{
    private final MemberRepository memberRepository;

    @Override
    public UpdatePasswordResultDTO updatePassword(Long memberId, UpdatePasswordDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // 이전 비밀번호가 일치하지 않는 경우
        if (!request.getCurrentPassword().equals(member.getPassword())) {
            throw new PasswordHandler(ErrorStatus.PASSWORD_MISMATCH);
        }
        // 이전 비밀번호와 새 비밀번호가 일치하는 경우
        if (request.getNewPassword().equals(member.getPassword())) {
            throw new PasswordHandler(ErrorStatus.SAME_PASSWORD);
        }
        // 새 비밀번호와 새 비밀번호 확인이 일치하지 않는 경우
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new PasswordHandler(ErrorStatus.PASSWORD_CONFIRMATION_MISMATCH);
        }

        // 비밀번호 변경
        member.updatePassword(request.getNewPassword());

        memberRepository.save(member);

        return UpdatePasswordResultDTO.builder()
                .message("비밀번호 변경이 완료되었습니다.")
                .build();

    }

}
