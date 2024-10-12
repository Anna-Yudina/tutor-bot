package ru.yudina.tutorbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yudina.tutorbot.entity.user.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
