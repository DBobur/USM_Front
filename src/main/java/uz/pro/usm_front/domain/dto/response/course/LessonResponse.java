package uz.pro.usm_front.domain.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Long courseId;
}
