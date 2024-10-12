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
import ru.yudina.tutorbot.entity.user.Action;
import ru.yudina.tutorbot.entity.user.Role;
import ru.yudina.tutorbot.entity.user.User;
import ru.yudina.tutorbot.repository.UserRepo;
import ru.yudina.tutorbot.service.manager.AuthManager;
import ru.yudina.tutorbot.telegram.Bot;

@Aspect
@Order(100)
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class AuthAspect {

    UserRepo userRepo;

    AuthManager authManager;

    @Pointcut("execution(* ru.yudina.tutorbot.service.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut() {
    }

    @Around("distributeMethodPointcut()")
    public Object authMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("зашел в метод AuthAspect.authMethodAdvice");
        Update update = (Update) joinPoint.getArgs()[0];
        User user;

        if (update.hasMessage()) {
            user = userRepo.findById(update.getMessage().getChatId()).get();
        } else if (update.hasCallbackQuery()) {
            user = userRepo.findById(update.getCallbackQuery().getMessage().getChatId()).get();
        } else {
            return joinPoint.proceed();
        }

        if (user.getRole() != Role.EMPTY) {
            return joinPoint.proceed();
        }

        if (user.getAction() == Action.AUTH) {
            return joinPoint.proceed();
        }

        log.info("Перед вызовом authManager.answerMessage");
        return authManager.answerMessage(update.getMessage(), (Bot) joinPoint.getArgs()[1]);
    }
}
