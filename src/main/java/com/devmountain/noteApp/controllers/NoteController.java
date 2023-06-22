package com.devmountain.noteApp.controllers;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/user/{userId}")
    public List<NoteDto> getNotesByUser(@PathVariable Long userId){
        return noteService.getAllNotesByUserId(userId);
    }

    @GetMapping("/{noteId}") // http://localhost:8080/api/v1/notes/user/1
    public Optional<NoteDto> getNoteById(@PathVariable Long noteId){
        return noteService.getNoteById(noteId);
    }

    @PostMapping("/user/{userId}")
    public void addNote(@RequestBody NoteDto noteDto, @PathVariable Long userId){
        noteService.addNote(noteDto, userId);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNoteById(@PathVariable Long noteId){
        noteService.deleteNoteById(noteId);
    }

    @PutMapping //http://localhost:8080/api/v1/notes
    public void updateNote(@RequestBody NoteDto noteDto){
        noteService.updateNoteById(noteDto);
    }
}