package co.edu.uptc.project_1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.uptc.project_1.exceptions.ProjectExeption;
import co.edu.uptc.project_1.exceptions.TypeMessage;
import co.edu.uptc.project_1.model.Place;
import co.edu.uptc.services.myList.SimpleList;

@Service
public class PlaceServices {
    private List<Place> placesList = new SimpleList<>();

    public void add(Place place) throws ProjectExeption {
        try {
            placesList.add(place);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }

    }

    public List<Place> getPlaces() throws ProjectExeption {
        return placesList;
    }

    public void deletePlaces(String id) throws ProjectExeption {
        try {
            Place deletePlace = getPlace(id);
            if (deletePlace == null) {
                throw new ProjectExeption(TypeMessage.NOT_FOUND);
            }
            placesList.remove(deletePlace);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
    }

    public void modifyPlace(String id, Place newPlace) throws ProjectExeption {
        if (!newPlace.getId().equals(id)) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND);
        }
        try {
            placesList.set(getIndex(id), newPlace);
        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
    }

    public Place getPlace(String id) throws ProjectExeption {
        try {
            for (Place place : placesList) {
                if (place.getId().equals(id)) {
                    return place;
                }
            }

        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.NOT_FOUND_FILE);
        }
        throw new ProjectExeption(TypeMessage.NOT_FOUND);
    }

    public int getIndex(String id) throws ProjectExeption {
        int pos = 0;
        try {
            for (Place place : placesList) {
                if (place.getId().equals(id)) {
                    return pos;
                }
                pos++;
            }

        } catch (Exception e) {
            throw new ProjectExeption(TypeMessage.ILEGAL_ACTION);
        }
        return -1;
    }
}
