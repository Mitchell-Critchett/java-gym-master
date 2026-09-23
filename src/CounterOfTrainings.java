public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;
    private int numberOfTrainings;

    CounterOfTrainings(Coach coach) {
        this.coach = coach;
        this.numberOfTrainings = 0;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }

    public void setNumberOfTrainings(int numberOfTrainings) {
        this.numberOfTrainings = numberOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return numberOfTrainings - o.numberOfTrainings;
    }
}
