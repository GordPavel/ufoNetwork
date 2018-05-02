package com.netcracker.controllers.forms;

import com.netcracker.repository.PersonRepository;
import com.netcracker.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ChangeFormValidator implements Validator {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    RaceRepository raceRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangeForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "login",
                "",
                "Поле логин не может быть пустым");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "name",
                "",
                "Введите свое имя");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "age",
                "",
                "Укажите свой возраст");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "sex",
                "",
                "Укажите свой пол");
        if (errors.hasErrors()) return;

        ChangeForm form = (ChangeForm) o;

        if ( !raceRepository.getByName(form.race).isPresent())
            errors.rejectValue("race", "Такой расы нет в базе");

    }
}