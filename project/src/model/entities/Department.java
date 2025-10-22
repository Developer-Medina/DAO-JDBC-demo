package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {

    //Atributos e serialização

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String Name;


    //Construtores

    public Department() {}

    public Department(Integer ID, String name) {
        this.ID = ID;
        Name = name;
    }

    //Getters n Setters


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    //hashCode e Equals - somente por ID

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(getID(), that.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getID());
    }


    //toString

    @Override
    public String toString() {
        return "Department: "
                + getName()
                + " | ID: "
                + getID();
    }
}
