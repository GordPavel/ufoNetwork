package com.netcracker.controllers.forms;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SearchPersonsForm {

    String name;
    String race;
    String ageFrom;
    String ageTo;
    String sex;
}
