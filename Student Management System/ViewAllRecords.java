import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;

public class ViewAllRecords {
    private JFrame fr, pFrame;
    private JLabel closeIcon;
    private static final String FONT = "Times New Roman";
    private static final String[] colNames = {"ID", "Student", "Father", "Mother", "Date of Birth", "Gender", "Blood", "Mobile", "Email", "Address", "Course", "Branch", "HS Year ", "HS Name ", "HS Max ", "HS Obtained", "I Year ", "I Name ", "I Max ", "I Obtained", "G Year ", "G Name ", "G Max ", "G Obtained", "PG Year ", "PG Name ", "PG Max ", "PG Obtained"};
    private Connection con;
    private JPanel panel;

    public ViewAllRecords(JFrame f) {

        pFrame = f;

        fr = new JFrame();
        fr.setSize(1900, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        panel = new JPanel();
        panel.setBounds(0, 0, 1900, 972);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));

        JLabel titleL = new JLabel("All Records");
        titleL.setForeground(Color.ORANGE);
        titleL.setHorizontalAlignment(SwingConstants.CENTER);
        titleL.setFont(new Font(FONT, Font.BOLD, 50));
        titleL.setBounds((1900 - 700) / 2, 0, 700, 75);
        panel.add(titleL);

        closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
        closeIcon.setBounds(1825, 5, 75, 75);
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

        fr.add(panel);
        fr.setVisible(true);

        loadData();
    }

    private void loadData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "root");
            PreparedStatement ps1 = con.prepareStatement("select * from student");
            PreparedStatement ps2 = con.prepareStatement("select * from edu");
            ResultSet resultSet1 = ps1.executeQuery();
            ResultSet resultSet2 = ps2.executeQuery();
            Vector<String> colHeadings = new Vector<String>(Arrays.asList(colNames));
            Vector<Vector> row = new Vector<>();
            while (resultSet1.next() & resultSet2.next()) {
                Vector<Object> data = new Vector<>();
                for (int i = 1; i <= 10; i++)
                    data.add(resultSet1.getString(i));

                for (int i = 2; i <= 19; i++)
                    data.add(resultSet2.getString(i));

                row.add(data);
            }
            JTable table = new JTable(row, colHeadings);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setDragEnabled(false);
            table.setColumnSelectionAllowed(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(25);
            table.getColumnModel().getColumn(5).setPreferredWidth(50);
            table.getColumnModel().getColumn(6).setPreferredWidth(40);
            table.getColumnModel().getColumn(12).setPreferredWidth(45);
            table.getColumnModel().getColumn(14).setPreferredWidth(45);
            table.getColumnModel().getColumn(15).setPreferredWidth(45);
            table.getColumnModel().getColumn(16).setPreferredWidth(45);
            table.getColumnModel().getColumn(18).setPreferredWidth(45);
            table.getColumnModel().getColumn(19).setPreferredWidth(45);
            table.getColumnModel().getColumn(20).setPreferredWidth(45);
            table.getColumnModel().getColumn(22).setPreferredWidth(45);
            table.getColumnModel().getColumn(23).setPreferredWidth(45);
            table.getColumnModel().getColumn(24).setPreferredWidth(45);
            table.getColumnModel().getColumn(26).setPreferredWidth(45);
            table.getColumnModel().getColumn(27).setPreferredWidth(45);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(table);
            sp.setBounds(25, 100, 1900 - 50, 972 - 200);
            panel.add(sp);
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

    public static void main(String[] args) {
        new ViewAllRecords(new JFrame());
    }
}
