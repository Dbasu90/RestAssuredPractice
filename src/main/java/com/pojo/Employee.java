package com.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = true)
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> jobs;

}
