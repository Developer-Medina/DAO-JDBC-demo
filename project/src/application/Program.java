package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao(); //O programa não conhece a implementação,só a interface. É uma forma de injeção de dependência sem explicitar a implementação

        System.out.println("==== TEST 1: Seller findById ====");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println(" ");
        System.out.println("==== TEST 2: Department findById ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println(" ");
        System.out.println("==== TEST 3: seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println(" ");
        System.out.println("==== TEST 4: seller insert ===");
        Seller newSel = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSel); //Inserindo no BD
        System.out.println("Inserted! New ID = " + newSel.getId());

    }
}
