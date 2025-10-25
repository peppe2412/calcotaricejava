import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int borderWidth = 360;
    int borderHeight = 540;

    Color displayWindow = new Color(41, 40, 40);
    Color topOperation = new Color(229, 226, 226);
    Color operationButtons = new Color(232, 204, 49);
    Color equalSignColor = new Color(232, 49, 49);
    Color textButtons = new Color(0, 0, 0);

    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };
    String[] rightSymbols = { "÷", "×", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    JFrame frame = new JFrame("Calcolatrice");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String numberA = "0";
    String operator = null;
    String numberB = null;

    void clear() {
        numberA = "0";
        operator = null;
        numberB = null;
    }

    String removeZeroDecimal(double numberDisplay) {
        if (numberDisplay % 1 == 0) {
            return Integer.toString((int) numberDisplay);
        } else {
            return Double.toString(numberDisplay);
        }
    }

    Calculator() {
        // frame.setVisible(true);
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(displayWindow);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 90));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(displayWindow);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 35));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(displayWindow));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(topOperation);
                button.setForeground(textButtons);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                if (buttonValue.equals("=")) {
                    button.setBackground(equalSignColor);
                    button.setForeground(Color.white);
                } else {
                    button.setBackground(operationButtons);
                    button.setForeground(textButtons);
                }
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (numberA != null){
                                numberB = displayLabel.getText();
                                double numA = Double.parseDouble(numberA);
                                double numB = Double.parseDouble(numberB);

                                switch (operator) {
                                    case "+":
                                        displayLabel.setText(removeZeroDecimal(numA + numB));
                                        break;
                                    case "-":
                                        displayLabel.setText(removeZeroDecimal(numA - numB));
                                        break;
                                    case "×":
                                        displayLabel.setText(removeZeroDecimal(numA * numB));
                                        break;
                                    case "÷":
                                        displayLabel.setText(removeZeroDecimal(numA / numB));
                                        break;
                                    default:
                                        break;
                                }
                                clear();
                            }
                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                numberA = displayLabel.getText();
                                displayLabel.setText("0");

                                numberB = "0";
                            }
                            operator = buttonValue;
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clear();
                            displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numberDisplay = Double.parseDouble(displayLabel.getText());
                            numberDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numberDisplay));
                        } else if (buttonValue == "%") {
                            double numberDisplay = Double.parseDouble(displayLabel.getText());
                            numberDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numberDisplay));
                        }
                    } else {
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }
}
