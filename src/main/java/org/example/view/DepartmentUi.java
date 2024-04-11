package org.example.view;

import org.example.dao.DepartmentDaoImpl;
import org.example.model.Department;

import javax.swing.*;
import java.awt.*;

public class DepartmentUi extends JDialog {

    private final DepartmentDaoImpl dao=new DepartmentDaoImpl();
    private JTextField nameInput;

    public DepartmentUi(JFrame frame){
        setLocationRelativeTo(frame);
    }
    public void addView(){
        JPanel panel=new JPanel(new GridLayout(0, 1));
        setSize(200,250);
        JLabel nameLabel = new JLabel("Nom:");
        panel.add(nameLabel);
        nameInput=new JTextField(20);
        panel.add(nameInput);
        JButton okButton=new JButton("Ajouter");
        okButton.addActionListener(e->addDepartment());
        panel.add(okButton);
        add(panel);
        pack();
        setModal(true);
        setVisible(true);
    }

    private void addDepartment(){
        String name=nameInput.getText();
        if(!name.isEmpty()){
            Department department=Department.builder().name(name).build();
            dao.create(department);
            nameInput.setText("");
            dispose();
        }
    }

    public void editView(Department department){
        JPanel panel=new JPanel(new GridLayout(0, 1));
        setSize(200,250);
        JLabel nameLabel = new JLabel("Nom:");
        panel.add(nameLabel);
        nameInput=new JTextField(20);
        nameInput.setText(department.getName());
        panel.add(nameInput);
        JButton okButton=new JButton("Modifié");
        okButton.addActionListener(e->{
            Department newDepartment=editDepartment(department);
            if(newDepartment!=null){
                dispose();
            }
        });
        panel.add(okButton);
        add(panel);
        pack();
        setModal(true);
        setVisible(true);
    }

    private Department editDepartment(Department department){
        if(!nameInput.getText().isEmpty()){
            department.setName(nameInput.getText());
            dao.edit(department);
            nameInput.setText("");
            return department;
        }
        return null;
    }
}
