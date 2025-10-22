package application;

import model.entities.Department;

public class Program {
    public static void main(String[] args) {

        Department obj1 = new Department(1, "Books");
        Department obj2 = new Department(2, "Technology");

        System.out.println(obj1);
        System.out.println(obj2);

        System.out.println(obj1.equals(obj2));

    }
}
