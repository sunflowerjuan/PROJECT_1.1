package co.edu.uptc.project_1.services;

import java.util.List;

import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Subject;
import co.edu.uptc.services.FileManager.FileManager;
import co.edu.uptc.services.myList.SimpleList;

public class SubjectService {

    private String path = "subject.txt";
    private FileManager fileManager = new FileManager(path);

    public void add(Subject subject) throws ProjectExeption {
        try {
            fileManager.writeFile(convertToString(subject));
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }

    }

    public List<Subject> getSubjects() throws ProjectExeption {
        List<Subject> subjects = new SimpleList<>();
        try {
            List<String> stringSubjects = fileManager.readFile();
            for (String line : stringSubjects) {
                String[] data = line.split(",");
                Subject subject = new Subject();
                subject.setId(data[0]);
                subject.setName(data[1]);
                subjects.add(subject);
            }
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
        return subjects;
    }

    public void deleteSubject(String id) throws ProjectExeption {
        try {
            Subject deleteSubject = getSubject(id);
            if (deleteSubject == null) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            fileManager.deleteLine(convertToString(deleteSubject));
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
    }

    public void modifySubject(String id, Subject newSubject) throws ProjectExeption {
        try {
            if (!newSubject.getId().equals(id)) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            deleteSubject(id);
            add(newSubject);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
    }

    public Subject getSubject(String id) throws ProjectExeption {
        try {
            List<String> stringSubjects = fileManager.readFile();
            for (String line : stringSubjects) {
                String[] data = line.split(",");
                Subject subject = new Subject();
                if (data[0].equals(id)) {
                    subject.setId(data[0]);
                    subject.setName(data[1]);
                    return subject;
                }

            }
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
        return null;
    }

    public String convertToString(Subject subject) {
        return subject.getId() + "," +
                subject.getName();
    }
}
