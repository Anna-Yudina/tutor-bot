package ru.yudina.tutorbot.entity.task;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Table(name = "tasks")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String tittle;

    @Column(name = "text_content")
    String textContent;

    @Column(name = "actual_message_id")
    Integer actualMessageId;
}
