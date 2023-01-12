package project.reviews.login;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/*
* 2022-09-23
* 회원가입 2차 비밀번호 확인 검증기
* */
@Component
public class JoinValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    /*
    * 회원가입시 입력하는 패스워드와 2차 패스워드가 동일한지 비교하는 로직
    * */
    @Override
    public void validate(Object target, Errors errors) {
        JoinForm joinForm = (JoinForm) target;

        if(!joinForm.getPassword().equals(joinForm.getCheck_password())) {
            errors.rejectValue("password", "NotEquals");
            errors.rejectValue("check_password", "NotEquals");
        }
    }
}
