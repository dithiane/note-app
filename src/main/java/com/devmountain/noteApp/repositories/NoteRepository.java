package com.devmountain.noteApp.repositories;

import com.devmountain.noteApp.entities.Note;
import com.devmountain.noteApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// The NoteRepository interface is a repository interface in the Spring Framework.
// It extends the JpaRepository interface, which is a part of Spring Data JPA, and provides methods for performing
// CRUD (Create, Read, Update, Delete) operations on the Note entity.

// @Repository annotation is used to indicate that the interface is a repository component.
// It allows the Spring container to automatically detect and create an instance of the repository bean
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserEquals(User user);
    List<Note> findAllByBodyEquals(String body);
}