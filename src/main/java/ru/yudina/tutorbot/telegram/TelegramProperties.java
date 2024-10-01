package ru.yudina.tutorbot.telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("telegram-bot")
@Getter
@Setter
public class TelegramProperties {

    private String username;
    private String token;
    private String path;
}
