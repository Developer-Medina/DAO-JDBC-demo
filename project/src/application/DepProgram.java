package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class DepProgram {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao(); //Criamos a conexão
        Department dep1 = new Department(null, "Music"); //Criamos um departamento (sem ID, o banco deveria auto-gerar)

        /*
        System.out.println("===== TEST 1: INSERT =====");
        departmentDao.insert(dep1); //inserimos esse departamento no BD

        */
        System.out.println();
        System.out.println("===== TEST 2: DELETE =====");
        departmentDao.deleteById(6);

        System.out.println();
        System.out.println("===== TEST 3: FIND BY ID =====");
        System.out.println(departmentDao.findById(4));

        System.out.println();
        System.out.println("===== TEST 4: UPDATE =====");
        System.out.print("ID: ");
        int depId = sc.nextInt();
        sc.nextLine();
        System.out.print("New name: ");
        String depNewName = sc.next();
        departmentDao.update(depId, depNewName);

        System.out.println();
        System.out.println("===== TEST 5: FIND ALL =====");
        List<Department> list = departmentDao.findAll();
        for (Department obj : list) {
            System.out.println(obj);
        }






    }
}
