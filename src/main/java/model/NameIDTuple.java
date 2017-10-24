package model;

/*** Immutable object that encapsulates a Name/ID into an immutable tuple ***/
public class NameIDTuple {
    // Instance variables are immutable thus public
    public final int id;
    public final String name;
    
    public NameIDTuple(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj != null && obj.getClass().isAssignableFrom(this.getClass())) {
            NameIDTuple otherRecord = (NameIDTuple) obj;
            result = otherRecord.id == this.id 
                    && otherRecord.name.equals(this.name);
                    
        }
        return result;
    }
    
}
