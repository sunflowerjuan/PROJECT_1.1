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

}
