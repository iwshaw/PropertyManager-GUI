
/**
 *
 * @author Ian Shaw
 */

import java.io.Serializable;

public class Property implements Comparable<Property>, Serializable {

    private final String reference;
    private final String town;
    private final Integer rent;

    public Property(String reference, String town, Integer rent) {
        this.reference = reference;
        this.town = town;
        this.rent = rent;
    }

    public String getReference() {
        return this.reference;
    }

    @Override
    public int compareTo(Property property) {
        Integer result = this.town.compareTo(property.town);
        if (result == 0) // towns the same, compare rent
        {
            result = this.rent.compareTo(property.rent);
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("%-12s%-16s%-13s", reference, town, rent);
    }

}
