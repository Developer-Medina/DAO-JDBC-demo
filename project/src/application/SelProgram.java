package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;
import java.util.Scanner;

public class SelProgram {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao(); //O programa não conhece a implementação,só a interface. É uma forma de injeção de dependência sem explicitar a implementação

        System.out.println("==== TEST 1: Seller findById ====");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println(" ");
        System.out.println("==== TEST 2: Department findById ===");
        Department department = new Department(2, null); //Instancia um novo department sem nome
        List<Seller> list = sellerDao.findByDepartment(department); //criamos uma lista de tipos Seller, e aplicamos nossa consulta com base no departamento. Os objetos ficarão guardados aqui após serem instanciados pelo metodo
        for (Seller obj : list) { //Exibicao
            System.out.println(obj);
        }

        System.out.println(" ");
        System.out.println("==== TEST 3: seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        /*

        System.out.println(" ");
        System.out.println("==== TEST 4: seller insert ===");
        Seller newSel = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSel); //Inserindo no BD
        System.out.println("Inserted! New ID = " + newSel.getId());

         */

        System.out.println(" ");
        System.out.println("==== TEST 5: seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update completed!");

        System.out.println(" ");
        System.out.println("==== TEST 6: seller delete ===");
        System.out.println("Enter ID for delete test: ");
        int id = sc.nextInt();
        sc.nextLine();
        sellerDao.deleteById(id);
        System.out.println("Deletion completed!");

        sc.close();


    }
}
