package com.netcracker.controllers.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchGroupsFormValidator implements Validator {

    @Override
    public boolean supports( Class<?> aClass ){
        return SearchGroupsForm.class.equals( aClass );
    }

    @Override
    public void validate( Object o , Errors errors ){

        SearchGroupsForm form = ( SearchGroupsForm ) o;
        Pattern p           = Pattern.compile( "[\\d\\w-_\\*\\?]*" );

        if( !p.matcher( form.name ).matches() ){
            errors.rejectValue("name", "", "Недопустимые символы в названии");
        }

        if( !p.matcher( form.ownerName ).matches() ){
            errors.rejectValue("ownerName", "","Недопустимые символы в имени владельца");
        }


        if ( form.name.isEmpty() && form.ownerName.isEmpty() )
            errors.rejectValue("name", "", "Введите хотя бы один критерий поиска");


    }
}
