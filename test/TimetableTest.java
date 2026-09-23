import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> mondayTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTable = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondayTable.size());
        assertNull(tuesdayTable);

    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий

        TreeMap<TimeOfDay, List<TrainingSession>> mondayTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTable = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        TreeMap<TimeOfDay, List<TrainingSession>> tuesdayTable = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondayTable.size());

        TimeOfDay[] thursdayKeys = thursdayTable.keySet().toArray(TimeOfDay[]::new);

        assertEquals(13, thursdayKeys[0].getHours());
        assertEquals(20, thursdayKeys[1].getHours());

        assertNull(tuesdayTable);



}

@Test
void testGetTrainingSessionsForDayAndTime() {
    Timetable timetable = new Timetable();

    Group group = new Group("Акробатика для детей", Age.CHILD, 60);
    Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
    TrainingSession singleTrainingSession = new TrainingSession(group, coach,
            DayOfWeek.MONDAY, new TimeOfDay(13, 0));

    timetable.addNewTrainingSession(singleTrainingSession);

    //Проверить, что за понедельник в 13:00 вернулось одно занятие
    //Проверить, что за понедельник в 14:00 не вернулось занятий
    TreeMap<TimeOfDay, List<TrainingSession>> mondayTable = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

    assertEquals(1, mondayTable.get(new TimeOfDay(13, 0)).size());
    assertNull(mondayTable.get(new TimeOfDay(14, 0)));
}



}

