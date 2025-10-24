package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao(); //O programa não conhece a implementação,só a interface. É uma forma de injeção de dependência sem explicitar a implementação
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

    }
}
