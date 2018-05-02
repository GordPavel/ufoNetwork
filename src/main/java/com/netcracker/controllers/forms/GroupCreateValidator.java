package com.netcracker.controllers.forms;

import com.netcracker.DAO.GroupEntity;
import com.netcracker.DAO.PersonEntity;
import com.netcracker.repository.GroupRepository;
import com.netcracker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GroupCreateValidator  implements Validator {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public boolean supports( Class<?> aClass ){
        return GroupCreateForm.class.equals( aClass );
    }

    @Override
    public void validate( Object o , Errors errors ){
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "name" ,
                "" ,
                "Поле не может быть пустым" );
        if( errors.hasErrors() ) return;

        GroupCreateForm form = ( GroupCreateForm ) o;
        if( !groupRepository.getByName( form.name ).isEmpty() ){
            errors.rejectValue( "name" ,
                    "" ,
                    "Такая группа уже существует существует" );
            System.out.print("test");
        }
        if( form.image.isEmpty() ) form.setImage( null );
        else if( !form.image.getOriginalFilename().matches( "^.+\\.(jpg|jpeg|png)$" ) )
            errors.rejectValue( "image" ,
                    "" ,
                    "Можно загружать только в форматах jpg | jpeg | png" );
    }
}