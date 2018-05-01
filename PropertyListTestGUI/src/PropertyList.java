
/**
 *
 * @author Ian Shaw
 */

import java.io.*;

public class PropertyList implements Serializable {

    private Property[] propertyList;
    private Integer numberOfProperties;
    private final Integer MIN_RENT = 100;
    private final Integer MAX_RENT = 1000;

    public PropertyList(Integer propertyListSize) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("propertyList.ser"))) {
            this.propertyList = (Property[]) in.readObject();
            // rebuild numberofProperties
            numberOfProperties = 0;
            for (Integer i = 0; i < propertyList.length; i++) {
                if (this.propertyList[i] != null) {
                    this.numberOfProperties++;
                }
            }
        } catch (FileNotFoundException error) {
            this.propertyList = new Property[propertyListSize];
            this.numberOfProperties = 0;
        } catch (Exception error) {
            System.out.println(error);
        }
    }

    public void savePropertyList() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("propertyList.ser"))) {
            out.writeObject(this.propertyList);
        } catch (Exception error) {
        }
    }

    public void addCommercialProperty(String reference, String town, Integer rent, Integer area) throws arrayFullException, propertyReferenceNotUnique, rentNotWithinBounds {
        this.checkForErrors(reference, rent);
        this.propertyList[this.numberOfProperties] = new Commercial(reference, town, rent, area);
        this.numberOfProperties++;
        this.orderProperties();
    }

    public void addResidentialProperty(String reference, String town, Integer rent, Integer bedrooms) throws arrayFullException, propertyReferenceNotUnique, rentNotWithinBounds {
        this.checkForErrors(reference, rent);
        this.propertyList[this.numberOfProperties] = new Residential(reference, town, rent, bedrooms);
        this.numberOfProperties++;
        this.orderProperties();
    }

    public void checkForErrors(String reference, Integer rent) throws arrayFullException, propertyReferenceNotUnique, rentNotWithinBounds {
        if (this.numberOfProperties == this.propertyList.length) {
            throw new arrayFullException();
        }
        for (Integer i = 0; i < numberOfProperties; i++) {
            if (this.propertyList[i].getReference().equals(reference)) {
                throw new propertyReferenceNotUnique();
            }
        }
        if (rent < MIN_RENT || rent > MAX_RENT) {
            throw new rentNotWithinBounds();
        }
    }

    public class arrayFullException extends Exception {
    }

    public class propertyReferenceNotUnique extends Exception {
    }

    public class rentNotWithinBounds extends Exception {
    }

    public void orderProperties() {
        /*
    SORTING ALGORITHM
    suppose the list is sorted.
    for each pair of neighbours:
        compare the pair.
        if wrong order
            swap them
            assert that the list is not sorted
    end for
    continue until a full pass with no neighbour swaps, ie the list is sorted
         */
        Boolean listNotSorted;
        do {
            listNotSorted = false;
            for (Integer i = 1; i < this.numberOfProperties; i++) {
                Property checkProperty1 = this.propertyList[i - 1];
                Property checkProperty2 = this.propertyList[i];
                if (checkProperty1.compareTo(checkProperty2) > 0) {
                    this.propertyList[i - 1] = checkProperty2;
                    this.propertyList[i] = checkProperty1;
                    listNotSorted = true;
                }
            }
        } while (listNotSorted);
    }

    public void deleteProperty(String propertyReferenceToDelete) throws propertyReferenceNotFoundException {
        /*
    DELETION ALGORITHM
        suppose the property is not found
        for each member of the list
            if the property has already been found
                take this property and move it to the previous place in the array
                    (so previous (null) array element now refers to this object)
                point this property object's reference to null to 'delete' it
            otherwise
                compare the reference numbers
                if they are the same
                    property to delete is found
                    point this property object's reference to null to 'delete' it (garbage collection will get it for real)
            end if
        end for
        if the property was found
            record that there is now one less property (safe to do now the loop has completed)
        otherwise
            tell the user that reference was not found (throw error)
         */
        Boolean propertyFound = false;
        for (Integer i = 0; i < numberOfProperties; i++) {
            if (propertyFound) {
                propertyList[i - 1] = propertyList[i];
                propertyList[i] = null;
            } else {
                if (propertyList[i].getReference().equals(propertyReferenceToDelete)) {
                    propertyFound = true;
                    propertyList[i] = null;
                }
            }
        }
        if (propertyFound) {
            this.numberOfProperties--;
        } else {
            throw new propertyReferenceNotFoundException();
        }
    }

    public class propertyReferenceNotFoundException extends Exception {
    }

    @Override
    public String toString() {
        String propertyListDetails = new String();
        if (this.numberOfProperties != 0) {
            propertyListDetails += String.format("%-12s%-16s%-13s%-12s\n", "REFERENCE", "TOWN", "RENT", "TYPE");
            for (Integer i = 0; i < this.numberOfProperties; i++) {
                propertyListDetails += this.propertyList[i].toString() + "\n";
            }
        } else {
            propertyListDetails += "No properties on the list.";
        }
        return propertyListDetails;
    }

}
