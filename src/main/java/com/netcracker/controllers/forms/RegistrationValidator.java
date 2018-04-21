package com.netcracker.controllers.forms;

import com.netcracker.repository.PersonRepository;
import com.netcracker.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
        if( errors.hasErrors() ) return;

        RegistrationForm form = ( RegistrationForm ) o;
        if( !form.pass.equals( form.passAccept ) )
            errors.rejectValue( "passAccept" , "" , "Пароли должны совпадать" );
        if( !raceRepository.getByName( form.race ).isPresent() )
            errors.rejectValue( "race" , "" , "Такой расы не существует в системе" );
        if( form.image.isEmpty() ) form.setImage( null );
        else if( !form.image.getOriginalFilename().matches( "^.+\\.(jpg|jpeg|png)$" ) )
            errors.rejectValue( "image" ,
                                "" ,
                                "Можно загружать только в форматах jpg | jpeg | png" );

    }
}
