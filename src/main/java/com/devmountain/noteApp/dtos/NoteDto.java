package com.devmountain.noteApp.dtos;

import com.devmountain.noteApp.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// The NoteDto class is a data transfer object (DTO) class.
// It is used to transfer data related to a note between different layers of an application,
// such as between the controller and service layers or between the service and data access layers

// @Data annotation is provided by the Lombok library and is used to automatically generate
// getter and setter methods, toString(), equals(), and hashCode() methods, and other boilerplate code.
@Data
// @AllArgsConstructor annotation is provided by Lombok and generates a constructor with parameters
// for all fields in the class.
@AllArgsConstructor
// This annotation is provided by Lombok and generates a no-argument constructor for the class.
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private String body;
    private String imageData;
    private UserDto userDto;

    public NoteDto(Note note) {
        if (note.getId() != null) {
            this.id = note.getId();
        }
        if (note.getBody() != null) {
            this.body = note.getBody();
        }
        if (note.getImageData() != null) {
            this.imageData = note.getImageData();
        }
    }
}
