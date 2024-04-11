package org.example.dao;

import org.example.model.Department;


public class DepartmentDaoImpl implements BaseDao<Department> {


    @Override
    public Class<Department> getEntityClass() {
        return Department.class;
    }
}
