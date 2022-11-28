package com.test.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;




@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
    @Id
    private int userId;
    private String name;
    private int age;
    private String address;
}
