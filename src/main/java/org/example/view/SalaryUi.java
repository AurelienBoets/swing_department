package org.example.view;

import org.example.dao.DepartmentDaoImpl;
import org.example.dao.SalaryDaoImpl;
import org.example.model.Department;
import org.example.model.Role;
import org.example.model.Salary;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalaryUi extends JDialog {


    private final SalaryDaoImpl salaryDao=new SalaryDaoImpl();

    private List<Department> departments;
    private final DepartmentDaoImpl departmentDao=new DepartmentDaoImpl();
    private final JTextField firstnameInput=new JTextField(15);
    private final JTextField lastnameInput=new JTextField(15);
    private final JComboBox<String> roleInput=new JComboBox<>();

    private final ButtonGroup departmentInput=new ButtonGroup();
    private final JLabel firstnameLabel=new JLabel("Prénom:");
    private final JLabel lastnameLabel=new JLabel("Nom:");
    private final JLabel roleLabel=new JLabel("Rôle");
    private final JLabel departmentLabel=new JLabel("departmentLabel");

    public SalaryUi(JFrame frame){
        setLocationRelativeTo(frame);
        setSize(350,250);
        departments=departmentDao.getAll();
    }

    public void addView(){
        getContentPane().removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setTitle("Ajouté salarié");

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(firstnameLabel, constraints);

        constraints.gridx++;
        add(firstnameInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(lastnameLabel, constraints);

        constraints.gridx++;
        add(lastnameInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(roleLabel, constraints);

        constraints.gridx++;
        for(Role role:Role.values()){
            roleInput.addItem(role.toString());
        }
        add(roleInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(departmentLabel, constraints);

        constraints.gridx++;
        JPanel departmentPanel = new JPanel(new FlowLayout());

        for(Department department : departments){
            JRadioButton radioButton = new JRadioButton(department.getName());
            radioButton.setActionCommand(String.valueOf(department.getId()));
            departmentInput.add(radioButton);
            departmentPanel.add(radioButton);
        }
        add(departmentPanel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JButton button=new JButton("Valider");
        button.addActionListener(e->addSalary());
        add(button, constraints);

        setModal(true);
        setVisible(true);
    }


    private void addSalary(){
        String firstname=firstnameInput.getText();
        String lastname=lastnameInput.getText();
        Role role=Role.valueOf((String) roleInput.getSelectedItem());
        Department department=departmentDao.getById(Long.parseLong(departmentInput.getSelection().getActionCommand()));
        if(!firstname.isEmpty() && !lastname.isEmpty() && department!=null){
            Salary salary=Salary.builder().
                    firstname(firstname).
                    lastname(lastname).
                    role(role).
                    department(department).
                    build();

            salaryDao.create(salary);
            firstnameInput.setText("");
            lastnameInput.setText("");
            dispose();
        }
    }

    public void editView(long idSalary){
        Salary salary=salaryDao.getById(idSalary);
        getContentPane().removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setTitle("Modifié salarié");

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(firstnameLabel, constraints);

        constraints.gridx++;
        firstnameInput.setText(salary.getFirstname());
        add(firstnameInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(lastnameLabel, constraints);

        constraints.gridx++;
        lastnameInput.setText(salary.getLastname());
        add(lastnameInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(roleLabel, constraints);

        constraints.gridx++;
        for(Role role:Role.values()){
            roleInput.addItem(role.toString());
        }
        roleInput.setSelectedItem(salary.getRole().toString());
        add(roleInput, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(departmentLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JButton button=new JButton("Valider");
        button.addActionListener(e->editSalary(salary));
        add(button, constraints);

        setModal(true);
        setVisible(true);
    }

    private void editSalary(Salary salary){
        String firstname=firstnameInput.getText();
        String lastname=lastnameInput.getText();
        Role role=Role.valueOf((String) roleInput.getSelectedItem());
        if(!firstname.isEmpty()&&!lastname.isEmpty()){
            salary.setFirstname(firstname);
            salary.setLastname(lastname);
            salary.setRole(role);
            salaryDao.edit(salary);
            dispose();
        }
    }
}
