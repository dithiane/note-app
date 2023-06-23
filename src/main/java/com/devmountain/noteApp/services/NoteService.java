package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.NoteDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<NoteDto> getAllNotesByUserId(Long userId);

    List<NoteDto> getAllNotesByBody(String body, Long userId);

    @Transactional
    void addNote(NoteDto noteDto, Long userId);

    @Transactional
    void deleteNoteById(Long noteId);

    @Transactional
    void updateNoteById(NoteDto noteDto);

    Optional<NoteDto> getNoteById(Long noteId);
}