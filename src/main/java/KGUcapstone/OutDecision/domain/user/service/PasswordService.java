package KGUcapstone.OutDecision.domain.user.service;

import KGUcapstone.OutDecision.domain.user.dto.PasswordRequestDTO.UpdatePasswordDTO;
import KGUcapstone.OutDecision.domain.user.dto.PasswordResponseDTO.UpdatePasswordResultDTO;


public interface PasswordService {
    UpdatePasswordResultDTO updatePassword(Long memberId, UpdatePasswordDTO request);
}
