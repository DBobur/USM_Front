package uz.pro.usm_front.domain.dto.request.course;

import lombok.Data;

@Data
public class LessonRequest {
    private String title;
    private String content;
    private Long courseId;
}
