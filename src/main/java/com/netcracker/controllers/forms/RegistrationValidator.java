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
                                                   "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "pass" ,
                                                   "" ,
                                                   "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "passAccept" ,
                                                   "" ,
                                                   "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "race" ,
                                                   "" ,
                                                   "Can`t be empty" );
        if( errors.hasErrors() ) return;
        RegistrationForm form = ( RegistrationForm ) o;
        if( !form.pass.equals( form.passAccept ) )
            errors.rejectValue( "passAccept" , "" , "Password and confirm must be same" );
        if( form.image.isEmpty() ) form.setImage( null );
        else if( !form.image.getOriginalFilename().matches( "^.+\\.(jpg|jpeg|png)$" ) )
            errors.rejectValue( "image" ,
                                "" ,
                                "You can use only jpg | jpeg | png" );

        Pattern pattern = Pattern.compile( "[\\d\\w-_]*" );
        if (!pattern.matcher( form.name ).matches()) {
            errors.rejectValue( "name" ,
                    "" ,
                    "You must use only letters, numbers, - and _" );
        }

        if (!pattern.matcher( form.sex ).matches()) {
            errors.rejectValue( "sex" ,
                    "" ,
                    "You must use only letters, numbers, - and _" );
        }

        if (!pattern.matcher( form.race ).matches()) {
            errors.rejectValue( "race" ,
                    "" ,
                    "You must use only letters, numbers, - and _" );
        }

        if (!pattern.matcher( form.login ).matches()) {
            errors.rejectValue( "login" ,
                    "" ,
                    "You must use only letters, numbers, - and _" );
        }

        Pattern numberPattern = Pattern.compile( "[\\d]*" );
        if (!numberPattern.matcher( form.age ).matches()) {
            errors.rejectValue( "age" ,
                    "" ,
                    "You must use only integer" );
        }

        if (personRepository.getByLogin(form.login).isPresent()) {
            errors.rejectValue( "login" ,
                    "" ,
                    "Login already used, choose another" );
        }
    }
}
