import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntervalTimeChecker {
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

class Time implements Comparable<Time> {
    private final int hours;
    private final int minutes;
    private final String meridian;

    public Time(int hours, int minutes, String meridian) {
        if (hours < 1 || hours > 12 || minutes < 0 || minutes >= 60
                || (!meridian.equals("AM") && !meridian.equals("PM"))) {
            throw new InvalidTime("Invalid time format");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.meridian = meridian;
    }

    public Time(String time) {
        String[] parts = time.split(" ");
        if (parts.length != 2)
            throw new InvalidTime("Invalid time format");
        String[] hm = parts[0].split(":");
        if (hm.length != 2)
            throw new InvalidTime("Invalid time format");

        int hours = Integer.parseInt(hm[0]);
        int minutes = Integer.parseInt(hm[1]);
        String meridian = parts[1];

        if (hours < 1 || hours > 12 || minutes < 0 || minutes >= 60
                || (!meridian.equals("AM") && !meridian.equals("PM"))) {
            throw new InvalidTime("Invalid time format");
        }

        this.hours = hours;
        this.minutes = minutes;
        this.meridian = meridian;
    }

    @Override
    public int compareTo(Time other) {
        if (this.meridian.equals(other.meridian)) {
            if (this.hours != other.hours)
                return this.hours - other.hours;
            return this.minutes - other.minutes;
        }
        return this.meridian.equals("AM") ? -1 : 1;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d %s", hours, minutes, meridian);
    }
}

class InvalidTime extends RuntimeException {
    public InvalidTime(String message) {
        super(message);
    }
}

class DataInput extends JFrame {
    private JTextField interval1StartField, interval1EndField;
    private JTextField interval2StartField, interval2EndField;
    private JTextField timeToCheckField;
    private JTextArea outputArea;

    public DataInput() {
        setTitle("Interval Time Checker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel gridPanel = new JPanel(new GridLayout(6, 3, 10, 10));

        // Labels for columns
        gridPanel.add(new JLabel(""));
        gridPanel.add(new JLabel("Start Time"));
        gridPanel.add(new JLabel("End Time"));

        // Interval 1 inputs
        gridPanel.add(new JLabel("Interval 1"));
        interval1StartField = new JTextField();
        gridPanel.add(interval1StartField);
        interval1EndField = new JTextField();
        gridPanel.add(interval1EndField);

        // Interval 2 inputs
        gridPanel.add(new JLabel("Interval 2"));
        interval2StartField = new JTextField();
        gridPanel.add(interval2StartField);
        interval2EndField = new JTextField();
        gridPanel.add(interval2EndField);

        // Compare intervals button
        gridPanel.add(new JLabel(""));
        JButton compareButton = new JButton("Compare Intervals");
        compareButton.addActionListener(new CompareIntervalsActionListener());
        gridPanel.add(compareButton);
        gridPanel.add(new JLabel(""));

        // Time to check input
        gridPanel.add(new JLabel("Time to Check:"));
        timeToCheckField = new JTextField();
        gridPanel.add(timeToCheckField);
        gridPanel.add(new JLabel(""));

        // Check time button
        gridPanel.add(new JLabel(""));
        JButton checkTimeButton = new JButton("Check Time");
        checkTimeButton.addActionListener(new CheckTimeActionListener());
        gridPanel.add(checkTimeButton);
        gridPanel.add(new JLabel(""));

        mainPanel.add(gridPanel, BorderLayout.CENTER);

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
                Time start1 = new Time(interval1StartField.getText());
                Time end1 = new Time(interval1EndField.getText());
                Time start2 = new Time(interval2StartField.getText());
                Time end2 = new Time(interval2EndField.getText());

                Interval<Time> interval1 = new Interval<>(start1, end1);
                Interval<Time> interval2 = new Interval<>(start2, end2);

                if (interval1.overlaps(interval2)) {
                    outputArea.append("\nThe intervals overlap");
                } else {
                    outputArea.append("Intervals are disjointed");
                }
            } catch (InvalidTime ex) {
                outputArea.append("Invalid time format: " + ex.getMessage() + "\n");
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid interval: " + ex.getMessage() + "\n");
            }
        }
    }

    private class CheckTimeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Time start1 = new Time(interval1StartField.getText());
                Time end1 = new Time(interval1EndField.getText());
                Time start2 = new Time(interval2StartField.getText());
                Time end2 = new Time(interval2EndField.getText());
                Time timeToCheck = new Time(timeToCheckField.getText());

                Interval<Time> interval1 = new Interval<>(start1, end1);
                Interval<Time> interval2 = new Interval<>(start2, end2);

                boolean inInterval1 = interval1.within(timeToCheck);
                boolean inInterval2 = interval2.within(timeToCheck);

                if (inInterval1 && inInterval2) {
                    outputArea.append("Both intervals contain the time " + timeToCheck + "\n");
                } else if (inInterval1) {
                    outputArea.append("Only Interval 1 contains the time " + timeToCheck + "\n");
                } else if (inInterval2) {
                    outputArea.append("Only Interval 2 contains the time " + timeToCheck + "\n");
                } else {
                    outputArea.append("Neither interval contains the time " + timeToCheck + "\n");
                }
            } catch (InvalidTime ex) {
                outputArea.append("Invalid time format: " + ex.getMessage() + "\n");
            }
        }
    }
}
