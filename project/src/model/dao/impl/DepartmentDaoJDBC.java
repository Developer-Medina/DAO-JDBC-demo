package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.List;

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
    public void update(Department obj) {

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
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }

}
