
/**
 *
 * @author B00337975 Ian Shaw
 */

public class Commercial extends Property {

    private final Integer area;

    public Commercial(String reference, String town, Integer rent, Integer area) {
        super(reference, town, rent);
        this.area = area;
    }

    @Override
    public String toString() {
        String propertyDetails = super.toString();
        propertyDetails += String.format("%-4s%1d%13s", "COMMERCIAL: There is ", area, " square feet of area.");
        return propertyDetails;
    }
}
