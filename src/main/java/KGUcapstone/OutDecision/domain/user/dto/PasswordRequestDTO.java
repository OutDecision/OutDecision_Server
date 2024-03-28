package KGUcapstone.OutDecision.domain.user.dto;

import lombok.Getter;

public class PasswordRequestDTO {
    @Getter
    public static class UpdatePasswordDTO{
        String currentPassword;
        String newPassword;
        String confirmNewPassword;
    }
}
