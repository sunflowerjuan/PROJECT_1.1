package co.edu.uptc.project_1.dtos;

import co.edu.uptc.project_1.model.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceDTO {
    private final String id;
    private final String name;
    private final String location;

    public static PlaceDTO convertToPlace(Place place) {
        return new PlaceDTO(place.getId(), place.getName(), place.getLocation());
    }

    public static Place convertToDto(PlaceDTO placeDTO) {

        return new Place(placeDTO.getId(), placeDTO.getName(), placeDTO.getLocation());
    }
}
