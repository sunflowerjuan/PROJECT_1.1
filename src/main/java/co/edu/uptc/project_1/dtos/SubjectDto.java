package co.edu.uptc.project_1.dtos;

import co.edu.uptc.project_1.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubjectDto {
    private final String id;
    private final String name;

    public static SubjectDto convertToSubject(Subject subject) {
        return new SubjectDto(subject.getId(), subject.getName());
    }

    public static Subject convertToDto(SubjectDto subjectDto) {

        return new Subject(subjectDto.getId(), subjectDto.getName());
    }

}
