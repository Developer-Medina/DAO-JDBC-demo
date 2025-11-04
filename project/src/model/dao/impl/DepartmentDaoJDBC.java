package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    //Constante de conexão

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO department (Name) "
                    + "VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, obj.getName()); //Atribuimos o nome

            int rowsAffected = ps.executeUpdate(); //Faz o push

            if(rowsAffected > 0) { //Checa se houveram linhas afetados
                ResultSet rs = ps.getGeneratedKeys(); //Pegamos as linhas afetadas

                if(rs.next()) { //Caso haja proxima linha (havera)
                    int id = rs.getInt(1);
                    obj.setID(id); //Atribuimos o ID que o banco mandou ao nosso objeto
                    System.out.printf("Done! Rows affefcted: " + rowsAffected);
                    System.out.println();
                    System.out.println("Object description: " + obj);
                }
            }




        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Integer id, String newName) {

        Department obj = findById(id); //Instanciamos um dep temporariamente para usar o metodo. Aqui ele já é instanciado...
        if(obj == null) {
            throw new DbException("Department not found!");
        } else {

            PreparedStatement ps = null;

            try {
                ps = connection.prepareStatement("UPDATE department SET Name = ? WHERE Id =?", ps.RETURN_GENERATED_KEYS);

                ps.setString(1, newName);
                ps.setInt(2, id);

                int rowsAffected = ps.executeUpdate(); //Fazemos o update, mas nao retorna nada
                obj = findById(id); //Só pra atualizarmos nossa instância, que é antiga ainda
                System.out.println(obj);

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeStatement(ps);
            }


        }


    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM department WHERE ID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Deletion completed! ID affected: " + id);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM department WHERE ID = ?");

            ps.setInt(1, id);
            rs = ps.executeQuery(); //Para o BD devolver uma tabela temporaria num ResultSet que possamos manipular para instanciar o Department

            if(rs.next()) {
                Department obj = instantiateDepartment(rs);
                return obj;
            }

            return null; //Porque caso ele nao complete o if acima, falha, retornando nulo

        } catch (SQLException e) {
            throw new  DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Department> findAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs = ps.executeQuery(); //Executamos e voltamos o resultado a um resultset

            List<Department> depList = new ArrayList<>();
            while(rs.next()) {
                Department dep =  instantiateDepartment(rs);
                depList.add(dep);
            }

            return depList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department obj = new Department();
        obj.setID(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));

        return obj;
    }

}
