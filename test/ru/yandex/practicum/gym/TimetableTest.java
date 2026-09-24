package ru.yandex.practicum.gym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        assertTrue(tuesdayTable.isEmpty());

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
        //Проверка занятия за понедельник
        assertEquals(1, mondayTable.size());

        TimeOfDay[] thursdayKeys = thursdayTable.keySet().toArray(TimeOfDay[]::new);
        //Проверка занятий за четверг
        assertEquals(13, thursdayKeys[0].getHours());
        assertEquals(20, thursdayKeys[1].getHours());
        //Проверка занятий за вторник
        assertTrue(tuesdayTable.isEmpty());


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

        List<TrainingSession> sessionsAt2PM = mondayTable.getOrDefault(new TimeOfDay(14, 0), new ArrayList<>());
        assertTrue(sessionsAt2PM.isEmpty());
    }

    //Три дополнительных тест кейса для таблицы
    //Проверка что два занятия с одинаковым временем сохранятся
    @Test
    void testGetTrainingSessionsForTheSameDayAndTime() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        Group group2 = new Group("Гимнастика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильева", "Анна", "Петровна");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(secondTrainingSession);

        List<TrainingSession> mondayTable = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        assertEquals(2, mondayTable.size());
    }

    // Получение расписания для абсолютно пустого расписания
    @Test
    void testGetTrainingSessionsFromEmptyTimetable() {
        Timetable timetable = new Timetable();
        Map<TimeOfDay, List<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);
        assertTrue(sessions.isEmpty());
    }

    // Удаление одного занятия из расписания
    @Test
    void testRemoveTrainingSessionFromTable() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        Group group2 = new Group("Гимнастика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильева", "Анна", "Петровна");
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(secondTrainingSession);

        List<TrainingSession> mondayTable = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        mondayTable.remove(1);

        assertEquals(1, mondayTable.size());
    }

    //Тесты для метода подсчёта занятий
    // Получение тренера с самым большим количеством занятий
    @Test
    void getCoachWithMaxSessions() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0));

        Coach coach2 = new Coach("Васильева", "Анна", "Петровна");
        TrainingSession thirdTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(thirdTrainingSession);
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        List<CounterOfTrainings> couchSessionsList = timetable.getCountByCoaches();

        assertEquals(coach, couchSessionsList.getFirst().getCoach());
        assertEquals(2, couchSessionsList.getFirst().getNumberOfTrainings());
    }

    // Получение тренера с самым малым количеством занятий
    @Test
    void getCoachWithMinSessions() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(11, 0));


        Coach coach2 = new Coach("Васильева", "Анна", "Петровна");
        TrainingSession thirdTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        Coach coach3 = new Coach("Терентьев", "Михаил", "Павлович");
        TrainingSession fourthTrainingSession = new TrainingSession(group, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(11, 0));
        TrainingSession fifthTrainingSession = new TrainingSession(group, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession sixTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thirdTrainingSession);
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(fourthTrainingSession);
        timetable.addNewTrainingSession(fifthTrainingSession);
        timetable.addNewTrainingSession(sixTrainingSession);

        List<CounterOfTrainings> couchSessionsList = timetable.getCountByCoaches();

        assertEquals(coach2, couchSessionsList.getLast().getCoach());
        assertEquals(1, couchSessionsList.getLast().getNumberOfTrainings());
    }

    // Получение пустого списка тренеров
    @Test
    void testListSessionsFromEmptyTimetable() {
        Timetable timetable = new Timetable();
        List<CounterOfTrainings> couchSessionsList = timetable.getCountByCoaches();
        assertTrue(couchSessionsList.isEmpty());
    }


}

