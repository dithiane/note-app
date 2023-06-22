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

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<NoteDto> getAllNotesByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            List<Note> noteList = noteRepository.findAllByUserEquals(userOptional.get());
            return noteList.stream().map(NoteDto::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
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