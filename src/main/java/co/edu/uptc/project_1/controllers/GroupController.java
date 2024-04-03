package co.edu.uptc.project_1.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.project_1.dtos.GroupDTO;
import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Group;
import co.edu.uptc.project_1.model.Subject;
import co.edu.uptc.project_1.services.GroupServices;
import co.edu.uptc.project_1.services.PlaceServices;
import co.edu.uptc.project_1.services.SubjectService;
import co.edu.uptc.project_1.utils.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/group")
public class GroupController {

    private GroupServices services;
    @Autowired
    private PlaceServices placeServices;
    @Autowired
    private SubjectService subjectService;

    public GroupController(SubjectService subjectService, PlaceServices placeServices) {
        services = new GroupServices();
        this.placeServices = placeServices;
        this.subjectService = subjectService;
        test();
    }

    public void test() {
        Schedule[] schedules = new Schedule[3];

        try {
            schedules[0] = new Schedule(DayOfWeek.MONDAY, LocalTime.of(13, 0), 2);
            schedules[1] = new Schedule(DayOfWeek.TUESDAY, LocalTime.of(13, 0), 2);
            schedules[2] = new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), 2);
            services.add(new Group("1", "1", "1", schedules));

        } catch (Exception e) {

        }
    }

    @GetMapping()
    public ResponseEntity<Object> getGroups() {
        List<Group> groups;
        try {
            groups = services.getGroups();
            return ResponseEntity.status(HttpStatus.OK).body(groups);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getSubject(@PathVariable String id) {
        try {
            Group group = services.getGroup(id);
            if (group == null) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> postSubject(@RequestBody GroupDTO group) {
        try {
            services.add(GroupDTO.convertToDto(group));
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePlace(@PathVariable String id) {

        try {
            Group group = services.getGroup(id);
            services.deleteGroup(id);
            return ResponseEntity.status(HttpStatus.OK).body(group);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<Object> postModify(@PathVariable String id, @RequestBody GroupDTO groupDTO) {
        try {
            services.modifyGroup(id, GroupDTO.convertToDto(groupDTO));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(groupDTO);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @GetMapping("/getWith/place/{id}")
    public ResponseEntity<Object> getSubjectWithPlace(@PathVariable String id) {
        load();
        try {
            List<Subject> subjects = services.subjectsWithPlace(id, placeServices, subjectService);
            return ResponseEntity.status(HttpStatus.OK).body(subjects);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @GetMapping("/getWith/group/{id}")
    public ResponseEntity<Object> getSubjectWithGroup(@PathVariable String id) {
        load();
        try {
            List<Subject> subjects = services.subjectWithGroup(subjectService);
            return ResponseEntity.status(HttpStatus.OK).body(subjects);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping("/getWith/schedule/")
    public ResponseEntity<Object> getSubjectWithSchedule(@RequestBody Schedule[] schedule) {
        load();
        try {
            List<Subject> subjects = services.subjectWithScheudel(subjectService, schedule);
            return ResponseEntity.status(HttpStatus.OK).body(subjects);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    public void load() {
        if (placeServices == null) {
            placeServices = new PlaceServices();
        }
        if (subjectService == null) {
            subjectService = new SubjectService();
        }

    }

}
