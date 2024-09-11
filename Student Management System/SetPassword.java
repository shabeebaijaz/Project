import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

class SetPassword implements ActionListener, KeyListener, Runnable {
    private JFrame pFrame;
    private JFrame fr;
    private JLabel strengthL;
    private JLabel checkL;
    private JLabel movingLabel;
    private JPasswordField newPassPF, confirmPassPF;
    private JButton saveB, cancelB;
    private String id;
    private Connection con;
    private boolean flag;

    SetPassword(JFrame f, String s) {

        pFrame = f;
        id = s;

        fr = new JFrame("Set Password");
        fr.setSize(500, 500);
        fr.setLocationRelativeTo(null);
        fr.setUndecorated(true);
        fr.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 500, 500);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));

        JLabel titleL = new JLabel("Set Password");
        titleL.setBounds((500 - 250) / 2, 50, 250, 50);
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        JLabel newL = new JLabel("New Password");
        newL.setBounds((500 - 300) / 2, 130, 150, 20);
        newL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        newL.setForeground(Color.RED);
        panel.add(newL);

        newPassPF = new JPasswordField();
        newPassPF.setBounds((500 - 300) / 2, 150, 300, 30);
        newPassPF.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        newPassPF.setForeground(Color.RED);
        newPassPF.addKeyListener(this);
        panel.add(newPassPF);

        JLabel confirmL = new JLabel("Confirm Password");
        confirmL.setBounds((500 - 300) / 2, 240, 170, 20);
        confirmL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        confirmL.setForeground(Color.RED);
        panel.add(confirmL);

        confirmPassPF = new JPasswordField();
        confirmPassPF.setBounds((500 - 300) / 2, 260, 300, 30);
        confirmPassPF.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        confirmPassPF.setForeground(Color.RED);
        confirmPassPF.addKeyListener(this);
        panel.add(confirmPassPF);

        strengthL = new JLabel();
        strengthL.setBounds(300, 180, 95, 30);
        strengthL.setHorizontalAlignment(JTextField.CENTER);
        strengthL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        strengthL.setForeground(Color.RED);
        panel.add(strengthL);

        checkL = new JLabel();
        checkL.setBounds(315, 290, 90, 30);
        checkL.setHorizontalAlignment(JTextField.CENTER);
        checkL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        checkL.setForeground(Color.RED);
        panel.add(checkL);

        saveB = new JButton("Update");
        saveB.setBounds(100, 370, 140, 40);
        saveB.setFont(new Font("Times New Roman", Font.BOLD, 30));
        saveB.addActionListener(this);
        saveB.setFocusPainted(false);
        saveB.setEnabled(false);
        panel.add(saveB);

        cancelB = new JButton("Cancel");
        cancelB.setBounds(260, 370, 140, 40);
        cancelB.setFont(new Font("Times New Roman", Font.BOLD, 30));
        cancelB.addActionListener(this);
        cancelB.setFocusPainted(false);
        panel.add(cancelB);

        movingLabel = new JLabel("Student Management System");
        movingLabel.setForeground(Color.RED);
        movingLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(movingLabel);

        fr.add(panel);
        fr.setVisible(true);

        Thread th = new Thread(this);
        flag = true;
        th.start();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == saveB) {
            char[] pass = newPassPF.getPassword();
            char[] cPass = confirmPassPF.getPassword();

            if (pass.length != 0 || cPass.length != 0) {
                if (Arrays.equals(pass, cPass)) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
                        PreparedStatement ps = con.prepareStatement("update admin set pass = ? where id in (?)");
                        ps.setString(1, new String(pass));
                        ps.setString(2, id);
                        int rs = ps.executeUpdate();
                        if (rs > 0) {
                            JOptionPane.showMessageDialog(fr, "Password Updated Successfully!!");
                            new Login();
                        } else {
                            //error
                        }
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                } else {
                    //password not same
                }
            } else {
                //cannot be empty
            }

        } else if (ae.getSource() == cancelB) {
            flag = false;
            pFrame.setEnabled(true);
            fr.dispose();
        }
    }

    public void run() {
        int x = 500;
        while (flag) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (x == -250)
                x = 500;
            else {
                movingLabel.setBounds(x, 450, 250, 40);
                x--;
            }
        }
    }

    public void keyReleased(KeyEvent ke) {
        char[] pass = newPassPF.getPassword();
        int passwordScore = 0;

        if (pass.length >= 10)
            passwordScore += 2;
        else
            passwordScore += 1;

        if (new String(pass).matches("(?=.*[0-9]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[a-z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[A-Z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[~!@#$%^&*()_-]).*"))
            passwordScore += 2;

        if (passwordScore <= 2) {
            strengthL.setText("Very Weak");
            strengthL.setForeground(Color.RED);
        } else if (passwordScore <= 4) {
            strengthL.setForeground(Color.ORANGE);
            strengthL.setText("Weak");
        } else if (passwordScore <= 6) {
            strengthL.setForeground(Color.ORANGE);
            strengthL.setText("Medium");
        } else if (passwordScore <= 8) {
            strengthL.setForeground(Color.GREEN);
            strengthL.setText("Strong");
        } else {
            strengthL.setForeground(Color.GREEN);
            strengthL.setText("Very Strong");
        }
        char[] cPass = confirmPassPF.getPassword();
        if (Arrays.equals(pass, cPass)) {
            checkL.setText("Same");
            checkL.setForeground(Color.GREEN);
            saveB.setEnabled(true);
        } else {
            checkL.setText("Not Same");
            checkL.setForeground(Color.RED);
            saveB.setEnabled(false);
        }
    }

    public void keyPressed(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }
}
