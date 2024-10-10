package ru.yudina.tutorbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yudina.tutorbot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
