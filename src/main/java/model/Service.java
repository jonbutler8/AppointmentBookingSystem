package model;

public class Service implements Comparable<Service> {
    private final int id;
    private final String name;

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String toString() {
        return name;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public int compareTo(Service service) {
        return name.compareTo(service.name);
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj != null && obj.getClass().isAssignableFrom(this.getClass())) {
            Service otherService = (Service) obj;
            result = otherService.id == this.id;

        }
        return result;
    }
}
