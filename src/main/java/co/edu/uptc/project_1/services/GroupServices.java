package co.edu.uptc.project_1.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Group;
import co.edu.uptc.project_1.model.Subject;
import co.edu.uptc.project_1.utils.Schedule;
import co.edu.uptc.services.myList.SimpleList;

@Service
public class GroupServices {
    private List<Group> groupList = new SimpleList<>();

    public void add(Group group) throws ProjectExeption {
        try {
            if (verify(group)) {
                groupList.add(group);
            } else {
                throw new ProjectExeption(TypeMessage.DUPLICATE);
            }
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
    }

    public List<Group> getGroups() throws ProjectExeption {
        return groupList;
    }

    public void deleteGroup(String id) throws ProjectExeption {
        try {
            Group deleteGroup = getGroup(id);
            if (deleteGroup == null) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            groupList.remove(deleteGroup);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
    }

    public void modifyGroup(String id, Group newGroup) throws ProjectExeption {
        if (!newGroup.getId().equals(id) || getGroup(id) == null) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND);
        }
        try {
            deleteGroup(id);
            add(newGroup);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
    }

    public Group getGroup(String id) throws ProjectExeption {
        try {
            for (Group Group : groupList) {
                if (Group.getId().equals(id)) {
                    return Group;
                }
            }

        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
        return null;
    }

    public boolean verify(Group group) throws ProjectExeption {
        if (getGroup(group.getId()) != null) {
            throw new ProjectExeption(TypeMessage.DUPLICATE);
        }
        for (Group existingGroup : groupList) {
            if (existingGroup.getPlaceId().equals(group.getPlaceId()) &&
                    hasOverlap(existingGroup.getSchedule(), group.getSchedule())) {
                return false;
            }
        }
        return true;
    }

    private boolean hasOverlap(Schedule[] schedule1, Schedule[] schedule2) {
        for (Schedule s1 : schedule1) {
            for (Schedule s2 : schedule2) {
                if (s1.overlapsWith(s2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Subject> subjectsWithPlace(String placeId, PlaceServices placeServices, SubjectService subjectService)
            throws ProjectExeption {
        List<Subject> response = new SimpleList<>();
        if (placeServices.getPlace(placeId) != null) {
            for (Group group : groupList) {
                if (group.getPlaceId().equals(placeId)) {
                    response.add(subjectService.getSubject(group.getSubjectId()));
                }
            }
        } else {
            throw new ProjectExeption(TypeMessage.NOT_FOUND);
        }
        return response;
    }

    public List<Subject> subjectWithGroup(SubjectService subjectService) throws ProjectExeption {
        List<Subject> response = new SimpleList<>();
        Map<String, Integer> subjectCountMap = new HashMap<>();
        for (Group group : groupList) {
            String subjectId = group.getSubjectId();
            subjectCountMap.put(subjectId, subjectCountMap.getOrDefault(subjectId, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : subjectCountMap.entrySet()) {
            if (entry.getValue() > 1) {
                Subject subject = subjectService.getSubject(entry.getKey());
                if (subject != null) {
                    response.add(subject);
                }
            }
        }
        return response;
    }

    public List<Subject> subjectWithScheudel(SubjectService subjectService, Schedule[] schedule)
            throws ProjectExeption {
        List<Subject> response = new SimpleList<>();
        for (Group group : groupList) {
            if (hasOverlap(group.getSchedule(), schedule)) {
                response.add(subjectService.getSubject(group.getSubjectId()));
            }
        }
        return response;
    }

}
