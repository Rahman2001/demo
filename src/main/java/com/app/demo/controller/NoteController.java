package com.app.demo.controller;

import com.app.demo.models.Note;
import com.app.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("notes")
    public Note queueNote(@RequestBody Note note) {
        return this.noteService.save(note);
    }

    @GetMapping("notes/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") int id) {
        Note note = this.noteService.findById(id);
        return note == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(note);
    }
}
