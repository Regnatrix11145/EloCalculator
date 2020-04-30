package com.regnatrix.eloCalculator.eloCalculatorFrame;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * @author Regnatrix
 * Copyright (c) 2019 - 2020 by infrasion.de to present. All rights reserved.
 */
public class EloCalculatorFrame extends JFrame {

    private JTextField yourEloTextField;
    private JTextField otherEloTextField;
    private JLabel yourEloWin;
    private JLabel yourEloLose;

    public EloCalculatorFrame() {
        setTitle("Elo-Rechner(GommeHD.net)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400 , 120);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new FlowLayout());
        setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        initObjects();
        setVisible(true);
    }

    private void initObjects() {
        this.yourEloTextField = initYourEloTextField();
        this.otherEloTextField = initOtherEloTextField();
        this.yourEloWin = new JLabel("Wenn du gewinnst, bekommst du [...] Elo-Punkte");
        this.yourEloLose = new JLabel("Wenn du verlierst, verlierst du [...] Elo-Punkte");

        add(this.yourEloTextField);
        add(this.otherEloTextField);
        add(this.yourEloLose);
        add(this.yourEloWin);
        add(initButton());
    }

    private JTextField initYourEloTextField() {
        JTextField jTextField = new JTextField("Your Elo..." , 10);
        jTextField.setSize(25 , 25);
        return jTextField;
    }

    private JTextField initOtherEloTextField() {
        JTextField jTextField = new JTextField("Other Elo..." ,10);
        jTextField.setSize(25 , 25);
        return jTextField;
    }


    private JButton initButton() {
         JButton jButton = new JButton("Brechnen");
         jButton.setSize(100 , 100);

         jButton.addActionListener(e -> {
             try {
                this.yourEloWin.setText("Wenn du gewinnst, bekommst du " + calculateWinEloRate(Float.parseFloat(this.otherEloTextField.getText()) , Float.parseFloat(this.yourEloTextField.getText())) + " Elo-Punkte");
                this.yourEloLose.setText(("Wenn du verlierst, verlierst du " + calculateWinEloRate(Float.parseFloat(this.yourEloTextField.getText()) , Float.parseFloat(this.otherEloTextField.getText())) + " Elo-Punkte"));
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this , "Die Angaben sind keine Zahlen!");
             }
         });

        return jButton;
    }

    private int calculateWinEloRate(float clanWhoLost , float clanWhoWon) {
         int eloRate = (int) BigDecimal.valueOf(49 * (1 - (1 / (1 + Math.pow(10.0, (clanWhoLost / 400) - (clanWhoWon / 400))))))
                .setScale(1 , BigDecimal.ROUND_HALF_UP)
                .doubleValue();
         if (eloRate == 0)
             eloRate = 1;
         return eloRate;
    }

}
