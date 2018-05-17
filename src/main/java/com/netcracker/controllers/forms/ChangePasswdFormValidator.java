package com.netcracker.controllers.forms;

import com.netcracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ChangePasswdFormValidator implements Validator {

    @Autowired
    PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePasswdForm.class.equals(aClass);
    }

    @Override
    public void validate( Object o, Errors errors){

        ValidationUtils.rejectIfEmptyOrWhitespace( errors,
                "oldPasswd",
                "",
                "Inser your old password!");
        ValidationUtils.rejectIfEmptyOrWhitespace( errors,
                "newPasswd",
                "",
                "Inser your new password!");
        ValidationUtils.rejectIfEmptyOrWhitespace( errors,
                "acceptPasswd",
                "",
                "Confirm password");

        if( errors.hasErrors() ) return;

        ChangePasswdForm form = (ChangePasswdForm)( o );

        if ( !form.newPasswd.equals( form.acceptPasswd ))
            errors.rejectValue( "acceptPasswd" , "Password and confirm not same" );


    }

}
