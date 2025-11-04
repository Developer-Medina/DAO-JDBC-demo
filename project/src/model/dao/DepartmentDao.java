package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department obj);
    void update(Integer id, String newName);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();

}
