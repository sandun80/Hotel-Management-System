import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Rcheckin extends JFrame implements ActionListener {
    JLabel idlbl,titlbl;
    JTextField idfd;
    JTextArea detarea;
    JPanel uppan,midppan;
    JButton checkbtn;

    Rcheckin(){
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

        midppan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,50,50,100);

        idlbl = new JLabel("CUSTOMER NAME");
        idlbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        midppan.add(idlbl,gbc);

        idfd = new JTextField();
        idfd.setPreferredSize(new Dimension(400,40));
        idfd.setFont(new Font("Arial",Font.ITALIC,20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        midppan.add(idfd,gbc);

        detarea = new JTextArea();
        detarea.setPreferredSize(new Dimension(400,300));
        detarea.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,20));
        detarea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midppan.add(detarea,gbc);

        checkbtn = new JButton("CHECK IN");
        checkbtn.setFocusable(false);
        checkbtn.setPreferredSize(new Dimension(300,35));
        checkbtn.addActionListener(this);
        getRootPane().setDefaultButton(checkbtn);
        checkbtn.setBackground(Color.decode("#007d28"));
        checkbtn.setForeground(Color.WHITE);
        checkbtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        midppan.add(checkbtn,gbc);

        setVisible(true);
        setSize(1000,800);
        setLocation(300,10);
        setTitle("Euro Hotel");
        setDefaultCloseOperation(addroom.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        this.add(uppan,BorderLayout.NORTH);
        this.add(midppan,BorderLayout.CENTER);
    }

    public void resercheckin(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            String query = "SELECT * FROM reservations WHERE Customer_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,idfd.getText());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String query2 = "SELECT customer_id,phone_number,reservation_type,reservation_date FROM reservations WHERE customer_name = ?";
                PreparedStatement statement1 = connection.prepareStatement(query2);
                statement1.setString(1,idfd.getText());
                ResultSet resultSet1 = statement1.executeQuery();

                if (resultSet1.next()){
                    String name = resultSet1.getString("Customer_id");
                    String number = resultSet1.getString("Phone_number");
                    String type = resultSet1.getString("Reservation_type");
                    String date = resultSet1.getString("Reservation_date");

                    StringBuilder result = new StringBuilder();
                    result.append("Customer ID                           :").append(name).append("\n");
                    result.append("Customer Phone Number    :").append(number).append("\n");
                    result.append("Reservation Type                   :").append(type).append("\n");
                    result.append("Reservation Date                   :").append(date).append("\n");
                    result.append("RESERVATION IS OK");

                    detarea.setText(String.valueOf(result));
                }
                else {
                    JOptionPane.showMessageDialog(this,"Problem With the Recorded Data");
                }

            }
            else {
                JOptionPane.showMessageDialog(this,"No Record Found");
            }


        }catch (Exception e){
            System.out.println(e);
        }

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkbtn){
            resercheckin();
        }
    }

    public static void main(String[] args) {
        Rcheckin gg = new Rcheckin();
    }
}
