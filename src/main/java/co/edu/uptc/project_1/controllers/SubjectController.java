package co.edu.uptc.project_1.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.project_1.dtos.SubjectDto;
import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Subject;
import co.edu.uptc.project_1.services.SubjectService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("subject")
public class SubjectController {
    SubjectService service = new SubjectService();

    @GetMapping()
    public ResponseEntity<Object> getSubjects() {
        List<Subject> subjects;

        try {
            subjects = service.getSubjects();
            return ResponseEntity.status(HttpStatus.OK).body(subjects);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> postSubject(@RequestBody SubjectDto subject) {
        try {
            SubjectDto.validateSubject(subject);
            service.add(SubjectDto.convertToDto(subject));
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable String id) {

        try {
            Subject subject = service.getSubject(id);
            service.deleteSubject(id);
            return ResponseEntity.status(HttpStatus.OK).body(subject);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<Object> postModify(@PathVariable String id, @RequestBody SubjectDto subject) {
        try {
            service.modifySubject(id, SubjectDto.convertToDto(subject));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(subject);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

}
