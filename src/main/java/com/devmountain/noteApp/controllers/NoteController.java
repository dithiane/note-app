package com.devmountain.noteApp.controllers;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// The NoteController class is a controller class in the Spring Framework.
// It handles incoming HTTP requests related to notes and interacts with the NoteService to perform
// the necessary operations.


// @RestController: This annotation is used to indicate that the class is a RESTful controller.
// It combines the @Controller and @ResponseBody annotations, simplifying the creation of RESTful APIs.

// @RequestMapping("api/v1/notes"): This annotation is used to specify the base URL path for all the endpoints defined
// in the controller.
@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    //  This annotation maps the HTTP GET requests with the URL pattern /api/v1/notes/user/{userId}
    //  to the getNotesByUser() method.
    //  It retrieves a list of NoteDto objects associated with the specified user ID.
    @GetMapping("/user/{userId}")
    public List<NoteDto> getNotesByUser(@PathVariable Long userId){
        return noteService.getAllNotesByUserId(userId);
    }

    // This annotation maps the HTTP POST requests with the URL pattern /api/v1/notes/user/body/{userId}
    // to the getNotesByBody() method. It retrieves a list of NoteDto objects based on the provided request body and user ID.
    @PostMapping("/user/body/{userId}")
    public List<NoteDto> getNotesByBody(@RequestBody(required = false) String body, @PathVariable Long userId){
        return noteService.getAllNotesByBody(body, userId);
    }

    // This annotation maps the HTTP GET requests with the URL pattern /api/v1/notes/{noteId} to the getNoteById() method.
    // It retrieves a single NoteDto object based on the provided note ID
    @GetMapping("/{noteId}") // http://localhost:8080/api/v1/notes/user/1
    public Optional<NoteDto> getNoteById(@PathVariable Long noteId){
        return noteService.getNoteById(noteId);
    }

    // This annotation maps the HTTP POST requests with the URL pattern /api/v1/notes/user/{userId} to the addNote() method.
    // It adds a new note by accepting a NoteDto object in the request body and associating it with the specified user ID.
    @PostMapping("/user/{userId}")
    public void addNote(@RequestBody NoteDto noteDto, @PathVariable Long userId){
        noteService.addNote(noteDto, userId);
    }

    //  This annotation maps the HTTP DELETE requests with the URL pattern /api/v1/notes/{noteId} to the deleteNoteById() method.
    //  It deletes a note based on the provided note ID.
    @DeleteMapping("/{noteId}")
    public void deleteNoteById(@PathVariable Long noteId){
        noteService.deleteNoteById(noteId);
    }

    // This annotation maps the HTTP PUT requests with the URL pattern /api/v1/notes to the updateNote() method.
    // It updates an existing note by accepting a NoteDto object in the request body.
    @PutMapping //http://localhost:8080/api/v1/notes
    public void updateNote(@RequestBody NoteDto noteDto){
        noteService.updateNoteById(noteDto);
    }
}