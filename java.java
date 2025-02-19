package com.mycompany.mavenproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Mavenproject1 extends JFrame {
    private JTextField lowerBoundField, upperBoundField, stepField;
    private JTable table;
    private DefaultTableModel tableModel;

    public Mavenproject1() {
        setTitle("Вычисление интеграла");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        lowerBoundField = new JTextField();
        upperBoundField = new JTextField();
        stepField = new JTextField();

        inputPanel.add(new JLabel("Нижняя граница:"));
        inputPanel.add(new JLabel("Верхняя граница:"));
        inputPanel.add(new JLabel("Шаг:"));

        inputPanel.add(lowerBoundField);
        inputPanel.add(upperBoundField);
        inputPanel.add(stepField);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Нижняя граница", "Верхняя граница", "Шаг", "Результат"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Добавить");
        JButton removeButton = new JButton("Удалить");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addRow());
        removeButton.addActionListener(e -> removeRow());

        setVisible(true);
    }

    private void addRow() {
        try {
            double lower = Double.parseDouble(lowerBoundField.getText());
            double upper = Double.parseDouble(upperBoundField.getText());
            double step = Double.parseDouble(stepField.getText());

            if (lower >= upper || step <= 0) {
                JOptionPane.showMessageDialog(this, "Некорректные значения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double result = integrate(lower, upper, step);

            tableModel.addRow(new Object[]{lower, upper, step, result});
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите числовые значения!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Выберите строку для удаления!", "Ошибка", JOptionPane.WARNING_MESSAGE);
        }
    }

    private double integrate(double lower, double upper, double step) {
        double sum = 0.0;
        for (double x = lower; x < upper; x += step) {
            double newX = Math.min(x + step, upper);
            double f1 = function(x);
            double f2 = function(newX);
            sum += (f1 + f2) * (newX - x) / 2.0;
        }
        return sum;
    }

    private double function(double x) {
        return 1.0 / Math.log(x);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mavenproject1::new);
    }
}