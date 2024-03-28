package KGUcapstone.OutDecision.domain.user.controller;

import KGUcapstone.OutDecision.domain.user.dto.MemberRequestDTO.UpdateMemberDTO;
import KGUcapstone.OutDecision.domain.user.dto.PasswordRequestDTO.UpdatePasswordDTO;
import KGUcapstone.OutDecision.domain.user.service.MemberService;
import KGUcapstone.OutDecision.domain.user.service.PasswordService;
import KGUcapstone.OutDecision.global.error.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static KGUcapstone.OutDecision.domain.user.dto.MemberResponseDTO.*;
import static KGUcapstone.OutDecision.domain.user.dto.PasswordResponseDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MemberRestController {

    private final MemberService memberService;
    private final PasswordService passwordService;

    @GetMapping("/{memberId}/edit")
    @Operation(summary = "마이페이지 개인정보수정 조회 API", description = "마이페이지 개인정보수정 페이지에서 개인정보를 조회합니다.")
    public ApiResponse<MemberDTO> getMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberDTO memberDTO = memberService.getMemberById(memberId);
        return ApiResponse.onSuccess(memberDTO);
    }

    @PatchMapping("/{memberId}/edit")
    @Operation(summary = "마이페이지 개인정보수정 API", description = "마이페이지 개인정보수정 페이지에서 개인정보를 수정합니다.")
    public ApiResponse<MemberDTO> updateMemberInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateMemberDTO request) {
        MemberDTO updateMemberDTO = memberService.updateMemberInfo(memberId, request);
        return ApiResponse.onSuccess(updateMemberDTO);
    }

    @PatchMapping("/{memberId}/edit/password")
    @Operation(summary = "마이페이지 비밀번호 변경", description = "비밀번호를 변경합니다.")
    public ApiResponse<UpdatePasswordResultDTO> updatePassword(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdatePasswordDTO request) {
        UpdatePasswordResultDTO updatePasswordResultDTO = passwordService.updatePassword(memberId, request);
        return ApiResponse.onSuccess(updatePasswordResultDTO);
    }

}