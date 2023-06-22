package com.devmountain.noteApp.entities;
import com.devmountain.noteApp.dtos.NoteDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "Notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String body;

    @Column(columnDefinition = "text")
    private String imageData;

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