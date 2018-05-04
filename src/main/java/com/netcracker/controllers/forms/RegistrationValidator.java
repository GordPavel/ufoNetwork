package com.netcracker.controllers.forms;

import com.netcracker.repository.PersonRepository;
import com.netcracker.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class RegistrationValidator implements Validator{

    @Autowired PersonRepository personRepository;
    @Autowired RaceRepository   raceRepository;

    @Override
    public boolean supports( Class<?> aClass ){
        return RegistrationForm.class.equals( aClass );
    }

    @Override
    public void validate( Object o , Errors errors ){
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "login" ,
                                                   "" ,
                                                   "Поле не может быть пустым" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "pass" ,
                                                   "" ,
                                                   "Поле не может быть пустым" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "passAccept" ,
                                                   "" ,
                                                   "Поле не может быть пустым" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "race" ,
                                                   "" ,
                                                   "Поле не может быть пустым" );
        if( errors.hasErrors() ) return;
        RegistrationForm form = ( RegistrationForm ) o;
        if( !form.pass.equals( form.passAccept ) )
            errors.rejectValue( "passAccept" , "" , "Пароли должны совпадать" );
        if( form.image.isEmpty() ) form.setImage( null );
        else if( !form.image.getOriginalFilename().matches( "^.+\\.(jpg|jpeg|png)$" ) )
            errors.rejectValue( "image" ,
                                "" ,
                                "Можно загружать только в форматах jpg | jpeg | png" );

        Pattern pattern = Pattern.compile( "[\\d\\w-_]*" );
        if (!pattern.matcher( form.name ).matches()) {
            errors.rejectValue( "name" ,
                    "" ,
                    "Имя может состоять только из букв, цифр и символов - и _" );
        }

        if (!pattern.matcher( form.sex ).matches()) {
            errors.rejectValue( "sex" ,
                    "" ,
                    "Название пола может состоять только из букв, цифр и символов - и _" );
        }

        if (!pattern.matcher( form.race ).matches()) {
            errors.rejectValue( "race" ,
                    "" ,
                    "Название расы может состоять только из букв, цифр и символов - и _" );
        }

        if (!pattern.matcher( form.login ).matches()) {
            errors.rejectValue( "login" ,
                    "" ,
                    "Логин может состоять только из букв, цифр и символов - и _" );
        }

        Pattern numberPattern = Pattern.compile( "[\\d]*" );
        if (!numberPattern.matcher( form.age ).matches()) {
            errors.rejectValue( "age" ,
                    "" ,
                    "Возраст может быть только целым числом" );
        }

        if (personRepository.getByLogin(form.login).isPresent()) {
            errors.rejectValue( "login" ,
                    "" ,
                    "Этот логин занят, выберите себе другой" );
        }
    }
}
