package com.devmountain.noteApp.entities;

import com.devmountain.noteApp.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// The User class is an entity class in the Spring Framework.
// It represents a note entity that can be stored and retrieved from a database.

// @Entity annotation is used to indicate that the class is an entity and should be mapped to a database table.
@Entity
// @Table(name = "Users"): This annotation specifies the name of the database table to which the entity is mapped.
@Table(name = "Users")
// @Data annotation is provided by the Lombok library and is used to automatically generate getter and setter methods,
// toString(), equals(), and hashCode() methods, and other boilerplate code.
@Data
// @AllArgsConstructor annotation is provided by Lombok and generates a constructor with parameters
// for all fields in the class.
@NoArgsConstructor
// This annotation is provided by Lombok and generates a no-argument constructor for the class.
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    // @ManyToOne annotation is used to establish a many-to-one relationship with the User entity.
    // @JsonBackReference annotation is used to handle the serialization and deserialization of the relationship
    // when converting the entity to JSON.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Note> noteSet = new HashSet<>();

    public User(UserDto userDto){
        if (userDto.getUsername() != null){
            this.username = userDto.getUsername();
        }
        if (userDto.getPassword() != null){
            this.password = userDto.getPassword();
        }
    }

}