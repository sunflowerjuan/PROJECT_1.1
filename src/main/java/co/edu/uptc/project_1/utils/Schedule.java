package co.edu.uptc.project_1.utils;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private DayOfWeek day;
    private LocalTime starHour;
    private int duration;

    public boolean overlapsWith(Schedule otherSchedule) {
        if (!this.day.equals(otherSchedule.getDay())) {
            return false;
        }
        LocalTime endHour = this.starHour.plusMinutes(this.duration);
        LocalTime otherEndHour = otherSchedule.getStarHour().plusMinutes(otherSchedule.getDuration());
        return this.starHour.isBefore(otherEndHour) && endHour.isAfter(otherSchedule.getStarHour());
    }

}
