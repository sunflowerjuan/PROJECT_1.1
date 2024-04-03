package co.edu.uptc.project_1.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.project_1.dtos.PlaceDTO;
import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.model.Place;
import co.edu.uptc.project_1.services.PlaceServices;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/place")

public class PlaceController {

    private PlaceServices services;

    public PlaceController() {
        services = new PlaceServices();
        test();
    }

    public void test() {
        try {
            services.add(new Place("1", "Mayorca", "uptc"));
            services.add(new Place("2", "Barcelona", "uptc"));
        } catch (ProjectExeption e) {

            e.printStackTrace();
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getPlaces() {
        List<Place> places;
        try {
            places = services.getPlaces();
            return ResponseEntity.status(HttpStatus.OK).body(places);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getSubject(@PathVariable String id) {
        try {
            Place place = services.getPlace(id);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping()
    public ResponseEntity<Object> postSubject(@RequestBody PlaceDTO place) {
        try {
            PlaceDTO.validatePlace(place);
            services.add(PlaceDTO.convertToDto(place));
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePlace(@PathVariable String id) {

        try {
            Place place = services.getPlace(id);
            services.deletePlaces(id);
            return ResponseEntity.status(HttpStatus.OK).body(place);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp())
                    .body(e.getMenssage());
        }

    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<Object> postModify(@PathVariable String id, @RequestBody PlaceDTO place) {
        try {
            services.modifyPlace(id, PlaceDTO.convertToDto(place));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(place);
        } catch (ProjectExeption e) {
            return ResponseEntity.status(e.getMenssage().getCodeHttp()).body(e.getMenssage());
        }
    }

}
