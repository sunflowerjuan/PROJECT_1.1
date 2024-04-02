package co.edu.uptc.project_1.services;

import java.util.List;

import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Subject;
import co.edu.uptc.services.myList.SimpleList;

@SuppressWarnings("unchecked")
public class SubjectService {
    private List<Subject> subjectList = new SimpleList<>();

    public void add(Subject subject) throws ProjectExeption {
        try {
            subjectList.add(subject);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }

    }

    public List<Subject> getSubjects() throws ProjectExeption {
        return subjectList;
    }

    public void deleteSubject(String id) throws ProjectExeption {
        try {
            Subject deleteSubject = getSubject(id);
            if (deleteSubject == null) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            subjectList.remove(deleteSubject);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
    }

    public void modifySubject(String id, Subject newSubject) throws ProjectExeption {
        if (!newSubject.getId().equals(id)) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND);
        }
        try {
            deleteSubject(id);
            add(newSubject);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
    }

    public Subject getSubject(String id) throws ProjectExeption {
        try {
            for (Subject subject : subjectList) {
                if (subject.getId().equals(id)) {
                    return subject;
                }
            }

        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
        return null;
    }

}
