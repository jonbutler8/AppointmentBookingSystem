package model;

/*** Immutable object that encapsulates information about an employee ***/
public class EmployeeRecord extends NameIDTuple {
    // Instance variables are immutable thus public
    public final String lastName;
    public final String firstName;
    public final int businessID;

    public EmployeeRecord(int id, String firstName, String lastName, int
      businessID) {
        super(id, firstName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.businessID = businessID;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj != null && obj.getClass().isAssignableFrom(this.getClass())) {
            EmployeeRecord otherRecord = (EmployeeRecord) obj;
            result = otherRecord.id == this.id
                    && otherRecord.name.equals(this.name)
                    && otherRecord.lastName.equals(this.lastName);

        }
        return result;
    }

}
