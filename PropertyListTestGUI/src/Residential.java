
/**
 *
 * @author B00337975 Ian Shaw
 */

public class Residential extends Property {

    private final Integer bedrooms;

    public Residential(String reference, String town, Integer rent, Integer bedrooms) {
        super(reference, town, rent);
        this.bedrooms = bedrooms;
    }

    @Override
    public String toString() {
        String propertyDetails = super.toString();
        propertyDetails += String.format("%-10s%1d%9s", "RESIDENTIAL: There are ", bedrooms, " bedrooms.");
        return propertyDetails;
    }

}
