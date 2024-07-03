package org.example.controller;

import org.example.dao.DepartmentDaoImpl;
import org.example.model.Department;
import org.example.view.DepartmentUi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DepartmentController extends JFrame {
    private final DepartmentDaoImpl dao = new DepartmentDaoImpl();

    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);
    private Integer departmentSelection = null;

    private final DepartmentUi ui = new DepartmentUi(this);

    public DepartmentController() {
        setTitle("Gestion des département");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Department> departments = dao.getAll();
        for (Department department : departments) {
            model.addElement(department.toString());
        }

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                departmentSelection = list.getSelectedIndex();
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        getContentPane().add(scrollPane, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        JButton switchButton = new JButton("Employé");

        addButton.addActionListener(e -> {
            ui.addView();
            refreshList();
        });

        editButton.addActionListener(e -> {
            if (departmentSelection != null) {
                Department department = departments.get(departmentSelection);
                ui.editView(department);
                refreshList();
            }
        });

        deleteButton.addActionListener(e -> {
            if (departmentSelection != null) {
                Department department = departments.get(departmentSelection);
                removeDepartment(department);
            }
        });

        switchButton.addActionListener(e -> switchFrame());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(switchButton);


        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void switchFrame() {
        dispose();
        new SalaryController();
    }

    private void refreshList() {
        model.clear();
        List<Department> updatedDepartments = dao.getAll();
        for (Department department : updatedDepartments) {
            model.addElement(department.toString());
        }

    }

    private void removeDepartment(Department department) {
        model.removeElement(department.toString());
        dao.remove(department.getId());
    }
}
