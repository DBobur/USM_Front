package uz.pro.usm_front.domain.dto.request.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppErrorRequest {
    private String errorPath;
    private String errorMessage;
    private Integer errorCode;
    private LocalDateTime timeStamp;
}
