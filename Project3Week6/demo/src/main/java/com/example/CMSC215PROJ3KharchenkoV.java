package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CMSC215PROJ3KharchenkoV extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        InputData inputData = new InputData();
        primaryStage.setTitle("Trip Cost Estimator");
        primaryStage.setScene(new Scene(inputData.createInputGrid(), 400, 300));
        primaryStage.show();
    }
}

class InputData {
    private TextField distanceField = new TextField();
    private ComboBox<String> distanceUnitBox = new ComboBox<>();
    private TextField gasolineCostField = new TextField();
    private ComboBox<String> gasolineCostUnitBox = new ComboBox<>();
    private TextField gasMileageField = new TextField();
    private ComboBox<String> gasMileageUnitBox = new ComboBox<>();
    private TextField hotelCostField = new TextField();
    private TextField foodCostField = new TextField();
    private TextField numOfDaysField = new TextField();
    private TextField attractionsField = new TextField();
    private TextField totalCostField = new TextField();

    public GridPane createInputGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label distanceLabel = new Label("Distance:");
        distanceUnitBox.getItems().addAll("Miles", "Kilometers");
        distanceUnitBox.setValue("Miles");

        Label gasolineCostLabel = new Label("Gasoline Cost:");
        gasolineCostUnitBox.getItems().addAll("Dollars per Gallon", "Dollars per Liter");
        gasolineCostUnitBox.setValue("Dollars per Gallon");

        Label gasMileageLabel = new Label("Gas Mileage:");
        gasMileageUnitBox.getItems().addAll("Miles per Gallon", "Kilometers per Liter");
        gasMileageUnitBox.setValue("Miles per Gallon");

        Label hotelCostLabel = new Label("Hotel Cost per Night:");
        Label foodCostLabel = new Label("Food Cost per Day:");
        Label numOfDaysLabel = new Label("Number of Days:");
        Label attractionsLabel = new Label("Attractions Cost:");

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateTripCost());

        totalCostField.setEditable(false);

        grid.add(distanceLabel, 0, 0);
        grid.add(distanceField, 1, 0);
        grid.add(distanceUnitBox, 2, 0);
        grid.add(gasolineCostLabel, 0, 1);
        grid.add(gasolineCostField, 1, 1);
        grid.add(gasolineCostUnitBox, 2, 1);
        grid.add(gasMileageLabel, 0, 2);
        grid.add(gasMileageField, 1, 2);
        grid.add(gasMileageUnitBox, 2, 2);
        grid.add(hotelCostLabel, 0, 3);
        grid.add(hotelCostField, 1, 3);
        grid.add(foodCostLabel, 0, 4);
        grid.add(foodCostField, 1, 4);
        grid.add(numOfDaysLabel, 0, 5);
        grid.add(numOfDaysField, 1, 5);
        grid.add(attractionsLabel, 0, 6);
        grid.add(attractionsField, 1, 6);
        grid.add(calculateButton, 0, 7);
        grid.add(totalCostField, 1, 7, 2, 1);

        return grid;
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

            if (distanceUnitBox.getValue().equals("Kilometers")) {
                distance *= 0.621371; // convert to miles
            }

            if (gasolineCostUnitBox.getValue().equals("Dollars per Liter")) {
                gasolineCost *= 3.78541; // convert to dollars per gallon
            }

            if (gasMileageUnitBox.getValue().equals("Kilometers per Liter")) {
                gasMileage *= 2.35215; // convert to miles per gallon
            }

            TripPost trip = new TripPost(distance, gasolineCost, gasMileage, hotelCost, foodCost, numOfDays,
                    attractions);
            double totalCost = trip.computeTotalTripCost();
            totalCostField.setText(String.format("%.2f", totalCost));
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