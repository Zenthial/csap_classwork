package tolls;

import java.util.List;

/**
 * A simple interface to a database of toll records.
 *
 * @author RIT CS
 */
public interface TollDB {
    /**
     * This method is called whenever a scanner detects that a vehicle
     * has entered or has left the Thruway.  Note that the parameters
     * do not indicate if the vehicle is entering or leaving the Thruway,
     * it is up to the database to determine which event has occurred.
     * If either parameter is invalid, the method will terminate without
     * changing the state of this object.
     *
     * @param tag  the tag of the vehicle that has passed the scanner.
     * @param exit the Exit at which the scanner is located
     */
    void log(String tag, String exit);

    /**
     * Returns a list of open trips (vehicles that have entered the
     * Thruway but have not yet exited).  The List contains the
     * TollRecords of the vehicles currently on the Thruway sorted in
     * ascending order by the Exit number where they entered the Thruway,
     * and alphabetically by tag if they have the same Exit number.
     *
     * @return a list containing all vehicles currently on the Thruway sorted
     * in ascending order by entry Exit number.
     */
    List<TollRecord> openTrips();

    /**
     * Return a list of all completed trips.  The list will contain the
     * TollRecords of the people who have completed trips on the
     * Thruway.  The list will be sorted in ascending order by vehicle
     * tag and then, if there are multiple entries for the same tag, they will be sorted by the Exit number where the vehicle
     * entered the Thruway.
     *
     * @return a list containing the completed trips sorted in ascending
     * order by tag and entry Exit number.
     */
    List<TollRecord> charges();
}
