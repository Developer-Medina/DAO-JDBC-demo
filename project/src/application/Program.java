package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao(); //O programa não conhece a implementação,só a interface. É uma forma de injeção de dependência sem explicitar a implementação

        System.out.println("==== TEST 1: Seller findById ====");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println(" ");
        System.out.println("==== TEST 2: Department findById ===");
        Department department = new Department(2, null); // ???????? Por que
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

    }
}
