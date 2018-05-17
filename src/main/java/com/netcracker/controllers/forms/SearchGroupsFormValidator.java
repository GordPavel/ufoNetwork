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
        Pattern p           = Pattern.compile( "[\\d\\w\\s-_\\*\\?]*" );

        if( !p.matcher( form.name ).matches() ){
            errors.rejectValue("name", "", "You must use only letters, numbers, spaces, *, ? - and _");
        }

        if( !p.matcher( form.ownerName ).matches() ){
            errors.rejectValue("ownerName", "","You must use only letters, numbers, spaces, *, ? - and _");
        }


        if ( form.name.isEmpty() && form.ownerName.isEmpty() )
            errors.rejectValue("name", "", "Insert atleast one creteria");


    }
}
