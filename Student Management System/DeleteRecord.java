import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.*;

class DeleteRecord {

    private JFrame fr, pFrame;
    private JLabel closeIcon, deleteIcon;
    private JLabel photoL;
    private JTextField[] personalTextField;
    private JTextField[] yearTF;
    private JTextField[] schoolTF;
    private JTextField[] mmTF;
    private JTextField[] moTF;
    private JTextField courseTF;
    private JTextField branchTF;
    private Connection con;
    private static final int HEIGHT = 40;
    private static final int TF_FONT_SIZE = 25;
    private static final int TF_WIDTH = 320;
    private static final String FONT = "Times New Roman";

    DeleteRecord(JFrame f) {

        pFrame = f;

        fr = new JFrame();
        fr.setSize(1728, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        String id = JOptionPane.showInputDialog(fr, "Enter id to delete", "Delete Record", JOptionPane.QUESTION_MESSAGE);
        boolean found = checkId(id);
        if (!found) {
            JOptionPane.showMessageDialog(fr, "No Record for this id exist!", "ERROR", JOptionPane.ERROR_MESSAGE);
            pFrame.setEnabled(true);
            pFrame.requestFocus();
            fr.dispose();
        } else {

            JPanel panel = new JPanel();
            panel.setBounds(0, 0, 1728, 972);
            panel.setBackground(Color.BLACK);
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));

            JLabel titleL = new JLabel("Delete Record");
            titleL.setForeground(Color.ORANGE);
            titleL.setHorizontalAlignment(SwingConstants.CENTER);
            titleL.setFont(new Font(FONT, Font.BOLD, 50));
            titleL.setBounds((1728 - 700) / 2, 0, 700, 75);
            panel.add(titleL);

            closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
            closeIcon.setBounds(1650, 5, 75, 75);
            closeIcon.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    closeIcon.setIcon(new ImageIcon("images/close_big.png"));
                }

                public void mouseExited(MouseEvent me) {
                    closeIcon.setIcon(new ImageIcon("images/close_small.png"));
                }

                public void mouseClicked(MouseEvent me) {
                    pFrame.setEnabled(true);
                    pFrame.requestFocus();
                    fr.dispose();
                }
            });
            panel.add(closeIcon);

            deleteIcon = new JLabel(new ImageIcon("images/delete_small.png"));
            deleteIcon.setBounds(10, 5, 75, 75);
            deleteIcon.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    deleteIcon.setIcon(new ImageIcon("images/delete_big.png"));
                }

                public void mouseExited(MouseEvent me) {
                    deleteIcon.setIcon(new ImageIcon("images/delete_small.png"));
                }

                public void mouseClicked(MouseEvent me) {
                    deleteRecord(id);
                }
            });
            panel.add(deleteIcon);


            JPanel pLeft = new JPanel();
            pLeft.setBounds(0, 75, 866, 895);
            pLeft.setBackground(Color.BLACK);
            pLeft.setLayout(null);
            pLeft.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pLeft);

            JLabel personal = new JLabel("Personal Details");
            personal.setBounds((866 - 250) / 2, 20, 250, 35);
            personal.setForeground(Color.ORANGE);
            personal.setHorizontalAlignment(SwingConstants.CENTER);
            personal.setFont(new Font(FONT, Font.BOLD, 30));
            pLeft.add(personal);

            String[] personalLabelsString = {"ID", "Name", "Father's Name", "Mother's Name", "Date of Birth", "Gender", "Blood Group", "Mobile", "Email", "Address"};
            JLabel[] personalLabels = new JLabel[personalLabelsString.length];

            int x = 110;
            int y = 100;
            for (int i = 0; i < personalLabelsString.length; i++) {
                personalLabels[i] = new JLabel(personalLabelsString[i]);
                personalLabels[i].setBounds(x, y, 200, 30);
                personalLabels[i].setForeground(Color.ORANGE);
                personalLabels[i].setFont(new Font(FONT, Font.BOLD, 25));
                pLeft.add(personalLabels[i]);
                y += 75;
            }

            x = 400;
            y = 100;

            personalTextField = new JTextField[personalLabelsString.length];

            for (int i = 0; i < personalLabelsString.length; i++) {
                personalTextField[i] = new JTextField();
                personalTextField[i].setBounds(x, y, TF_WIDTH, HEIGHT);
                personalTextField[i].setForeground(Color.BLACK);
                personalTextField[i].setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
                personalTextField[i].setHorizontalAlignment(SwingConstants.CENTER);
                personalTextField[i].setEditable(false);
                pLeft.add(personalTextField[i]);
                y += 75;
            }

            JPanel pRightTop = new JPanel();
            pRightTop.setBounds(862, 520, 866, 450);
            pRightTop.setBackground(Color.BLACK);
            pRightTop.setLayout(null);
            pRightTop.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pRightTop);

            JLabel education = new JLabel("Educational Details");
            education.setBounds((866 - 260) / 2, 20, 260, 35);
            education.setForeground(Color.ORANGE);
            education.setHorizontalAlignment(SwingConstants.CENTER);
            education.setFont(new Font(FONT, Font.BOLD, 30));
            pRightTop.add(education);

            String[] educationLabelsString = {"High School", "Inter", "Graduation", "Post Graduation"};
            JLabel[] educationLabels = new JLabel[educationLabelsString.length];

            JLabel ch1 = new JLabel("EXAMINATION");
            ch1.setBounds(40, 75, 190, HEIGHT);
            ch1.setForeground(Color.ORANGE);
            //ch1.setHorizontalAlignment(SwingConstants.CENTER);
            ch1.setFont(new Font(FONT, Font.BOLD, 25));
            pRightTop.add(ch1);

            JLabel ch2 = new JLabel("YEAR");
            ch2.setBounds(260, 75, 100, HEIGHT);
            ch2.setForeground(Color.ORANGE);
            ch2.setHorizontalAlignment(SwingConstants.CENTER);
            ch2.setFont(new Font(FONT, Font.BOLD, 25));
            pRightTop.add(ch2);

            JLabel ch3 = new JLabel("SCHOOL");
            ch3.setBounds(380, 75, 260, HEIGHT);
            ch3.setForeground(Color.ORANGE);
            ch3.setHorizontalAlignment(SwingConstants.CENTER);
            ch3.setFont(new Font(FONT, Font.BOLD, 25));
            pRightTop.add(ch3);

            JLabel ch4 = new JLabel("MM");
            ch4.setBounds(660, 75, 75, HEIGHT);
            ch4.setForeground(Color.ORANGE);
            ch4.setHorizontalAlignment(SwingConstants.CENTER);
            ch4.setFont(new Font(FONT, Font.BOLD, 25));
            pRightTop.add(ch4);

            JLabel ch5 = new JLabel("MO");
            ch5.setBounds(755, 75, 75, HEIGHT);
            ch5.setForeground(Color.ORANGE);
            ch5.setHorizontalAlignment(SwingConstants.CENTER);
            ch5.setFont(new Font(FONT, Font.BOLD, 25));
            pRightTop.add(ch5);

            x = 50;
            y = 150;
            for (int i = 0; i < educationLabelsString.length; i++) {
                educationLabels[i] = new JLabel(educationLabelsString[i]);
                educationLabels[i].setBounds(x, y, 180, 30);
                educationLabels[i].setForeground(Color.ORANGE);
                educationLabels[i].setFont(new Font(FONT, Font.BOLD, 25));
                pRightTop.add(educationLabels[i]);
                y += 75;
            }

            x = 260;
            y = 150;
            yearTF = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                yearTF[i] = new JTextField();
                yearTF[i].setBounds(x, y, 100, 30);
                yearTF[i].setHorizontalAlignment(SwingConstants.CENTER);
                yearTF[i].setFont(new Font(FONT, Font.BOLD, 25));
                yearTF[i].setEditable(false);
                pRightTop.add(yearTF[i]);
                y += 75;
            }

            x = 380;
            y = 150;
            schoolTF = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                schoolTF[i] = new JTextField();
                schoolTF[i].setBounds(x, y, 260, 30);
                schoolTF[i].setHorizontalAlignment(SwingConstants.CENTER);
                schoolTF[i].setFont(new Font(FONT, Font.BOLD, 25));
                schoolTF[i].setEditable(false);
                pRightTop.add(schoolTF[i]);
                y += 75;
            }

            x = 660;
            y = 150;
            mmTF = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                mmTF[i] = new JTextField();
                mmTF[i].setBounds(x, y, 75, 30);
                mmTF[i].setHorizontalAlignment(SwingConstants.CENTER);
                mmTF[i].setFont(new Font(FONT, Font.BOLD, 25));
                mmTF[i].setEditable(false);
                pRightTop.add(mmTF[i]);
                y += 75;
            }

            x = 755;
            y = 150;
            moTF = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                moTF[i] = new JTextField();
                moTF[i].setBounds(x, y, 75, 30);
                moTF[i].setHorizontalAlignment(SwingConstants.CENTER);
                moTF[i].setFont(new Font(FONT, Font.BOLD, 25));
                moTF[i].setEditable(false);
                pRightTop.add(moTF[i]);
                y += 75;
            }

            JPanel pRightBottom = new JPanel();
            pRightBottom.setBounds(862, 75, 866, 448);
            pRightBottom.setBackground(Color.BLACK);
            pRightBottom.setLayout(null);
            pRightBottom.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pRightBottom);

            JLabel department = new JLabel("Departmental Details");
            department.setBounds((866 - 275) / 2, 20, 275, 35);
            department.setForeground(Color.ORANGE);
            department.setHorizontalAlignment(SwingConstants.CENTER);
            department.setFont(new Font(FONT, Font.BOLD, 30));
            pRightBottom.add(department);

            JLabel courseL = new JLabel("Course");
            courseL.setBounds(50, 100, 180, 30);
            courseL.setForeground(Color.ORANGE);
            courseL.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(courseL);

            JLabel branchL = new JLabel("Branch");
            branchL.setBounds(50, 175, 180, 30);
            branchL.setForeground(Color.ORANGE);
            branchL.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(branchL);

            courseTF = new JTextField();
            courseTF.setBounds(250, 100, 250, 30);
            courseTF.setFont(new Font(FONT, Font.BOLD, 25));
            courseTF.setEditable(false);
            pRightBottom.add(courseTF);

            branchTF = new JTextField();
            branchTF.setBounds(250, 175, 250, 30);
            branchTF.setFont(new Font(FONT, Font.BOLD, 25));
            branchTF.setEditable(false);
            pRightBottom.add(branchTF);

            photoL = new JLabel();
            photoL.setBounds(650, 100, 150, 175);
            photoL.setForeground(Color.ORANGE);
            photoL.setHorizontalAlignment(SwingConstants.CENTER);
            photoL.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            photoL.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(photoL);

            fr.add(panel);
            fr.setVisible(true);

            loadResult(id);
        }
    }

    private void deleteRecord(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
            PreparedStatement ps = con.prepareStatement("delete from student where id = ?");
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                PreparedStatement ps1 = con.prepareStatement("delete from edu where id = ?");
                ps1.setString(1, id);
                int rs1 = ps1.executeUpdate();
                if (rs1 > 0) {
                    String path = "stu_pic/" + id + ".jpg";
                    File f = new File(path);
                    if (f.delete()) {
                        JOptionPane.showMessageDialog(fr, "Record Deleted!");
                        pFrame.setEnabled(true);
                        pFrame.requestFocus();
                        fr.dispose();
                    } else {
                        JOptionPane.showMessageDialog(fr, "Error in deleting pic", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkId(String id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from student where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadResult(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from student where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            for (int i = 0; i < 10; i++) {
                personalTextField[i].setText(rs.getString(i + 1));
            }

            PreparedStatement ps1 = con.prepareStatement("select * from edu where id = ?");
            ps1.setString(1, id);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();

            courseTF.setText(rs1.getString(2));
            branchTF.setText(rs1.getString(3));

            for (int i = 1; i <= 4; i++) {
                if (Integer.parseInt(rs1.getString((i * 4))) != 0)
                    yearTF[i - 1].setText(rs1.getString((i * 4)));
            }

            for (int i = 1; i <= 4; i++) {
                if (!rs1.getString((i * 4) + 1).equalsIgnoreCase("0"))
                    schoolTF[i - 1].setText(rs1.getString((i * 4) + 1));
            }

            for (int i = 1; i <= 4; i++) {
                if (Integer.parseInt(rs1.getString((i * 4) + 2)) != 0)
                    mmTF[i - 1].setText(rs1.getString((i * 4) + 2));
            }

            for (int i = 1; i <= 4; i++) {
                if (Integer.parseInt(rs1.getString((i * 4) + 3)) != 0)
                    moTF[i - 1].setText(rs1.getString((i * 4) + 3));
            }

            String path = "stu_pic/" + id + ".jpg";
            Image original = Toolkit.getDefaultToolkit().getImage(path);
            Image scaled = original.getScaledInstance(150, 175, Image.SCALE_DEFAULT);
            photoL.setIcon(new ImageIcon(scaled));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}