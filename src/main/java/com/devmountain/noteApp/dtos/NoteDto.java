package com.devmountain.noteApp.dtos;

import com.devmountain.noteApp.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
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
