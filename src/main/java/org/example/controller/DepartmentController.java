package org.example.controller;

import org.example.dao.DepartmentDaoImpl;
import org.example.model.Department;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DepartmentController extends JFrame {
    private DepartmentDaoImpl dao=new DepartmentDaoImpl();

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);
    private Integer taskSelection = null;

    public DepartmentController(){
        setTitle("Gestion des département");
        setSize(1200,800);
        setLocationRelativeTo(null);
        List<Department> departments = dao.getAll();

        for (Department department : departments) {
            model.addElement(department.toString());
        }
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        getContentPane().add(scrollPane, BorderLayout.NORTH);
        JPanel buttonPanel=new JPanel(new FlowLayout());


    }
}
