package co.edu.uptc.project_1.services;

import java.util.List;

import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Group;
import co.edu.uptc.project_1.utils.Schedule;
import co.edu.uptc.services.myList.SimpleList;

public class GroupServices {
    private List<Group> groupList = new SimpleList<>();

    public void add(Group group) throws ProjectExeption {
        try {
            if (verify(group)) {
                groupList.add(group);
            } else {
                throw new ProjectExeption(TypeMessage.DUPLICATE_GROUP);
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

    public boolean verify(Group group) {
        // Verificar si existe algún grupo con el mismo lugar y horario
        for (Group existingGroup : groupList) {
            if (existingGroup.getPlaceId().equals(group.getPlaceId()) &&
                    hasOverlap(existingGroup.getSchedule(), group.getSchedule())) {
                // Si se encuentra un grupo con el mismo lugar y horario, retorno false
                return false;
            }
        }
        // Si no se encuentra ningún grupo con el mismo lugar y horario, retorno true
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
}
