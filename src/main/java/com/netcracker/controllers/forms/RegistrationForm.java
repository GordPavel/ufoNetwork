package com.netcracker.controllers.forms;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RegistrationForm{
    String        name;
    String        login;
    String        pass;
    String        passAccept;
    String        race;
    String       age;
    String        sex;
    MultipartFile image;
}
