package uz.team.triple.recommendationofcenter.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InputMediaDocument;
import com.pengrad.telegrambot.request.SendMediaGroup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.MessagesResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TelegramAppender extends AppenderBase<LoggingEvent> {
    private static final String exceptionsGroupID = "-1002074405338";
    private static final String logsGroupID = "-1002074405338";
    private final TelegramBot telegramBot = new TelegramBot("6480682699:AAHhpLTSWH__xjjDz-VdPWUgwc-9GAFG9YY");

    public TelegramAppender() {
        addFilter(new Filter<>() {
            @Override
            public FilterReply decide(LoggingEvent loggingEvent) {
                return loggingEvent.getLevel().equals(Level.ERROR) || loggingEvent.getLevel().equals(Level.WARN) ? FilterReply.ACCEPT : FilterReply.DENY;
            }
        });
    }

    @Async
    public void sendTelegramMessage(String message) {
        SendMessage telegramMessage = new SendMessage(exceptionsGroupID, message);
        telegramBot.execute(telegramMessage);
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        telegramBot.execute(new SendMessage(exceptionsGroupID, loggingEvent.toString()));
    }

    public final boolean sendTelegramLogs(InputMediaDocument message) {
        SendMediaGroup sendMediaGroup = new SendMediaGroup(logsGroupID, message);
        MessagesResponse execute = telegramBot.execute(sendMediaGroup);
        return execute.isOk();
    }
}