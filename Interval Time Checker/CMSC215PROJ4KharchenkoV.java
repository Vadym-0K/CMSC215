// CMSC215PROJ4KharchenkoV.java
// Name: Vadym Kharchenko
// Project: Interval Time Checker 
// Date: 8/4/2024
//---------------------------------------------------------------------
// The Interval Checker is a Java program designed to help users compare 
// time intervals and check if a specific time falls within those intervals. 
// It features a user-friendly graphical interface with fields for entering 
// start and end times for two different intervals. Users can compare these 
// intervals to see if they overlap or are disjointed. Additionally, users can 
// input a specific time to check whether it falls within either or both intervals.
//---------------------------------------------------------------------

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CMSC215PROJ4KharchenkoV {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DataInput().setVisible(true));
    }
}

class Interval<T extends Comparable<T>> {
    private final T start;
    private final T end;

    public Interval(T start, T end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start must be less than or equal to end");
        }
        this.start = start;
        this.end = end;
    }

    public boolean within(T value) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public boolean subinterval(Interval<T> other) {
        return this.start.compareTo(other.start) <= 0 && this.end.compareTo(other.end) >= 0;
    }

    public boolean overlaps(Interval<T> other) {
        return this.start.compareTo(other.end) <= 0 && this.end.compareTo(other.start) >= 0;
    }
}

// Time class
class Time implements Comparable<Time> {
    private final int hours;
    private final int minutes;
    private final String meridian;

    public Time(int hours, int minutes, String meridian) throws InvalidTime {
        if (hours < 1 || hours > 12 || minutes < 0 || minutes >= 60
                || (!meridian.equals("AM") && !meridian.equals("PM"))) {
            throw new InvalidTime("Invalid time format");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.meridian = meridian;
    }

    public Time(String time) throws InvalidTime {
        String[] parts = time.split(" ");
        if (parts.length != 2) {
            throw new InvalidTime("Invalid time format");
        }

        String[] hm = parts[0].split(":");
        if (hm.length != 2) {
            throw new InvalidTime("Invalid time format");
        }

        try {
            this.hours = Integer.parseInt(hm[0]);
            this.minutes = Integer.parseInt(hm[1]);
            this.meridian = parts[1];
        } catch (NumberFormatException e) {
            throw new InvalidTime("Hours and minutes must be numeric");
        }

        if (hours < 1 || hours > 12 || minutes < 0 || minutes >= 60
                || (!meridian.equals("AM") && !meridian.equals("PM"))) {
            throw new InvalidTime("Invalid time format");
        }
    }

    @Override
    public int compareTo(Time other) {
        int this24Hour = convertTo24HourFormat(this.hours, this.meridian);
        int other24Hour = convertTo24HourFormat(other.hours, other.meridian);

        if (this24Hour != other24Hour) {
            return Integer.compare(this24Hour, other24Hour);
        }
        return Integer.compare(this.minutes, other.minutes);
    }

    private int convertTo24HourFormat(int hours, String meridian) {
        if (meridian.equals("AM")) {
            return hours == 12 ? 0 : hours;
        } else {
            return hours == 12 ? 12 : hours + 12;
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d %s", hours, minutes, meridian);
    }
}

// InvalidTime exception class
class InvalidTime extends Exception {
    public InvalidTime(String message) {
        super(message);
    }
}

class DataInput extends JFrame {
    private JTextField int1StartField, int1EndField;
    private JTextField int2StartField, int2EndField;
    private JTextField timeCheckField;
    private JTextArea outputArea;

    public DataInput() {
        setTitle("Interval Time Checker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 70));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel matrixPanel = new JPanel(new GridLayout(6, 3, 10, 10));

        // Labels for columns
        matrixPanel.add(new JLabel(""));
        matrixPanel.add(new JLabel("Start Time"));
        matrixPanel.add(new JLabel("End Time"));

        // Interval 1 inputs
        matrixPanel.add(new JLabel("Interval 1"));
        int1StartField = new JTextField();
        matrixPanel.add(int1StartField);
        int1EndField = new JTextField();
        matrixPanel.add(int1EndField);

        // Interval 2 inputs
        matrixPanel.add(new JLabel("Interval 2"));
        int2StartField = new JTextField();
        matrixPanel.add(int2StartField);
        int2EndField = new JTextField();
        matrixPanel.add(int2EndField);

        // Compare intervals button
        matrixPanel.add(new JLabel(""));
        JButton compareButton = new JButton("Compare Intervals");
        compareButton.addActionListener(new CompareIntervalsActionListener());
        matrixPanel.add(compareButton);
        matrixPanel.add(new JLabel(""));

        // Time to check input
        matrixPanel.add(new JLabel("Time to Check:"));
        timeCheckField = new JTextField();
        matrixPanel.add(timeCheckField);
        matrixPanel.add(new JLabel(""));

        // Check time button
        matrixPanel.add(new JLabel(""));
        JButton checkTimeButton = new JButton("Check Time");
        checkTimeButton.addActionListener(new CheckTimeActionListener());
        matrixPanel.add(checkTimeButton);
        matrixPanel.add(new JLabel(""));

        mainPanel.add(matrixPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class CompareIntervalsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Time start1 = new Time(int1StartField.getText());
                Time end1 = new Time(int1EndField.getText());
                Time start2 = new Time(int2StartField.getText());
                Time end2 = new Time(int2EndField.getText());

                Interval<Time> interval1 = new Interval<>(start1, end1);
                Interval<Time> interval2 = new Interval<>(start2, end2);

                if (interval1.overlaps(interval2)) {
                    outputArea.append("\nThe intervals overlap");
                } else {
                    outputArea.append("\nIntervals are disjointed");
                }
            } catch (InvalidTime ex) {
                outputArea.append("\nInvalid time format: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                outputArea.append("\nInvalid interval: " + ex.getMessage());
            }
        }
    }

    private class CheckTimeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Time start1 = new Time(int1StartField.getText());
                Time end1 = new Time(int1EndField.getText());
                Time start2 = new Time(int2StartField.getText());
                Time end2 = new Time(int2EndField.getText());
                Time timeToCheck = new Time(timeCheckField.getText());

                Interval<Time> interval1 = new Interval<>(start1, end1);
                Interval<Time> interval2 = new Interval<>(start2, end2);

                boolean inInterval1 = interval1.within(timeToCheck);
                boolean inInterval2 = interval2.within(timeToCheck);

                if (inInterval1 && inInterval2) {
                    outputArea.append("\nBoth intervals contain the time " + timeToCheck);
                } else if (inInterval1) {
                    outputArea.append("\nOnly Interval 1 contains the time " + timeCheckField);
                } else if (inInterval2) {
                    outputArea.append("\nOnly Interval 2 contains the time " + timeToCheck);
                } else {
                    outputArea.append("\nNeither interval contains the time " + timeToCheck);
                }
            } catch (InvalidTime ex) {
                outputArea.append("Invalid time format: " + ex.getMessage());
            }
        }
    }
}
