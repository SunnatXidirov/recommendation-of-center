package uz.team.triple.recommendationofcenter.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import uz.team.triple.recommendationofcenter.dto.error.AppErrorDTO;
import uz.team.triple.recommendationofcenter.exceptions.InvalidParameterException;
import uz.team.triple.recommendationofcenter.exceptions.ItemNotFoundException;
import uz.team.triple.recommendationofcenter.logging.TelegramAppender;

import java.time.LocalDateTime;

import static uz.team.triple.recommendationofcenter.utils.WebUtils.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final TelegramAppender telegramAppender;
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int PAYMENT_REQUIRED = 402;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int CONFLICT = 409;
    private static final int INTERNAL_SERVER_ERROR = 500;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorDTO> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        AppErrorDTO appErrorDTO = new AppErrorDTO(
                request.getRequestURI(),
                "Serverdagi ichki xatolik. Iltimos, administratorga murojaat qiling.",
                INTERNAL_SERVER_ERROR
        );
        log.error(" REQUEST URL | {} \n:| EXCEPTION MESSAGE | {}", request.getRequestURI(), e.getMessage());
        telegramAppender.sendTelegramMessage(buildMessage(e, request));
        return ResponseEntity.internalServerError().body(appErrorDTO);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<AppErrorDTO> handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        telegramAppender.sendTelegramMessage(buildMessage(e, request));
        return ResponseEntity.status(NOT_FOUND)
                .body(new AppErrorDTO(request.getRequestURI(),
                        "So'rov qilingan resurs topilmadi", NOT_FOUND));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<AppErrorDTO> handleItemNotFoundException(ItemNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new AppErrorDTO(request.getRequestURI(), e.getText(), NOT_FOUND));
    }

    @ExceptionHandler({InvalidParameterException.class})
    public ResponseEntity<AppErrorDTO> handleInvalidParamExceptions(InvalidParameterException e, HttpServletRequest request) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new AppErrorDTO(request.getRequestURI(), e.getText(), BAD_REQUEST));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<AppErrorDTO> handleBadRequestExceptions(Exception e, HttpServletRequest request) {
        telegramAppender.sendTelegramMessage(buildMessage(e, request));
        return ResponseEntity.status(BAD_REQUEST)
                .body(new AppErrorDTO(request.getRequestURI(),
                        "So'rov qilingan resurs topilmadi",
                        BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        MultiValueMap<String, String> errors = new LinkedMultiValueMap<>();
        e.getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getField(), fieldError.getDefaultMessage()));
        AppErrorDTO errorDTO = new AppErrorDTO(request.getRequestURI(),
                "So'rov qilingan resurs topilmadi",
                errors, BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(errorDTO);
    }

    @NotNull
    private String buildMessage(Exception e, HttpServletRequest request) {
        return STR."""
                @xidi1ov
                @Mavlonovich_java
                @BKuldashov
                Attention! ⚠️⚠️⚠️
                Host:\{getHost(request)}
                IP Address:\{getClientIpAddress(request)}
                Device type:\{getDeviceType(request)}
                User-Agent:\{getUserAgent(request)}
                HTTP Method:\{request.getMethod()}
                Path:\{request.getRequestURI()}
                Timestamp:\{LocalDateTime.now()}
                Exception class:\{e.getClass().getName()}
                Unhandled exception:
                \{e.getMessage()}
                """;
    }
}
