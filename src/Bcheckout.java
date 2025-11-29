import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bcheckout extends JFrame implements ActionListener {
        JLabel checkolbl;
        JLabel titlbl;
        JLabel amountlbl1;
        JLabel amountlbl2;
        JTextField checkofd;
        JTextArea coarea;
        JButton button;
        JPanel uppan,midpan;

    Bcheckout(){

        uppan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uppan.setPreferredSize(new Dimension(100,100));
        uppan.setBackground(Color.decode("#001f52"));
        titlbl = new JLabel("EURO HOTEL COLOMBO");
        titlbl.setForeground(Color.WHITE);
        ImageIcon icon = new ImageIcon("logo2.png");
        Image icc = icon.getImage();
        this.setIconImage(icc);
        JLabel iconlbl = new JLabel(icon);
        titlbl.setFont(new Font("Impact",Font.PLAIN,30));
        uppan.add(iconlbl);
        uppan.add(titlbl);

        midpan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,50,50,100);

        checkolbl = new JLabel("CUSTOMER NIC");
        checkolbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(checkolbl,gbc);

        checkofd = new JTextField();
        checkofd.setPreferredSize(new Dimension(400,40));
        checkofd.setFont(new Font("Arial",Font.ITALIC,20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(checkofd,gbc);

        amountlbl1 = new JLabel("TOTAL AMOUNT TO PAY");
        amountlbl1.setFont(new Font("Arial",Font.BOLD,40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(amountlbl1,gbc);

        amountlbl2 = new JLabel(" ");
        amountlbl2.setFont(new Font("Arial",Font.BOLD,50));
        amountlbl2.setPreferredSize(new Dimension(290,40));
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(amountlbl2,gbc);

        button = new JButton("CHECK OUT");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(300,35));
        button.addActionListener(this);
        getRootPane().setDefaultButton(button);
        button.setBackground(Color.decode("#007d28"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(button,gbc);

        setLayout(new BorderLayout());
        this.add(uppan,BorderLayout.NORTH);
        this.add(midpan,BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,800);
        setLocation(300,10);
        setTitle("Euro Hotel");
        setDefaultCloseOperation(addroom.DISPOSE_ON_CLOSE);
    }

    public void checkoutdata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM bookings WHERE custom_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, checkofd.getText());
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                String query1 = "SELECT booking_type, duration, room_or_hall_number FROM bookings WHERE custom_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1, checkofd.getText());
                ResultSet resultSet = statement1.executeQuery();

                if (resultSet.next()) {
                    String Btype = resultSet.getString("booking_type");
                    int Bduration = resultSet.getInt("duration");
                    String HorRnumber = resultSet.getString("room_or_hall_number");

                    if (Btype.equals("ROOM")) {
                        try {
                            int charge = 0;
                            String query2 = "SELECT room_type,charge_perhour_Rupees FROM rooms WHERE room_number = ?";
                            PreparedStatement statement2 = connection.prepareStatement(query2);
                            statement2.setString(1, HorRnumber);
                            ResultSet resultSet1 = statement2.executeQuery();

                            if (resultSet1.next()) {
                                String roomtype = resultSet1.getString("room_type");
                                int chargephr = resultSet1.getInt("charge_perhour_Rupees");
                                if (roomtype.equals("VIP")) {
                                    charge = chargephr * Bduration;
                                } else if (roomtype.equals("FAMILY")) {
                                    charge = chargephr * Bduration;
                                } else if (roomtype.equals("ECONOMY")) {
                                    charge = chargephr * Bduration;
                                }

                                // Insert into income table
                                String queryX = "INSERT INTO income VALUES (?, ?)";
                                PreparedStatement statementX = connection.prepareStatement(queryX);
                                statementX.setString(1, "ROOM");
                                statementX.setInt(2, charge);
                                statementX.executeUpdate();
                                statementX.close();  // Close the insert statement

                                // Update rooms table
                                String queryY = "UPDATE rooms SET room_status = ? WHERE room_number = ?";
                                PreparedStatement statementY = connection.prepareStatement(queryY);
                                statementY.setString(1, "Available");
                                statementY.setString(2, HorRnumber);
                                statementY.executeUpdate();
                                statementY.close();

                                // Delete from bookings table
                                String queryZ = "DELETE FROM bookings WHERE custom_id = ?";
                                PreparedStatement statementZ = connection.prepareStatement(queryZ);
                                statementZ.setString(1, checkofd.getText());
                                statementZ.executeUpdate();
                                statementZ.close();

                                amountlbl2.setText(String.valueOf(charge));
                            }

                            resultSet1.close();
                            statement2.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    if (Btype.equals("HALL")){

                        try {
                            int Hcharge = 0;
                            String query2 = "SELECT charge_per_day FROM halls WHERE hall_number = ?";
                            PreparedStatement statement2 = connection.prepareStatement(query2);
                            statement2.setString(1, HorRnumber);
                            ResultSet resultSet1 = statement2.executeQuery();

                            if (resultSet1.next()) {
                                int chargepday = resultSet1.getInt("charge_per_day");
                                Hcharge = chargepday;

                                // Insert into income table
                                String queryX = "INSERT INTO income VALUES (?, ?)";
                                PreparedStatement statementX = connection.prepareStatement(queryX);
                                statementX.setString(1, "HALL");
                                statementX.setInt(2, Hcharge);
                                statementX.executeUpdate();
                                statementX.close();  // Close the insert statement

                                // Update rooms table
                                String queryY = "UPDATE halls SET hall_status = ? WHERE hall_number = ?";
                                PreparedStatement statementY = connection.prepareStatement(queryY);
                                statementY.setString(1, "Available");
                                statementY.setString(2, HorRnumber);
                                statementY.executeUpdate();
                                statementY.close();

                                // Delete from bookings table
                                String queryZ = "DELETE FROM bookings WHERE custom_id = ?";
                                PreparedStatement statementZ = connection.prepareStatement(queryZ);
                                statementZ.setString(1, checkofd.getText());
                                statementZ.executeUpdate();
                                statementZ.close();

                                amountlbl2.setText(String.valueOf(Hcharge));
                            }

                            resultSet1.close();
                            statement2.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "SQL Error Occurred");
                }

                resultSet.close();
                statement1.close();
            } else {
                JOptionPane.showMessageDialog(this, "Problem With the NIC Entered");
            }

            res.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            checkoutdata();
        }
    }

    public static void main(String[] args) {
        Bcheckout og = new Bcheckout();
    }
}
