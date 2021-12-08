package tolls;

import java.util.Comparator;

// Implement Activity 3 here
/**
 * This comparator will compare TollRecord objects based on the exit number where they entered the Thruway, and then the
 * tag. TollRecords will be sorted alphabetically (ascending) by entry booth, and then alphabetically (ascending)
 * by tag name
 */
public class EntryComparator implements Comparator<TollRecord> {
    /**
     * Compare two TollRecord objects based on the exit number where they entered the Thruway (ascending) and then compare their tags (ascending).
     */
    @Override
    public int compare(TollRecord o1, TollRecord o2) {
        int boothCompare = o1.getEntry().compareTo(o2.getEntry());
        if (boothCompare == 0) {
            return o1.getTag().compareTo(o2.getTag());
        }

        return boothCompare;
    }
}
