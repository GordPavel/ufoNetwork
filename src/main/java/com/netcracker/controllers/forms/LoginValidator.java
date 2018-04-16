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
                                                   "Поле не может быть пустым" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                                                   "pass" ,
                                                   "" ,
                                                   "Поле не может быть пустым" );
        if( errors.hasErrors() ) return;

        LoginForm form = ( LoginForm ) o;
        Specification<PersonEntity> login =
                ( root , criteriaQuery , criteriaBuilder ) -> criteriaBuilder.equal( root.get(
                        "login" ) , form.login );
        Specification<PersonEntity> password =
                ( root , criteriaQuery , criteriaBuilder ) -> criteriaBuilder.equal( root.get(
                        "pass" ) , form.pass );
        if( !personRepository.findOne( login.and( password ) ).isPresent() ){
            errors.rejectValue( "login" ,
                                "" ,
                                "Пользователя с таким логином или паролем не существует" );
            errors.rejectValue( "pass" , "" , "" );
        }
    }
}
