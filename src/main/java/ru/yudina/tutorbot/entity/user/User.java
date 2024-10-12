package ru.yudina.tutorbot.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Column(name = "id")
    @Id
    Long chatId;

    @Column(name = "token", unique = true)
    UUID token;

    @Enumerated(EnumType.STRING)
    Role role;

    @Enumerated(EnumType.STRING)
    Action action;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id")
    UserDetails userDetails;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            name = "relationships")
    List<User> users;

    // метод запускается перед каждым сохранением пользователя в БД
    @PrePersist
    private void generateUniqueToken() {
        if (token == null) {
            token = UUID.randomUUID();
        }
    }
}
