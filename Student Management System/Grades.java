import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Grades implements ActionListener {

    private JFrame fr;
    private JLabel closeIcon;
    private JButton internal1, internal2, internal3, quiz1, quiz2, quiz3, assignment, presentation, project, external;
    Thread th;

    public Grades() {

        fr = new JFrame();
        fr.setSize(1200, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1200, 972);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));

        JLabel titleL = new JLabel("Grades");
        titleL.setBounds((1200 - 200) / 2, 40, 200, 70);
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setBackground(Color.RED);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 70));
        panel.add(titleL);

        closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
        closeIcon.setBounds(1110, 10, 75, 75);
        closeIcon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_big.png"));
            }

            public void mouseExited(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_small.png"));
            }

            public void mouseClicked(MouseEvent me) {
                //pFrame.setEnabled(true);
                fr.dispose();
            }
        });
        panel.add(closeIcon);

        JPanel internalPanel = new JPanel();
        internalPanel.setBounds(100, 150, 300, 500);
        internalPanel.setBackground(Color.BLACK);
        internalPanel.setLayout(null);
        internalPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
        panel.add(internalPanel);

        JLabel internalTitle = new JLabel("Internals");
        internalTitle.setBounds(0, 25, 300, 50);
        internalTitle.setHorizontalAlignment(SwingConstants.CENTER);
        internalTitle.setForeground(Color.RED);
        internalTitle.setFont(new Font("Times New Roman", Font.BOLD, 45));
        internalTitle.setLayout(null);
        internalPanel.add(internalTitle);

        internal1 = new JButton("Internal 1");
        internal1.setBounds(50, 150, 200, 50);
        internal1.setHorizontalAlignment(SwingConstants.CENTER);
        internal1.setForeground(Color.RED);
        internal1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        internal1.addActionListener(this);
        internal1.setFocusPainted(false);
        internalPanel.add(internal1);

        internal2 = new JButton("Internal 2");
        internal2.setBounds(50, 250, 200, 50);
        internal2.setHorizontalAlignment(SwingConstants.CENTER);
        internal2.setForeground(Color.RED);
        internal2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        internal2.addActionListener(this);
        internal2.setFocusPainted(false);
        internalPanel.add(internal2);

        internal3 = new JButton("Internal 3");
        internal3.setBounds(50, 350, 200, 50);
        internal3.setHorizontalAlignment(SwingConstants.CENTER);
        internal3.setForeground(Color.RED);
        internal3.setFont(new Font("Times New Roman", Font.BOLD, 30));
        internal3.addActionListener(this);
        internal3.setFocusPainted(false);
        internalPanel.add(internal3);

        JPanel quizPanel = new JPanel();
        quizPanel.setBounds(450, 150, 300, 500);
        quizPanel.setBackground(Color.BLACK);
        quizPanel.setLayout(null);
        quizPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
        panel.add(quizPanel);

        JLabel quizTitle = new JLabel("Quiz");
        quizTitle.setBounds(0, 25, 300, 50);
        quizTitle.setHorizontalAlignment(SwingConstants.CENTER);
        quizTitle.setForeground(Color.RED);
        quizTitle.setFont(new Font("Times New Roman", Font.BOLD, 45));
        quizTitle.setLayout(null);
        quizPanel.add(quizTitle);

        quiz1 = new JButton("Quiz 1");
        quiz1.setBounds(50, 150, 200, 50);
        quiz1.setHorizontalAlignment(SwingConstants.CENTER);
        quiz1.setForeground(Color.RED);
        quiz1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        quiz1.addActionListener(this);
        quiz1.setFocusPainted(false);
        quizPanel.add(quiz1);

        quiz2 = new JButton("Quiz 2");
        quiz2.setBounds(50, 250, 200, 50);
        quiz2.setHorizontalAlignment(SwingConstants.CENTER);
        quiz2.setForeground(Color.RED);
        quiz2.setFont(new Font("Times New Roman", Font.BOLD, 30));
        quiz2.addActionListener(this);
        quiz2.setFocusPainted(false);
        quizPanel.add(quiz2);

        quiz3 = new JButton("Quiz 3");
        quiz3.setBounds(50, 350, 200, 50);
        quiz3.setHorizontalAlignment(SwingConstants.CENTER);
        quiz3.setForeground(Color.RED);
        quiz3.setFont(new Font("Times New Roman", Font.BOLD, 30));
        quiz3.addActionListener(this);
        quiz3.setFocusPainted(false);
        quizPanel.add(quiz3);

        JPanel extraPanel = new JPanel();
        extraPanel.setBounds(800, 150, 300, 500);
        extraPanel.setBackground(Color.BLACK);
        extraPanel.setLayout(null);
        extraPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
        panel.add(extraPanel);

        JLabel extraTitle = new JLabel("Extra");
        extraTitle.setBounds(0, 25, 300, 50);
        extraTitle.setHorizontalAlignment(SwingConstants.CENTER);
        extraTitle.setForeground(Color.RED);
        extraTitle.setFont(new Font("Times New Roman", Font.BOLD, 45));
        extraTitle.setLayout(null);
        extraPanel.add(extraTitle);

        assignment = new JButton("Assignment");
        assignment.setBounds(50, 150, 200, 50);
        assignment.setHorizontalAlignment(SwingConstants.CENTER);
        assignment.setForeground(Color.RED);
        assignment.setFont(new Font("Times New Roman", Font.BOLD, 30));
        assignment.addActionListener(this);
        assignment.setFocusPainted(false);
        extraPanel.add(assignment);

        presentation = new JButton("Presentation");
        presentation.setBounds(50, 250, 200, 50);
        presentation.setHorizontalAlignment(SwingConstants.CENTER);
        presentation.setForeground(Color.RED);
        presentation.setFont(new Font("Times New Roman", Font.BOLD, 30));
        presentation.addActionListener(this);
        presentation.setFocusPainted(false);
        extraPanel.add(presentation);

        project = new JButton("Project");
        project.setBounds(50, 350, 200, 50);
        project.setHorizontalAlignment(SwingConstants.CENTER);
        project.setForeground(Color.RED);
        project.setFont(new Font("Times New Roman", Font.BOLD, 30));
        project.addActionListener(this);
        project.setFocusPainted(false);
        extraPanel.add(project);

        JPanel externalPanel = new JPanel();
        externalPanel.setBounds(100, 700, 1000, 200);
        externalPanel.setBackground(Color.BLACK);
        externalPanel.setLayout(null);
        externalPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
        panel.add(externalPanel);

        JLabel externalTitle = new JLabel("External Exam");
        externalTitle.setBounds((1000 - 300) / 2, 25, 300, 50);
        externalTitle.setHorizontalAlignment(SwingConstants.CENTER);
        externalTitle.setForeground(Color.RED);
        externalTitle.setFont(new Font("Times New Roman", Font.BOLD, 45));
        externalTitle.setLayout(null);
        externalPanel.add(externalTitle);

        external = new JButton("External");
        external.setBounds((1000 - 300) / 2, 100, 300, 50);
        external.setHorizontalAlignment(SwingConstants.CENTER);
        external.setForeground(Color.RED);
        external.setFont(new Font("Times New Roman", Font.BOLD, 30));
        external.addActionListener(this);
        external.setFocusPainted(false);
        externalPanel.add(external);

        fr.add(panel);
        fr.setVisible(true);
    }


    public static void main(String[] args) {
        new Grades();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == internal1) {
            new InternalGrades(1);
        } else if (e.getSource() == internal2) {
            new InternalGrades(2);
        } else if (e.getSource() == internal3) {
            new InternalGrades(3);
        } else if (e.getSource() == quiz1) {
            new QuizGrades(1);
        } else if (e.getSource() == quiz2) {
            new QuizGrades(2);
        } else if (e.getSource() == quiz3) {
            new QuizGrades(3);
        } else if (e.getSource() == assignment) {
            new AssignmentGrades();
        } else if (e.getSource() == presentation) {
            new PresentationGrades();
        } else if (e.getSource() == project) {
            new ProjectGrades();
        } else if (e.getSource() == external) {
            new ExternalGrades();
        }
    }
}
