package com.devmountain.noteApp.entities;
import com.devmountain.noteApp.dtos.NoteDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// The Note class is an entity class in the Spring Framework.
// It represents a note entity that can be stored and retrieved from a database.

// @Entity annotation is used to indicate that the class is an entity and should be mapped to a database table.
@Entity
// @Table(name = "Notes"): This annotation specifies the name of the database table to which the entity is mapped.
@Table(name = "Notes")
// @Data annotation is provided by the Lombok library and is used to automatically generate getter and setter methods,
// toString(), equals(), and hashCode() methods, and other boilerplate code.
@Data
// @AllArgsConstructor annotation is provided by Lombok and generates a constructor with parameters
// for all fields in the class.
@AllArgsConstructor
// This annotation is provided by Lombok and generates a no-argument constructor for the class.
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String body;

    @Column(columnDefinition = "text")
    private String imageData;

    // @ManyToOne annotation is used to establish a many-to-one relationship with the User entity.
    // @JsonBackReference annotation is used to handle the serialization and deserialization of the relationship
    // when converting the entity to JSON.
    @ManyToOne
    @JsonBackReference
    private User user;

    public Note(NoteDto noteDto){
        if (noteDto.getBody() != null){
            this.body = noteDto.getBody();
        }
        if (noteDto.getImageData() != null) {
            this.imageData = noteDto.getImageData();
        }
    }

}