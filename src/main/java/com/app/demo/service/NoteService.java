package com.app.demo.service;

import com.app.demo.models.LLMResponseDto;
import com.app.demo.models.Note;
import com.app.demo.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OllamaService ollamaService;

    @Autowired
    public NoteService(NoteRepository noteRepository, OllamaService ollamaService, RabbitTemplate rabbitTemplate) {
        this.noteRepository = noteRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.ollamaService = ollamaService;
    }

    public Note save(Note note) {
        note.setStatus("queued");
        note.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        Note savedNote = this.noteRepository.save(note);
        this.rabbitTemplate.convertAndSend("notes.queue", savedNote);
        log.info("Job is queued successfully to RabbitMQ");
        return savedNote;
    }

    public Note findById(int id) {
        return this.noteRepository.findById((long) id).orElse(null);
    }

    @RabbitListener(queues = "notes.queue")
    private void queueReceiver(Note note) throws IOException {
        note.setStatus("processing");
        note = this.noteRepository.save(note);
        assert note.getStatus().equals("processing");
        log.info("Job is received by consumer and being processed successfully to RabbitMQ");

        LLMResponseDto llmResponseDto = this.ollamaService.getObjectFromJson(this.ollamaService.sendPostRequest(note));
        note.setSummary(llmResponseDto.getResponse());
        note.setStatus("done");
        log.info("Job is done successfully.");
        this.noteRepository.save(note);
    }
}
