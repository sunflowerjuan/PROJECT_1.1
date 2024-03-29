package co.edu.uptc.project_1.model;

import co.edu.uptc.project_1.utils.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Group {
    private String subjectId;
    private String placeId;
    private Schedule[] schedule;

    public Group(String subjectId, String placeId) {
        this.subjectId = subjectId;
        this.placeId = placeId;
        schedule = new Schedule[3];
    }

    public Group() {
        schedule = new Schedule[3];
    }
}
