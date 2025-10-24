package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.util.Date;

public class Program {
    public static void main(String[] args) {

        Department obj1 = new Department(1, "Books");
        Seller seller = new Seller(21, "Bob", "bob@email.com", new Date(), 3000.0, obj1);

        System.out.println(seller);

        SellerDao sellerDao = DaoFactory.createSellerDao(); //O programa não conhece a implementação,só a interface. É uma forma de injeção de dependência sem explicitar a implementação

    }
}
