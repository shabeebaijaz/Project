import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.regex.Pattern;

public class EditRecord implements FocusListener, ActionListener {

    private JFrame fr, pFrame;
    private JLabel closeIcon;
    private JLabel saveIcon;
    private JLabel photoL;
    private JTextField studentNameTF;
    private JTextField fatherNameTF;
    private JTextField motherNameTF;
    private JTextField phoneTF;
    private JTextField emailTF;
    private JTextField[] yearTF;
    private JTextField[] schoolTF;
    private JTextField[] mmTF;
    private JTextField[] moTF;
    private JTextArea addressTA;
    private String path;
    private JComboBox<Integer> dayOB, yearOB;
    private JComboBox<String> gen, blood, monthOB, courseCB, branchCB;
    private Image original, scaled;
    private static final int HEIGHT = 40;
    private static final int TF_FONT_SIZE = 25;
    private static final int TF_WIDTH = 320;
    private static final String FONT = "Times New Roman";
    private int flag = 0;
    private Connection con;

    EditRecord(JFrame f) {

        pFrame = f;

        fr = new JFrame();
        fr.setSize(1728, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        String id = JOptionPane.showInputDialog(fr, "Enter id to search", "Search Record", JOptionPane.QUESTION_MESSAGE);
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

            JLabel titleL = new JLabel("Edit Record");
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

            saveIcon = new JLabel(new ImageIcon("images/save 3 48.png"));
            saveIcon.setBounds(10, 5, 75, 75);
            saveIcon.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    saveIcon.setIcon(new ImageIcon("images/save 3 72.png"));
                }

                public void mouseExited(MouseEvent me) {
                    saveIcon.setIcon(new ImageIcon("images/save 3 48.png"));
                }

                public void mouseClicked(MouseEvent me) {
                    updateRecord(id);
                }
            });
            panel.add(saveIcon);

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

            String[] personalLabelsString = {"Name", "Father's Name", "Mother's Name", "Date of Birth", "Gender", "Blood Group", "Mobile", "Email", "Address"};
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
            studentNameTF = new JTextField();
            studentNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
            studentNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            studentNameTF.addFocusListener(this);
            studentNameTF.setHorizontalAlignment(SwingConstants.CENTER);
            pLeft.add(studentNameTF);
            y += 75;

            fatherNameTF = new JTextField();
            fatherNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
            fatherNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            fatherNameTF.addFocusListener(this);
            fatherNameTF.setHorizontalAlignment(SwingConstants.CENTER);
            pLeft.add(fatherNameTF);
            y += 75;

            motherNameTF = new JTextField();
            motherNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
            motherNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            motherNameTF.addFocusListener(this);
            motherNameTF.setHorizontalAlignment(SwingConstants.CENTER);
            pLeft.add(motherNameTF);
            y += 75;

            yearOB = new JComboBox<>();
            yearOB.setBounds(x, y, 80, HEIGHT);
            yearOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            for (int i = 1980; i <= 2019; i++) {
                yearOB.addItem(i);
            }
            pLeft.add(yearOB);


            String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            monthOB = new JComboBox<>(monthList);
            monthOB.setBounds(495, y, 150, HEIGHT);
            monthOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            monthOB.addActionListener(this);
            pLeft.add(monthOB);

            dayOB = new JComboBox<>();
            dayOB.setBounds(655, y, 65, HEIGHT);
            dayOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            pLeft.add(dayOB);
            y += 75;

            String[] genList = {"Select", "Male", "Female", "Other"};
            gen = new JComboBox<>(genList);
            gen.setBounds(x, y, TF_WIDTH, HEIGHT);
            gen.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            pLeft.add(gen);
            y += 75;

            String[] bloodList = {"Select", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
            blood = new JComboBox<>(bloodList);
            blood.setBounds(x, y, TF_WIDTH, HEIGHT);
            blood.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            pLeft.add(blood);
            y += 75;

            phoneTF = new JTextField();
            phoneTF.setBounds(x, y, TF_WIDTH, HEIGHT);
            phoneTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            phoneTF.addFocusListener(this);
            phoneTF.setHorizontalAlignment(SwingConstants.CENTER);
            pLeft.add(phoneTF);
            phoneTF.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent ke) {
                    if (phoneTF.getText().length() == 10 || ke.getKeyChar() < '0' || ke.getKeyChar() > '9')
                        ke.consume();
                }
            });
            y += 75;

            emailTF = new JTextField();
            emailTF.setBounds(x, y, TF_WIDTH, HEIGHT);
            emailTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            emailTF.addFocusListener(this);
            emailTF.setHorizontalAlignment(SwingConstants.CENTER);
            pLeft.add(emailTF);
            y += 75;

            addressTA = new JTextArea();
            addressTA.setBounds(x, y, TF_WIDTH, HEIGHT * 4);
            addressTA.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
            addressTA.setRows(4);
            addressTA.addFocusListener(this);
            pLeft.add(addressTA);

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
                yearTF[i].addFocusListener(this);
                pRightTop.add(yearTF[i]);
                yearTF[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                        JTextField tf = (JTextField) ke.getSource();
                        if (ke.getKeyChar() < '0' || ke.getKeyChar() > '9' || tf.getText().length() == 4)
                            ke.consume();
                    }
                });
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
                schoolTF[i].addFocusListener(this);
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
                mmTF[i].addFocusListener(this);
                mmTF[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                        if (ke.getKeyChar() < '0' || ke.getKeyChar() > '9')
                            ke.consume();
                    }
                });
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
                moTF[i].addFocusListener(this);
                pRightTop.add(moTF[i]);
                moTF[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                        if (ke.getKeyChar() < '0' || ke.getKeyChar() > '9')
                            ke.consume();
                    }
                });
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

            String[] courseList = {"B.C.A.", "B.Sc.", "B.tech.", "M.C.A.", "M.Sc.", "M.tech."};
            courseCB = new JComboBox<>(courseList);
            courseCB.setBounds(250, 100, 250, 30);
            courseCB.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(courseCB);

            branchCB = new JComboBox<>();
            branchCB.setBounds(250, 175, 250, 30);
            branchCB.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(branchCB);

            courseCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String c = courseCB.getSelectedItem().toString();
                    if (c.equalsIgnoreCase("B.C.A.") || c.equalsIgnoreCase("M.C.A.")) {
                        branchCB.removeAllItems();
                        branchCB.addItem("Computer Science");
                    } else if (c.equalsIgnoreCase("B.Sc.") || c.equalsIgnoreCase("M.Sc.")) {
                        branchCB.removeAllItems();
                        branchCB.addItem("Maths");
                        branchCB.addItem("Chem");
                        branchCB.addItem("Phy");
                        branchCB.addItem("Computer Science");
                    } else {
                        branchCB.removeAllItems();
                        branchCB.addItem("CS");
                        branchCB.addItem("IT");
                        branchCB.addItem("AG");
                        branchCB.addItem("ME");
                        branchCB.addItem("EC");
                    }
                }
            });

            photoL = new JLabel(new ImageIcon("images/placeHolder.png"));
            photoL.setBounds(650, 100, 150, 175);
            photoL.setForeground(Color.ORANGE);
            photoL.setHorizontalAlignment(SwingConstants.CENTER);
            photoL.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            photoL.setFont(new Font(FONT, Font.BOLD, 25));
            pRightBottom.add(photoL);
            photoL.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    try {
                        FileDialog fd = new FileDialog(fr, "Select Photo", FileDialog.LOAD);
                        fd.setVisible(true);
                        path = fd.getDirectory() + fd.getFile();

                        if (path.equals("nullnull")) //if nothing selected
                        {
                            flag = 0;
                            System.out.println("Nothing Selected");
                            path = "stu_pic/" + id + ".jpg";
                        } else {
                            flag = 1;
                            System.out.println(path);

                            original = Toolkit.getDefaultToolkit().getImage(path);
                            scaled = original.getScaledInstance(150, 175, Image.SCALE_DEFAULT);
                            photoL.setIcon(new ImageIcon(scaled));
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });

            fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            fr.add(panel);
            fr.setVisible(true);

            loadResult(id);
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

            studentNameTF.setText(rs.getString(2));
            fatherNameTF.setText(rs.getString(3));
            motherNameTF.setText(rs.getString(4));

            String date = rs.getString(5);
            String[] dob = date.split("/");
            yearOB.setSelectedItem(new Integer(dob[0]));
            monthOB.setSelectedItem(dob[1]);
            dayOB.setSelectedItem(new Integer(dob[2]));

            gen.setSelectedItem(rs.getString(6));
            blood.setSelectedItem(rs.getString(7));
            phoneTF.setText(rs.getString(8));
            emailTF.setText(rs.getString(9));
            addressTA.setText(rs.getString(10));

            PreparedStatement ps1 = con.prepareStatement("select * from edu where id = ?");
            ps1.setString(1, id);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();

            courseCB.setSelectedItem(rs1.getString(2));
            branchCB.setSelectedItem(rs1.getString(3));

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

    @Override
    public void focusGained(FocusEvent e) {

        if (e.getSource() == addressTA) {
            addressTA.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 4, Color.RED));
            addressTA.selectAll();
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.selectAll();
            tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 4, Color.RED));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

        if (e.getSource() == addressTA) {
            addressTA.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == monthOB) {
            int mo = monthOB.getSelectedIndex();
            if (mo == 1) {
                for (int i = 1; i <= 28; i++) {
                    dayOB.addItem(i);
                }
            } else if (mo == 3 || mo == 5 || mo == 8 || mo == 10) {
                for (int i = 1; i <= 30; i++) {
                    dayOB.addItem(i);
                }
            } else {
                for (int i = 1; i <= 31; i++) {
                    dayOB.addItem(i);
                }
            }
        }
    }

    private boolean validatePostGraduation(String pgYear, String pgSchool, String pgMM, String pgMO) {
        if (!pgMM.isEmpty()) {
            if (Integer.parseInt(pgMO) > Integer.parseInt(pgMM)) {
                JOptionPane.showMessageDialog(fr, "Post Graduation: Marks Obtained cannot be more than Max Marks", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else
                return true;
        }
        return true;
    }

    private boolean validateGraduation(String gradYear, String gradSchool, String gradMM, String gradMO) {
        if (!gradMM.isEmpty()) {
            if (Integer.parseInt(gradMO) > Integer.parseInt(gradMM)) {
                JOptionPane.showMessageDialog(fr, "Graduation: Marks Obtained cannot be more than Max Marks", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else
                return true;
        } else
            return true;
    }

    private boolean validateInter(String interYear, String interSchool, String interMM, String interMO) {
        if (interYear.isEmpty() || interSchool.isEmpty() || interMM.isEmpty() || interMO.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Intermediate details cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.parseInt(interMO) > Integer.parseInt(interMM)) {
            JOptionPane.showMessageDialog(fr, "Intermediate: Marks Obtained cannot be more than Max Marks", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            return true;
    }

    private boolean validateHighSchool(String hsYear, String hsSchool, String hsMM, String hsMO) {
        if (hsYear.isEmpty() || hsSchool.isEmpty() || hsMM.isEmpty() || hsMO.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "High School details cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.parseInt(hsMO) > Integer.parseInt(hsMM)) {
            JOptionPane.showMessageDialog(fr, "High School: Marks Obtained cannot be more than Max Marks", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            return true;
    }

    private boolean validatePhone(String phone) {
        return phone.length() == 10;
    }

    private boolean validateName(String sName) {
        return sName.isEmpty() || sName.matches("(?=.*[0-9]).*") || sName.matches("(?=.*[!@#$%^&*()_+-]).*");
    }

    private boolean validateGender(String gender) {
        if (gender.equals("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            return true;
    }

    private boolean validateBloodGroup(String bloodGroup) {
        if (bloodGroup.equals("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            return true;
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Email cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!pat.matcher(email).matches()) {
            JOptionPane.showMessageDialog(fr, "Please Check Email", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
            return true;
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

    private void updateRecord(String userId) {

        //personal
        String sName = studentNameTF.getText().trim();
        String fName = fatherNameTF.getText().trim();
        String mName = motherNameTF.getText().trim();
        String year = yearOB.getSelectedItem().toString();
        String month = monthOB.getSelectedItem().toString();
        String day = null;
        try {
            day = dayOB.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(fr, "Please Select Month", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String gender = gen.getSelectedItem().toString();
        String bloodGroup = blood.getSelectedItem().toString();
        String phone = phoneTF.getText().trim();
        String email = emailTF.getText().trim();
        String address = addressTA.getText().trim();

        //edu
        String hsYear = yearTF[0].getText().trim();
        String hsSchool = schoolTF[0].getText().trim();
        String hsMM = mmTF[0].getText().trim();
        String hsMO = moTF[0].getText().trim();

        String interYear = yearTF[1].getText().trim();
        String interSchool = schoolTF[1].getText().trim();
        String interMM = mmTF[1].getText().trim();
        String interMO = moTF[1].getText().trim();

        String gradYear = yearTF[2].getText().trim();
        if (gradYear.isEmpty()) gradYear = "0";
        String gradSchool = schoolTF[2].getText().trim();
        if (gradSchool.isEmpty()) gradSchool = "0";
        String gradMM = mmTF[2].getText().trim();
        if (gradMM.isEmpty()) gradMM = "0";
        String gradMO = moTF[2].getText().trim();
        if (gradMO.isEmpty()) gradMO = "0";

        String pgYear = yearTF[3].getText().trim();
        if (pgYear.isEmpty()) pgYear = "0";
        String pgSchool = schoolTF[3].getText().trim();
        if (pgSchool.isEmpty()) pgSchool = "0";
        String pgMM = mmTF[3].getText().trim();
        if (pgMM.isEmpty()) pgMM = "0";
        String pgMO = moTF[3].getText().trim();
        if (pgMO.isEmpty()) pgMO = "0";

        //dept
        String branch = null;
        String course = courseCB.getSelectedItem().toString();
        try {
            branch = branchCB.getSelectedItem().toString();
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(fr, "Please Select Course", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String photo = path;

        if (validateName(sName)) {
            JOptionPane.showMessageDialog(fr, "Check Student Name", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (validateName(fName)) {
            JOptionPane.showMessageDialog(fr, "Check Father's Name", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (validateName(mName)) {
            JOptionPane.showMessageDialog(fr, "Check Mother's Name", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!validateGender(gender)) {
            JOptionPane.showMessageDialog(fr, "Please select gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!validateBloodGroup(bloodGroup)) {
            JOptionPane.showMessageDialog(fr, "Please select Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!validatePhone(phone)) {
            JOptionPane.showMessageDialog(fr, "Please check phone number", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(fr, "Please check email", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Address cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!validateHighSchool(hsYear, hsSchool, hsMM, hsMO)) {
        } else if (!validateInter(interYear, interSchool, interMM, interMO)) {
        } else if (!validateGraduation(gradYear, gradSchool, gradMM, gradMO)) {
        } else if (!validatePostGraduation(pgYear, pgSchool, pgMM, pgMO)) {
        } else if (photo == "nullnull") {
            JOptionPane.showMessageDialog(fr, "Please Select Photo", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String dob = year + "/" + month + "/" + day;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
                PreparedStatement ps = con.prepareStatement("update student set name = ?,fname = ?,mname = ?,dob = ?,gender = ?,bloodg = ?,mobile = ?,email = ?,address = ? where id in (?)");
                ps.setString(1, sName);
                ps.setString(2, fName);
                ps.setString(3, mName);
                ps.setString(4, dob);
                ps.setString(5, gender);
                ps.setString(6, bloodGroup);
                ps.setString(7, phone);
                ps.setString(8, email);
                ps.setString(9, address);
                ps.setString(10, userId);

                int rs = ps.executeUpdate();
                if (rs > 0) {
                    PreparedStatement ps2 = con.prepareStatement("update edu set course = ?,branch = ?,hsyear = ?,hsschool = ?,hsmax = ?,hsob = ?,interyear = ?,interschool = ?,intermax = ?,interob = ?,gradyear = ?,gradschool = ?,gradmax = ?,gradob = ?,pgyear = ?,pgschool = ?,pgmax = ?,pgob = ? where id in (?)");
                    ps2.setString(1, course);
                    ps2.setString(2, branch);
                    ps2.setString(3, hsYear);
                    ps2.setString(4, hsSchool);
                    ps2.setString(5, hsMM);
                    ps2.setString(6, hsMO);
                    ps2.setString(7, interYear);
                    ps2.setString(8, interSchool);
                    ps2.setString(9, interMM);
                    ps2.setString(10, interMO);
                    ps2.setString(11, gradYear);
                    ps2.setString(12, gradSchool);
                    ps2.setString(13, gradMM);
                    ps2.setString(14, gradMO);
                    ps2.setString(15, pgYear);
                    ps2.setString(16, pgSchool);
                    ps2.setString(17, pgMM);
                    ps2.setString(18, pgMO);
                    ps2.setString(19, userId);
                    int rs2 = ps2.executeUpdate();
                    if (rs2 > 0) {
                        try {
                            if (flag == 0) {

                            } else if (flag == 1) {
                                File del = new File("stu_pic/" + userId + ".jpg");
                                del.delete();
                                File source = new File(photo);
                                File dest = new File("stu_pic/" + userId + ".jpg");
                                Files.copy(source.toPath(), dest.toPath());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(fr, "Record Updated for ID: " + userId);
                        resetAll();
                    } else {
                        JOptionPane.showMessageDialog(fr, "Education not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(fr, "Record not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                }
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

    private void resetAll() {
        studentNameTF.setText("");
        motherNameTF.setText("");
        fatherNameTF.setText("");
        yearOB.setSelectedItem(1);
        monthOB.setSelectedItem(1);
        dayOB.setSelectedItem(1);
        phoneTF.setText("");
        emailTF.setText("");
        addressTA.setText("");
        courseCB.setSelectedItem(1);
        branchCB.setSelectedItem(1);

        for (int i = 0; i < 4; i++) {
            yearTF[i].setText("");
            schoolTF[i].setText("");
            mmTF[i].setText("");
            moTF[i].setText("");
        }

        photoL.setIcon(new ImageIcon("images/placeHolder.png"));
    }
}