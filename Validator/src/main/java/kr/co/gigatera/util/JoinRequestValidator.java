package kr.co.gigatera.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class JoinRequestValidator implements Validator {
	
	private static final String emailExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	
	public JoinRequestValidator() {
		pattern = Pattern.compile(emailExp);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return JoinRequest.class.isAssignableFrom(clazz);
		//return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		JoinRequest joinReq = (JoinRequest) target;
        
        if(joinReq.getEmail() == null || joinReq.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "required", "필수 정보 입니다.");
        } else {
            Matcher matcher = pattern.matcher(joinReq.getEmail());
            if(!matcher.matches()) {
                errors.rejectValue("email", "bad", "올바르지 않는 이메일 형식입니다.");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uid", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uname", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmpty(errors, "pwd", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmpty(errors, "pwd2", "required", "필수 정보 입니다.");
        if(!joinReq.getPwd().isEmpty()) {
            if(!joinReq.isPwdSame()) {
                errors.rejectValue("pwd2", "nomatch", "비밀번호가 일치하지 않습니다.");
            }
        }
		
	}
	
}
