// CMSC215PROJ3KharchenkoV.java
// Name: Vadym Kharchenko
// Project: Trip Cost Estimator
// Date: 7/18/2024
//---------------------------------------------------------------------
// Description: A Swing application to estimate trip costs based on user 
// inputs for distance, gasoline cost, gas mileage, hotel cost, 
// food cost, number of days, and attractions.
//---------------------------------------------------------------------

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CMSC215PROJ3KharchenkoV {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TripCostEstimatorFrame frame = new TripCostEstimatorFrame();
            frame.setTitle("Trip Cost Estimator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setVisible(true);
        });
    }
}

class TripCostEstimatorFrame extends JFrame {

    private JTextField distanceField;
    private JComboBox<String> distanceUnitBox;
    private JTextField gasolineCostField;
    private JComboBox<String> gasolineCostUnitBox;
    private JTextField gasMileageField;
    private JComboBox<String> gasMileageUnitBox;
    private JTextField hotelCostField;
    private JTextField foodCostField;
    private JTextField numOfDaysField;
    private JTextField attractionsField;
    private JTextField totalCostField;
    private JButton calculateButton;

    public TripCostEstimatorFrame() {
        createComponents();
        setLayout(new GridBagLayout());
        addComponentsToPane();
    }

    private void createComponents() {
        distanceField = new JTextField(10);
        distanceUnitBox = new JComboBox<>(new String[] { "miles", "kilometers" });
        gasolineCostField = new JTextField(10);
        gasolineCostUnitBox = new JComboBox<>(new String[] { "dollars/gal", "dollars/lit" });
        gasMileageField = new JTextField(10);
        gasMileageUnitBox = new JComboBox<>(new String[] { "mil/gal", "kilometers/liter" });
        hotelCostField = new JTextField(10);
        foodCostField = new JTextField(10);
        numOfDaysField = new JTextField(10);
        attractionsField = new JTextField(10);
        totalCostField = new JTextField(10);
        totalCostField.setEditable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButListener());
    }

    private void addComponentsToPane() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Distance:"), gbc);

        gbc.gridx = 1;
        add(distanceField, gbc);

        gbc.gridx = 2;
        add(distanceUnitBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Gasoline Cost:"), gbc);

        gbc.gridx = 1;
        add(gasolineCostField, gbc);

        gbc.gridx = 2;
        add(gasolineCostUnitBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Gas Mileage:"), gbc);

        gbc.gridx = 1;
        add(gasMileageField, gbc);

        gbc.gridx = 2;
        add(gasMileageUnitBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Hotel Cost:"), gbc);

        gbc.gridx = 1;
        add(hotelCostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Food Cost:"), gbc);

        gbc.gridx = 1;
        add(foodCostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Number of Days:"), gbc);

        gbc.gridx = 1;
        add(numOfDaysField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Attractions:"), gbc);

        gbc.gridx = 1;
        add(attractionsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(calculateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Total Trip Cost:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(totalCostField, gbc);
    }

    private class CalculateButListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculateTripCost();
        }
    }

    private void calculateTripCost() {
        try {
            double distance = Double.parseDouble(distanceField.getText());
            double gasolineCost = Double.parseDouble(gasolineCostField.getText());
            double gasMileage = Double.parseDouble(gasMileageField.getText());
            double hotelCost = Double.parseDouble(hotelCostField.getText());
            double foodCost = Double.parseDouble(foodCostField.getText());
            int numOfDays = Integer.parseInt(numOfDaysField.getText());
            double attractions = Double.parseDouble(attractionsField.getText());

            if (distanceUnitBox.getSelectedItem().equals("Kilometers")) {
                distance *= 0.621371; // convert to miles
            }

            if (gasolineCostUnitBox.getSelectedItem().equals("Dollars per Liter")) {
                gasolineCost *= 3.78541; // convert to dollars per gallon
            }

            if (gasMileageUnitBox.getSelectedItem().equals("Kilometers per Liter")) {
                gasMileage *= 2.35215; // convert to miles per gallon
            }

            TripPost trip = new TripPost(distance, gasolineCost, gasMileage, hotelCost, foodCost, numOfDays,
                    attractions);
            double totalCost = trip.computeTotalTripCost();
            totalCostField.setText(String.format("$%.2f", totalCost));
        } catch (NumberFormatException e) {
            totalCostField.setText("Invalid input");
        }
    }
}

class TripPost {
    private final double distance;
    private final double gasolineCost;
    private final double gasMileage;
    private final double hotelCost;
    private final double foodCost;
    private final int numOfDays;
    private final double attractions;

    public TripPost(double distance, double gasolineCost, double gasMileage, double hotelCost, double foodCost,
            int numOfDays, double attractions) {
        this.distance = distance;
        this.gasolineCost = gasolineCost;
        this.gasMileage = gasMileage;
        this.hotelCost = hotelCost;
        this.foodCost = foodCost;
        this.numOfDays = numOfDays;
        this.attractions = attractions;
    }

    public double computeTotalTripCost() {
        double gasCost = (distance / gasMileage) * gasolineCost;
        double totalCost = gasCost + (hotelCost + foodCost) * numOfDays + attractions;
        return totalCost;
    }
}
