package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.entities.Note;
import com.devmountain.noteApp.entities.User;
import com.devmountain.noteApp.repositories.NoteRepository;
import com.devmountain.noteApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// @Service annotation is used to indicate that the class is a service component.
// It allows the Spring container to automatically detect and create an instance of the service bean.
@Service
public class NoteServiceImpl implements NoteService {
    // UserRepository: This is a repository interface that provides methods for performing database operations
    // on the User entity. It is autowired into the NoteServiceImpl class using the @Autowired annotation
    @Autowired
    private UserRepository userRepository;
    // NoteRepository: This is a repository interface that provides methods for performing database operations
    // on the Note entity. It is autowired into the NoteServiceImpl class using the @Autowired annotation
    @Autowired
    private NoteRepository noteRepository;

    // The @Override annotation is used to indicate that a method in a subclass is intended
    // to override a method with the same signature in its superclass or interface.
    @Override
    public List<NoteDto> getAllNotesByUserId(Long userId){
        //  Optional - It is used to represent an object that may or may not contain a non-null value.
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            List<Note> noteList = noteRepository.findAllByUserEquals(userOptional.get());
            return noteList.stream().map(NoteDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    //  The getAllNotesByBody method retrieves a list of NoteDto objects based on the provided body and userId.
    //  It filters the notes based on the body parameter if it is not null and returns the resulting list of NoteDto objects.
    @Override
    public List<NoteDto> getAllNotesByBody(String body, Long userId){
        return userRepository.findById(userId)
                .map(user -> {
                    List<Note> noteList = noteRepository.findAllByUserEquals(user);
                    if (body != null) {
                        return noteList.stream()
                                .filter(note -> note.getBody().contains(body))
                                .map(NoteDto::new)
                                .collect(Collectors.toList());
                    } else {
                        return noteList.stream()
                                .map(NoteDto::new)
                                .collect(Collectors.toList());
                    }
                })
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional // It means that when this method is invoked, a transaction will be started before the method
    // execution and committed after the method completes. If an exception occurs during the method execution,
    // the transaction will be rolled back, undoing any changes made within the method.
    public void addNote(NoteDto noteDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Note note = new Note(noteDto);
        userOptional.ifPresent(note::setUser);
        noteRepository.saveAndFlush(note);
    }

    @Override
    @Transactional
    public void deleteNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        noteOptional.ifPresent(note -> noteRepository.delete(note));
    }

    @Override
    @Transactional
    public void updateNoteById(NoteDto noteDto) {
        Optional<Note> noteOptional = noteRepository.findById(noteDto.getId());
        noteOptional.ifPresent(note -> {
            note.setBody(noteDto.getBody());
            noteRepository.saveAndFlush(note);
        });
    }

    @Override
    public Optional<NoteDto> getNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        return noteOptional.map(NoteDto::new);
    }
}