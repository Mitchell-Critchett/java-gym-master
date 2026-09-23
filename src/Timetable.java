import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();
    private Comparator<TimeOfDay> timeOfDayComparator = new Comparator<>() {
        @Override
        public int compare(TimeOfDay o1, TimeOfDay o2) {
            return (o1.getMinutes() + (o1.getHours() * 60)) - (o2.getMinutes() + (o2.getHours() * 60));
        }
    };

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, TrainingSession> dayTable = timetable.getOrDefault(trainingSession.getDayOfWeek(), new TreeMap<>(timeOfDayComparator));
        dayTable.put(trainingSession.getTimeOfDay(), trainingSession);
    }

    public TreeMap<TimeOfDay, TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> dayTable = timetable.get(dayOfWeek);
        if (dayTable != null) return dayTable;
        System.out.println("Тренировок на этот день недели нет");
        return null;
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> dayTable = timetable.get(dayOfWeek);
        if (dayTable != null) {
            TrainingSession session = dayTable.get(timeOfDay);
            if (session != null) {
                return session;
            }
            System.out.println("Тренировок на это время нет");
            return null;
        }
        System.out.println("Тренировок на этот день недели нет");
        return null;
    }

}

