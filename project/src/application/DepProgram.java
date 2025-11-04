package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepProgram {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao(); //Criamos a conexão
        Department dep1 = new Department(null, "Music"); //Criamos um departamento (sem ID, o banco deveria auto-gerar)

        /*
        System.out.println("===== TEST 1: INSERT =====");
        departmentDao.insert(dep1); //inserimos esse departamento no BD

        */

        departmentDao.deleteById(6);
    }
}
