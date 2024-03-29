package co.edu.uptc.project_1.dtos;

import co.edu.uptc.project_1.model.Group;
import co.edu.uptc.project_1.utils.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupDTO {
    private final String subjectId;
    private final String placeId;
    private final Schedule[] schedule;

    public static GroupDTO convertToGroup(Group group) {
        return new GroupDTO(group.getSubjectId(), group.getPlaceId(), group.getSchedule());
    }

    public static Group convertToDto(GroupDTO groupDTO) {

        return new Group(groupDTO.getSubjectId(), groupDTO.getPlaceId(), groupDTO.getSchedule());
    }
}
