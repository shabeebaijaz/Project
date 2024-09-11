import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SignUp implements Runnable, KeyListener{

    private JFrame fr, pFrame;
    private JTextField[] textField;
    private JPasswordField passPF, cPassPF;
    private JLabel movingLabel,strengthL,checkL;
    private boolean flag = false;
    private Connection con;

    SignUp(JFrame f) {
        pFrame = f;

        fr = new JFrame();
        fr.setSize(750, 750);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 750, 750);
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        panel.setLayout(null);

        JLabel titleL = new JLabel("Sign Up");
        titleL.setBounds((750 - 250) / 2, 50, 250, 50);
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        String[] labelList = {"ID", "Name", "Phone", "Email", "Password", "Confirm"};
        JLabel[] label = new JLabel[labelList.length];

        int y = 150;
        for (int i = 0; i < labelList.length; i++) {
            label[i] = new JLabel(labelList[i]);
            label[i].setForeground(Color.RED);
            label[i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
            label[i].setBounds(130, y, 120, 30);
            panel.add(label[i]);
            y = y + 75;
        }

        textField = new JTextField[4];

        y = 150;
        for (int i = 0; i < 4; i++) {
            textField[i] = new JTextField();
            textField[i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
            textField[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
            textField[i].setBounds(320, y, 280, 35);
            textField[i].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    JTextField tf = (JTextField) e.getSource();
                    tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    JTextField tf = (JTextField) e.getSource();
                    tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
                }
            });
            panel.add(textField[i]);
            y = y + 75;
        }

        textField[0].addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textField[0].getText().length() == 3) {
                    e.consume();
                }
            }
        });

        passPF = new JPasswordField();
        passPF.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        passPF.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
        passPF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                pf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                pf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
            }
        });
	
        passPF.addKeyListener(this);
        passPF.setBounds(320, y, 280, 30);
        passPF.setEchoChar('*');
        panel.add(passPF);
	
	
        strengthL = new JLabel();
        strengthL.setBounds(320, 480, 550, 30);
        strengthL.setHorizontalAlignment(JTextField.CENTER);
        strengthL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        strengthL.setForeground(Color.RED);
        panel.add(strengthL);

        y = y + 75;
        cPassPF = new JPasswordField();
        cPassPF.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        cPassPF.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
        cPassPF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                pf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                pf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
            }
        });

	
        cPassPF.addKeyListener(this);
        cPassPF.setBounds(320, y, 280, 30);
        cPassPF.setEchoChar('*');
        panel.add(cPassPF);

	y = y + 30;
        checkL = new JLabel();
        checkL.setBounds(320, y, 550, 30);
        checkL.setHorizontalAlignment(JTextField.CENTER);
        checkL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        checkL.setForeground(Color.RED);
        panel.add(checkL);

        y = y + 85;
        JLabel saveB = new JLabel("Save");
        saveB.setForeground(Color.RED);
        saveB.setHorizontalAlignment(SwingConstants.CENTER);
        saveB.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        saveB.setBounds(90, y, 150, 40);
        saveB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveRecord();
            }
        });
        saveB.setBackground(Color.BLACK);
        panel.add(saveB);

        JLabel cancelB = new JLabel("Cancel");
        cancelB.setForeground(Color.RED);
        cancelB.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        cancelB.setBounds(290, y, 150, 40);
        cancelB.setHorizontalAlignment(SwingConstants.CENTER);
        cancelB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flag = false;
                pFrame.setEnabled(true);
                fr.dispose();
            }
        });
        panel.add(cancelB);

        JLabel resetB = new JLabel("Reset");
        resetB.setForeground(Color.RED);
        resetB.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        resetB.setBounds(490, y, 150, 40);
        resetB.setHorizontalAlignment(SwingConstants.CENTER);
        resetB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < 4; i++)
                    textField[i].setText("");
                passPF.setText("");
                cPassPF.setText("");
            }
        });
        panel.add(resetB);

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

    private void saveRecord() {

        String id = textField[0].getText().trim();
        String name = textField[1].getText().trim();
        String phone = textField[2].getText().trim();
        String email = textField[3].getText().trim();
        char[] pass = passPF.getPassword();
        char[] cpass = cPassPF.getPassword();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "ID cannot be empty!");
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Name cannot be empty!");
        } else if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Phone cannot be empty!");
        } else if (!validate(email)) {
            JOptionPane.showMessageDialog(fr, "Please Check Email!");
        } else if (pass.length == 0) {
            JOptionPane.showMessageDialog(fr, "Password cannot be empty!");
        } else if (cpass.length == 0) {
            JOptionPane.showMessageDialog(fr, "Confirm Password cannot be empty!");
        } else {
            if (!Arrays.equals(pass, cpass)) {
                JOptionPane.showMessageDialog(fr, "Password and Confirm Password does not match!");
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
                    PreparedStatement ps = con.prepareStatement("insert into admin (id,name,phone,email,pass) values (?,?,?,?,?)");
                    ps.setString(1, id);
                    ps.setString(2, name);
                    ps.setString(3, phone);
                    ps.setString(4, email);
                    ps.setString(5, new String(pass));
                    int rs = ps.executeUpdate();
                    if (rs > 0) {
                        JOptionPane.showMessageDialog(fr, "Account Created");
                        flag = false;
                        pFrame.setEnabled(true);
                        fr.dispose();
                    } else {
                        JOptionPane.showMessageDialog(fr, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private boolean validate(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        else
            return pat.matcher(email).matches();
    }

    public void run() {
        int x = 750;
        while (flag) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (x == -250)
                x = 750;
            else {
                movingLabel.setBounds(x, 700, 250, 40);
                x--;
            }
        }
    }

    public void keyReleased(KeyEvent ke) {
        char[] pass = passPF.getPassword();
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
            strengthL.setText(" ");
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
        char[] cPass = cPassPF.getPassword();
        if (Arrays.equals(pass, cPass)) {
            checkL.setText("Same");
            checkL.setForeground(Color.GREEN);
            //saveB.setEnabled(true);
        } else {
            checkL.setText("Not Same");
            checkL.setForeground(Color.RED);
            //saveB.setEnabled(false);
        }
    }

    public void keyPressed(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }
}

