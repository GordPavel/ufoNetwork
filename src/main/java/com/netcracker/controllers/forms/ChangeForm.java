package com.netcracker.controllers.forms;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ChangeForm {

    String login;
    String name;
    String age;
    String race;
    String sex;
}
