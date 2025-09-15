package com.app.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Table
@Entity
@Data
@RequiredArgsConstructor
public class Note {
    @Id
    private long id;
    @Column
    private long authorId;
    @Column
    private String rawText;
    @Column
    private String summary;
    @Column
    private String status;
    @Column
    private Timestamp timestamp;
}
