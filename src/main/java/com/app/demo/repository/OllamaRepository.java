package com.app.demo.repository;

import com.app.demo.models.Note;

import java.io.IOException;

public interface OllamaRepository {
    String sendPostRequest(Note note) throws IOException;
}
