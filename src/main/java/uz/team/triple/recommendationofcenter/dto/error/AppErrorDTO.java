package uz.team.triple.recommendationofcenter.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import uz.team.triple.recommendationofcenter.dto.BaseDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppErrorDTO implements BaseDTO {
    private final String path;
    private final String message;
    private final Integer status;
    private final Object body;
    private final String timestamp;


    public AppErrorDTO(String path, String message, Integer status) {
        this(path, message, null, status);
    }

    public AppErrorDTO(String path, String message, Object body, Integer status) {
        this.path = path;
        this.message = message;
        this.body = body;
        this.status = status;
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Tashkent")).format(DateTimeFormatter.ISO_DATE_TIME);
    }
}

