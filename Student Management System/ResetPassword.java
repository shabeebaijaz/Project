import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class ResetPassword implements ActionListener, Runnable {
    private JFrame loginFrame, fr;
    private JLabel movingLabel;
    private JTextField idT, phoneT, emailT;
    private JButton resetB, cancelB;
    private boolean flag;

    ResetPassword(JFrame f) {
        loginFrame = f;
        fr = new JFrame("Reset Password");
        fr.setSize(500, 500);
        fr.setLocationRelativeTo(null);
        fr.setUndecorated(true);
        fr.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        panel.setBounds(0, 0, 500, 500);

        JLabel titleL = new JLabel("Reset Password");
        titleL.setBounds((500 - 250) / 2, 50, 250, 50);
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        JLabel idL = new JLabel("ID");
        idL.setBounds(100, 130, 100, 20);
        idL.setForeground(Color.RED);
        idL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        panel.add(idL);

        JLabel phoneL = new JLabel("Phone");
        phoneL.setBounds(100, 200, 100, 20);
        phoneL.setForeground(Color.RED);
        phoneL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        panel.add(phoneL);

        JLabel emailL = new JLabel("Email");
        emailL.setBounds(100, 270, 100, 20);
        emailL.setForeground(Color.RED);
        emailL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        panel.add(emailL);

        idT = new JTextField();
        idT.setBounds(100, 150, 300, 30);
        idT.setFont(new Font("Times New Roman", Font.BOLD, 20));
        idT.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 2));
        panel.add(idT);

        phoneT = new JTextField();
        phoneT.setBounds(100, 220, 300, 30);
        phoneT.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        phoneT.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 2));
        panel.add(phoneT);

        emailT = new JTextField();
        emailT.setBounds(100, 290, 300, 30);
        emailT.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        emailT.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 2));
        panel.add(emailT);

        resetB = new JButton("Search");
        resetB.setBounds(100, 370, 140, 40);
        resetB.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        resetB.addActionListener(this);
        resetB.setFocusPainted(false);
        panel.add(resetB);

        cancelB = new JButton("Cancel");
        cancelB.setBounds(260, 370, 140, 40);
        cancelB.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        cancelB.addActionListener(this);
        cancelB.setFocusPainted(false);
        panel.add(cancelB);

        movingLabel = new JLabel("Student Management System");
        movingLabel.setForeground(Color.RED);
        movingLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(movingLabel);

        fr.add(panel);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread th = new Thread(this);
        flag = true;
        th.start();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == resetB) {
            String id = idT.getText().trim();
            String phone = phoneT.getText().trim();
            String email = emailT.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Phone cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
                    PreparedStatement ps = con.prepareStatement("select * from admin where id = ?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if (rs.getString(3).equals(phone) && rs.getString(4).equals(email)) {
                            System.out.println(rs.getString(5));
                            new SetPassword(fr, id);
                            fr.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(fr, "Data does not match!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(fr, "ID does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        fr.dispose();
                        new SignUp(fr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (ae.getSource() == cancelB) {
            flag = false;
            loginFrame.setEnabled(true);
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
}
