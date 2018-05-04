package com.netcracker.controllers.forms;

import com.netcracker.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchPersonsFormValidator implements Validator {

    @Autowired RaceService raceService;

    @Override
    public boolean supports( Class<?> aClass ){
        return SearchPersonsForm.class.equals( aClass );
    }

    @Override
    public void validate( Object o , Errors errors ){

        SearchPersonsForm form = ( SearchPersonsForm ) o;
        Pattern p           = Pattern.compile( "[\\d\\w-_\\*\\?]*" );

        if( !p.matcher( form.name ).matches() ){
            errors.rejectValue("name", "", "Недопустимые символы в имени");
        }

        if( !p.matcher( form.sex ).matches() ){
            errors.rejectValue("sex", "","Недопустимые символы");
        }

        Pattern pNumber       = Pattern.compile( "[\\d]*" );
        if (pNumber.matcher(form.ageFrom).matches()&&pNumber.matcher(form.ageTo).matches()){

            if( (form.ageFrom.isEmpty() ? 0 : Integer.parseInt(form.ageFrom))
                    > (form.ageTo.isEmpty() ? Integer.MAX_VALUE :Integer.parseInt(form.ageTo ) ) ){
                errors.rejectValue("ageFrom", "", "Недопустимый диапазон возраста");
            }
        } else {
            errors.rejectValue("ageFrom", "", "Можно использовать только положительные цифры");
        }

        if (!form.race.isEmpty()) {
            if (!raceService.getByName(form.race).isPresent()) {
                errors.rejectValue("race", "", "Такой расы нет!");
            }
        }
        if ( form.name.isEmpty() && form.ageFrom.isEmpty() && form.ageTo.isEmpty()
                && form.race.isEmpty() && form.sex.isEmpty() )
            errors.rejectValue("sex", "", "Введите хотя бы один критерий поиска");


    }
}

