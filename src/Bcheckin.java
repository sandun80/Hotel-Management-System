import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bcheckin extends JFrame implements ActionListener {
    JLabel idlbl,titlbl;
    JTextArea contentarea;
    JTextField idfd;
    JPanel uppan,midpan;
    JButton checckinbtn;

    Bcheckin(){

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

        idlbl = new JLabel("CUSTOMER NIC");
        idlbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(idlbl,gbc);

        idfd = new JTextField();
        idfd.setPreferredSize(new Dimension(400,40));
        idfd.setFont(new Font("Arial",Font.ITALIC,20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(idfd,gbc);

        contentarea = new JTextArea();
        contentarea.setPreferredSize(new Dimension(400,300));
        contentarea.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,20));
        contentarea.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(contentarea,gbc);

        checckinbtn = new JButton("CHECK IN");
        checckinbtn.setFocusable(false);
        checckinbtn.setPreferredSize(new Dimension(300,35));
        checckinbtn.addActionListener(this);
        getRootPane().setDefaultButton(checckinbtn);
        checckinbtn.setBackground(Color.decode("#007d28"));
        checckinbtn.setForeground(Color.WHITE);
        checckinbtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(checckinbtn,gbc);


        setLayout(new BorderLayout());
        this.add(uppan,BorderLayout.NORTH);
        this.add(midpan,BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,800);
        setLocation(300,10);
        setTitle("Euro Hotel");
        setDefaultCloseOperation(addroom.DISPOSE_ON_CLOSE);
    }

    public void checkindata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            String query = "SELECT * FROM bookings WHERE custom_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,idfd.getText());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String query1 = "SELECT custom_name,booking_type,booking_date FROM bookings WHERE custom_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1,idfd.getText());
                ResultSet resultSet1 = statement1.executeQuery();
                StringBuilder result = new StringBuilder();

                if (resultSet1.next()){
                    String name = resultSet1.getString("custom_name");
                    String type = resultSet1.getString("booking_type");
                    String date = resultSet1.getString("booking_date");

                    result.append("CUSTOMER NAME     :").append(name).append("\n");
                    result.append("BOOKING TYPE           :").append(type).append("\n");
                    result.append("BOOKING DATE          :").append(date).append("\n").append("\n");
                    result.append("PAYMENT TO BE COLLECTED ON CHECK OUT").append("\n");
                    result.append("BOOKING IS OK");

                    contentarea.setText(String.valueOf(result));
                }

            }else {
                JOptionPane.showMessageDialog(this,"No Record Found");
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == checckinbtn){
            checkindata();
        }
    }

}
