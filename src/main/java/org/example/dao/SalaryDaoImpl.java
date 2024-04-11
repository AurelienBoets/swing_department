package org.example.dao;

import org.example.model.Department;
import org.example.model.Role;
import org.example.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDaoImpl implements BaseDao<Salary> {

    @Override
    public Class<Salary> getEntityClass() {
        return Salary.class;
    }
}
