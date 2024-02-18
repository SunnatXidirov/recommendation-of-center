package uz.team.triple.recommendationofcenter.dto.success;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public final class ResponseData<D> implements Serializable {
    private Boolean success;
    private HttpStatus httpStatus;
    private String message;
    private D data;
}