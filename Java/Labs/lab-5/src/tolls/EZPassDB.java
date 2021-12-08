package tolls;

import java.util.*;

/**
 * The computer end of the EZ pass system.
 * <p>
 * Every time a car passes by a scanner at a Thruway Exit (either for
 * entry or exit) the scanner generates a string that contains the
 * vehicle's tag and the Exit the vehicle is at.  This information is
 * passed to the log() method that records the event.
 * </p>
 * <p>
 * The DB maintains two collections.  The first collection holds the
 * TollRecords for the trips that have not yet been completed (i.e.
 * the vehicle is still on the Thruway).  The second collection holds
 * the TollRecords for all completed trips.  Note that a vehicle can
 * only appear once in the first collection and may appear several times
 * in the second collection.
 * </p>
 *
 * @author RIT CS
 * @author Thomas Schollenberger
 */

public class EZPassDB implements TollDB {
    private HashSet<String> inProgressTripTags;
    private HashSet<String> finishedTripTags;
    private ArrayList<TollRecord> inProgressTrips;
    private ArrayList<TollRecord> finishedTrips;

    /**
     * Create a new EZ pass database.
     */
    public EZPassDB() {
        this.inProgressTrips = new ArrayList<TollRecord>();
        this.finishedTrips = new ArrayList<TollRecord>();

        this.inProgressTripTags = new HashSet<String>();
        this.finishedTripTags = new HashSet<String>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String tag, String exit) {
        if (!TollSchedule.isValid(exit))
            return;
        if (this.inProgressTripTags.contains(tag)) {
            for (TollRecord record : this.inProgressTrips) {
                if (record.getTag().equals(tag)) {
                    this.inProgressTrips.remove(record);
                    this.inProgressTripTags.remove(record.getTag());
                    record.setExit(exit);
                    this.finishedTrips.add(record);
                    this.finishedTripTags.add(record.getTag());
                    System.out.println(String.format("LOG: Exiting: Vehicle ID: %s, Entry=%s, Exit=%s", tag, record.getEntry(), exit));
                    break;
                }
            }
        } else {
            TollRecord record = new TollRecord(tag);
            record.setEntry(exit);
            this.inProgressTripTags.add(tag);
            this.inProgressTrips.add(record);
            System.out.println(String.format("LOG: Entering: Vehicle ID: %s, Entry=%s, Exit=UNKNOWN", tag, exit));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TollRecord> openTrips() {
        return this.inProgressTrips;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TollRecord> charges() {
        return this.finishedTrips;
    }
}
