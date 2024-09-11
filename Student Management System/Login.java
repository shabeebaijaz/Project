import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

class Login {
    private JFrame fr;
    private JLabel closeIcon;
    private JLabel forgotPass;
    private JLabel newUser;
    private JTextField username;
    private JPasswordField password;

    Login() {
        fr = new JFrame();
        fr.setSize(700, 700);
        fr.setLocationRelativeTo(null);
        fr.setContentPane(new JLabel(new ImageIcon("images/login_bg.jpg")));
        fr.setUndecorated(true);
        fr.setLayout(null);

        closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
        closeIcon.setBounds(600, 30, 75, 75);
        fr.add(closeIcon);
        closeIcon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_big.png"));
            }

            public void mouseExited(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_small.png"));
            }

            public void mouseClicked(MouseEvent me) {
                fr.dispose();
            }
        });

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds((700 - 400) / 2, (700 - 650) / 2, 400, 650);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.GREEN));
        loginPanel.setLayout(null);

        JLabel loginTitle = new JLabel(new ImageIcon(new ImageIcon("images/login.jpg").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
        loginTitle.setBounds((400 - 200) / 2, 45, 200, 50);
        loginPanel.add(loginTitle);

        username = new JTextField("Enter Username");
        username.setBounds((400 - 250) / 2, 210, 250, 50);
        username.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        username.setHorizontalAlignment(JTextField.CENTER);
        username.setBorder(BorderFactory.createEmptyBorder(1, 42, 1, 1));
        loginPanel.add(username);

        username.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (username.getText().trim().equalsIgnoreCase("Enter Username"))
                    username.setText("");
            }

            public void focusLost(FocusEvent fe) {
                if (username.getText().trim().equalsIgnoreCase("") || username.getText().trim().isEmpty())
                    username.setText("Enter Username");
            }
        });

        JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("images/user.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        userIcon.setBounds(0, 0, 40, 40);
        username.add(userIcon);

        password = new JPasswordField("Enter Password");
        password.setBounds((400 - 250) / 2, 310, 250, 50);
        password.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        password.setHorizontalAlignment(JTextField.CENTER);
        password.setBorder(BorderFactory.createEmptyBorder(1, 42, 1, 1));
        password.setEchoChar('*');
        loginPanel.add(password);

        password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (Arrays.equals(password.getPassword(), new char[]{'E', 'n', 't', 'e', 'r', ' ', 'P', 'a', 's', 's', 'w', 'o', 'r', 'd',}))
                    password.setText("");
            }

            public void focusLost(FocusEvent fe) {
                if (password.getPassword().length == 0)
                    password.setText("Enter Password");
            }
        });

        JLabel keyIcon = new JLabel(new ImageIcon(new ImageIcon("images/key.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        keyIcon.setBounds(0, 0, 40, 40);
        password.add(keyIcon);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds((400 - 200) / 2, 410, 200, 50);
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 30));
        //fr.getRootPane().setDefaultButton(loginButton);
        loginButton.setMnemonic(KeyEvent.VK_L);
        loginButton.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        loginButton.setBackground(Color.RED);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String user = username.getText().trim();
                String pass = new String(password.getPassword());

                if (user.isEmpty() || user.equalsIgnoreCase("Enter Username")) {
                    JOptionPane.showMessageDialog(fr, "Username cannot be Empty!");
                } else if (pass.isEmpty() || pass.equalsIgnoreCase("Enter Password")) {
                    JOptionPane.showMessageDialog(fr, "Password cannot be Empty!");
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
                        PreparedStatement ps = con.prepareStatement("select * from admin where id = ?");
                        ps.setString(1, user);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String correctPass = rs.getString(5);
                            if (pass.equals(correctPass)) {
                                //JOptionPane.showMessageDialog(fr, "Success");
                                new MainMenu(user);
                                fr.dispose();
                            } else {
                                JOptionPane.showMessageDialog(fr, "Incorrect Password");
                            }
                        } else {
                            JOptionPane.showMessageDialog(fr, "Id does not exist!!");
                            new SignUp(fr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        forgotPass = new JLabel("Forgot Password?");
        forgotPass.setBounds(25, 510, 160, 40);
        forgotPass.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        loginPanel.add(forgotPass);

        forgotPass.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                forgotPass.setForeground(Color.BLUE);
                forgotPass.setFont(new Font("Times New Roman", Font.PLAIN, 21));
            }

            public void mouseExited(MouseEvent me) {
                forgotPass.setForeground(Color.BLACK);
                forgotPass.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            }

            public void mouseClicked(MouseEvent me) {
                fr.setEnabled(false);
                new ResetPassword(fr);
            }
        });

        newUser = new JLabel("New User? Sign Up");
        newUser.setBounds(225, 510, 165, 40);
        newUser.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        loginPanel.add(newUser);

        newUser.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                newUser.setForeground(Color.BLUE);
                newUser.setFont(new Font("Times New Roman", Font.PLAIN, 21));
            }

            public void mouseExited(MouseEvent me) {
                newUser.setForeground(Color.BLACK);
                newUser.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            }

            public void mouseClicked(MouseEvent me) {
                fr.setEnabled(false);
                new SignUp(fr);
            }
        });

        fr.add(loginPanel);
        fr.setVisible(true);
    }
public static void main(String args[])
{
	new Login();	
}

}
