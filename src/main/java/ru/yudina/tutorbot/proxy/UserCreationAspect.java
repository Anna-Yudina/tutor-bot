package ru.yudina.tutorbot.proxy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.yudina.tutorbot.entity.user.Action;
import ru.yudina.tutorbot.entity.user.Role;
import ru.yudina.tutorbot.entity.user.UserDetails;
import ru.yudina.tutorbot.repository.DetailsRepo;
import ru.yudina.tutorbot.repository.UserRepo;

import java.time.LocalDateTime;

@Component
@Aspect
@Order(10)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class UserCreationAspect {

    UserRepo userRepo;

    DetailsRepo detailsRepo;

    @Pointcut("execution(* ru.yudina.tutorbot.service.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut() {
    }

    @Around("distributeMethodPointcut()")
    public Object distributeMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Update update = (Update) joinPoint.getArgs()[0];
        User telegramUser;

        if (update.hasMessage()) {
            telegramUser = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            telegramUser = update.getCallbackQuery().getFrom();
        } else {
            return joinPoint.proceed();
        }

        if (userRepo.existsById(telegramUser.getId())) {
            return joinPoint.proceed();
        }

        UserDetails userDetails = UserDetails.builder()
                .name(telegramUser.getUserName())
                .firstName(telegramUser.getFirstName())
                .lastName(telegramUser.getLastName())
                .registeredAt(LocalDateTime.now())
                .build();

        detailsRepo.save(userDetails);

        ru.yudina.tutorbot.entity.user.User user = ru.yudina.tutorbot.entity.user.User.builder()
                .chatId(telegramUser.getId())
                .action(Action.FREE)
                .role(Role.EMPTY)
                .userDetails(userDetails)
                .build();

        userRepo.save(user);

        log.info("сохранили пользователя");

        return joinPoint.proceed();
    }
}
