package KGUcapstone.OutDecision.global.error.handler;


import KGUcapstone.OutDecision.global.error.dto.BaseErrorCode;
import KGUcapstone.OutDecision.global.error.exception.GeneralException;

public class PasswordHandler extends GeneralException {

    public PasswordHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
