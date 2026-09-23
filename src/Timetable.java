import java.util.*;
import java.util.Comparator;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private Comparator<TimeOfDay> timeOfDayComparator = new Comparator<>() {
        @Override
        public int compare(TimeOfDay o1, TimeOfDay o2) {
            return (o1.getMinutes() + (o1.getHours() * 60)) - (o2.getMinutes() + (o2.getHours() * 60));
        }
    };
    private Map<Coach, CounterOfTrainings> counterTrainingsMap = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.getOrDefault(trainingSession.getDayOfWeek(),
                new TreeMap<>(timeOfDayComparator));
        List<TrainingSession> sessions = dayTable.getOrDefault(trainingSession.getTimeOfDay(), new ArrayList<TrainingSession>());
        sessions.add(trainingSession);
        dayTable.put(trainingSession.getTimeOfDay(), sessions);
        timetable.put(trainingSession.getDayOfWeek(), dayTable);

        CounterOfTrainings counter = counterTrainingsMap.getOrDefault(trainingSession.getCoach(),
                new CounterOfTrainings(trainingSession.getCoach()));
        counter.setNumberOfTrainings(counter.getNumberOfTrainings() + 1);
        counterTrainingsMap.put(trainingSession.getCoach(), counter);


    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.get(dayOfWeek);

        if (dayTable != null) return dayTable;
        System.out.println("Тренировок на этот день недели нет");

        return new TreeMap<TimeOfDay, List<TrainingSession>>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> dayTable = timetable.get(dayOfWeek);

        if (dayTable != null) {
            List<TrainingSession> sessions = dayTable.get(timeOfDay);
            if (sessions != null) {
                return sessions;
            }
            System.out.println("Тренировок на это время нет");
            return new ArrayList<TrainingSession>();
        }

        System.out.println("Тренировок на этот день недели нет");
        return new ArrayList<TrainingSession>();
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        List<CounterOfTrainings> couchSessionsList = new ArrayList<>(counterTrainingsMap.values());
        couchSessionsList.sort(Comparator.reverseOrder());

        return couchSessionsList;
    }


}

