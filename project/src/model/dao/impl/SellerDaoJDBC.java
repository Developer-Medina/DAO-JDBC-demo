package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    //Dependencia da Conexao. O objeto de conexao estara a nossa disposicao aqui dentro
    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS); //Para sabermos os ids

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); //instancia uma dara em sql
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getID());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys(); //Mostra os ids
                if (rs.next()) {
                    int id = rs.getInt(1); //Pegamos o ID gerado
                    obj.setId(id); //Atribuimos o ID do objeto ao objeto Java
                }

                DB.closeResultSet(rs);

            } else {
                throw new DbException("Erro ao inserir seller");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller obj) {

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); //instancia uma data em sql
            ps.setDouble(4, obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getID());
            ps.setInt(6, obj.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement ps = null; //Nosso comando SQL
        ResultSet rs = null; //Nosso resultado do BD

        try {
            ps = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?" //Sem espaçuxo
            );

            ps.setInt(1, id);
            rs = ps.executeQuery(); //O resultset recebe nossa query, mas ele aponta para posição 0, que não tem nosso objeto (ele só aparece na opção 1)

            if(rs.next()) { //Checa se houve resultado
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }

            return null; //Porque if falha

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
            //Não fechamos conexão aqui
        }

    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {

        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);

        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException { //Não trataremos exceção, a propagaremos
        Department dep = new Department();
        dep.setID(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;

    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null; //Nosso comando SQL
        ResultSet rs = null; //Nosso resultado do BD

        try {
            ps = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            rs = ps.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>(); //Para controlarmos que, novamente, não teremos múltiplas instâncias de um mesmo dep

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement ps = null; //Nosso comando SQL
        ResultSet rs = null; //Nosso resultado do BD

        try {
            ps = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name"
            );

            ps.setInt(1, department.getID());
            rs = ps.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>(); //Para controlarmos que dep não terá múltiplas instâncias

            //O resultado pode ter 0 ou mais valores, por isso usaremos while

            while (rs.next()) {

                //Checando se dep já existe
                Department dep = map.get(rs.getInt("DepartmentId")); //Id de Dep que aparece em resultset. Se for nulo, ai sim instanciamos um depto

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep); //guardamos, na chave tal, o dep no map
                }

                Seller obj = instantiateSeller(rs, dep); //É o mesmo dep
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }

}
