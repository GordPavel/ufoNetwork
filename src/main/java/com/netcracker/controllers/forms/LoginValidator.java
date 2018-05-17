package com.netcracker.controllers.forms;

import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator{

    @Autowired PersonRepository personRepository;

    @Override
    public boolean supports( Class<?> aClass ){
        return LoginForm.class.equals( aClass );
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
        if( errors.hasErrors() ) return;

        LoginForm form = ( LoginForm ) o;
        Specification<PersonEntity> login = ( root , criteriaQuery , criteriaBuilder ) -> {
            return criteriaBuilder.equal( root.get( "login" ) , form.login );
        };
        Specification<PersonEntity> password = ( root , criteriaQuery , criteriaBuilder ) -> {
            return criteriaBuilder.equal( root.get( "pass" ) , form.pass );
        };
        if( !personRepository.findOne( login.and( password ) ).isPresent() ){
            errors.rejectValue( "login" ,
                                "" ,
                                "Such user or password doesn`t exist" );
            errors.rejectValue( "pass" , "" , "" );
        }
    }
}
