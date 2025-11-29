import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Booking extends JFrame implements ActionListener {
    JTextField idfd,namefd,datefd,durationfd,purposefd,hallnumfd;
    JLabel idlbl,namelbl,typelbl,datelbl,durationlbl,titlbl,purposelbl,hallnumlbl;
    JButton bookbtn;
    JTextArea availablearea;
    JComboBox Btypecombo;
    JPanel uppan,midpan;

    Booking(){

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
        gbc.insets = new Insets(0,50,30,10);

        idlbl = new JLabel("CUSTOMER NIC");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(idlbl,gbc);

        idfd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(idfd,gbc);

        namelbl = new JLabel("CUSTOMER NAME");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(namelbl,gbc);

        namefd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(namefd,gbc);

        typelbl = new JLabel("BOOKING TYPE");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(typelbl,gbc);

        String[] typelist = {"HALL","ROOM"};
        Btypecombo = new JComboBox<>(typelist);
        Btypecombo.setPreferredSize(new Dimension(400,40));
        Btypecombo.setFont(new Font("Cambria",Font.PLAIN,20));
        Btypecombo.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(Btypecombo,gbc);

        purposelbl = new JLabel("PURPOSE OF BOOKING");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(purposelbl,gbc);

        purposefd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(purposefd,gbc);

        datelbl = new JLabel("BOOKING DATE");
        gbc.gridx = 0;
        gbc.gridy = 4;
        midpan.add(datelbl,gbc);

        datefd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(datefd,gbc);

        durationlbl = new JLabel("DURATION");
        gbc.gridx = 0;
        gbc.gridy = 5;
        midpan.add(durationlbl,gbc);

        durationfd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        midpan.add(durationfd,gbc);

        hallnumlbl = new JLabel("HALL OR ROOM NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 6;
        midpan.add(hallnumlbl,gbc);

        hallnumfd = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 6;
        midpan.add(hallnumfd,gbc);

        availablearea = new JTextArea();
        availablearea.setPreferredSize(new Dimension(130,100));
        availablearea.setEditable(false);

        bookbtn = new JButton("MAKE THE BOOKING");
        bookbtn.setFocusable(false);
        bookbtn.setPreferredSize(new Dimension(300,35));
        bookbtn.addActionListener(this);
        getRootPane().setDefaultButton(bookbtn);
        bookbtn.setBackground(Color.decode("#007d28"));
        bookbtn.setForeground(Color.WHITE);
        bookbtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 7;
        midpan.add(bookbtn,gbc);

        for (JLabel lbl : new JLabel[]{idlbl,namelbl,typelbl,datelbl,durationlbl,purposelbl,hallnumlbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{idfd,namefd,datefd,durationfd,purposefd,hallnumfd}){
            fd.setPreferredSize(new Dimension(400,40));
            fd.setFont(new Font("Arial",Font.ITALIC,20));
        }

        setLayout(new BorderLayout());
        this.add(uppan,BorderLayout.NORTH);
        this.add(availablearea,BorderLayout.EAST);
        this.add(midpan,BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,800);
        setLocation(300,10);
        setTitle("Euro Hotel");
        setDefaultCloseOperation(addroom.DISPOSE_ON_CLOSE);
    }

    public void bookdata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           boolean empty = false;
           Connection connection = DriverManager.getConnection(url,user,pass);
           for (JTextField fd : new JTextField[]{idfd,namefd,datefd,durationfd,purposefd,hallnumfd}){
               String typefd = (String) Btypecombo.getSelectedItem();
               if (fd.getText().isEmpty() || typefd.isEmpty()){
                   empty = true;
               }
           }
           if (!empty){

               String query3 = "UPDATE rooms SET room_status = 'Booked' WHERE room_number = ?";
               String query4 = "UPDATE halls SET hall_status = 'Booked' WHERE hall_number = ?";

               String type = (String) Btypecombo.getSelectedItem();

               if (type == "ROOM"){
                   String query = "INSERT INTO bookings VALUES(?,?,?,?,?,?,?)";
                   String query2 = "INSERT INTO customers VALUES(?,?,?,?,?,?)";
                   String queryC = "SELECT * FROM rooms WHERE room_number = ?";
                   PreparedStatement statementC = connection.prepareStatement(queryC);
                   statementC.setString(1,hallnumfd.getText());
                   ResultSet resultC = statementC.executeQuery();
                   if (resultC.next()){
                       PreparedStatement statement2 = connection.prepareStatement(query3);
                       statement2.setString(1,hallnumfd.getText());
                       statement2.executeUpdate();

                       PreparedStatement statement = connection.prepareStatement(query);
                       PreparedStatement statement1 = connection.prepareStatement(query2);
                       statement.setString(1,idfd.getText());
                       statement.setString(2,namefd.getText());
                       statement.setString(3,type);
                       statement.setString(4,purposefd.getText());
                       statement.setDate(5,Date.valueOf(datefd.getText()));
                       statement.setInt(6,Integer.valueOf(durationfd.getText()));
                       statement.setString(7,hallnumfd.getText());

                       statement1.setString(1,idfd.getText());
                       statement1.setString(2,namefd.getText());
                       statement1.setString(3,type);
                       statement1.setString(4,purposefd.getText());
                       statement1.setDate(5,Date.valueOf(datefd.getText()));
                       statement1.setString(6,hallnumfd.getText());

                       JOptionPane.showMessageDialog(this,"Booking is Successful");
                       for (JTextField fid : new JTextField[]{idfd,namefd,purposefd,datefd,durationfd,hallnumfd}){
                           fid.setText("");
                       }

                       statement.executeUpdate();
                       statement1.executeUpdate();
                   }else {
                       JOptionPane.showMessageDialog(this,"No Room Available From Room Number Entered");
                   }

               }
               if (type == "HALL"){
                   String query = "INSERT INTO bookings VALUES(?,?,?,?,?,?,?)";
                   String query2 = "INSERT INTO customers VALUES(?,?,?,?,?,?)";
                   String queryD = "SELECT * FROM halls WHERE hall_number = ?";
                   PreparedStatement statementD = connection.prepareStatement(queryD);
                   statementD.setString(1,hallnumfd.getText());
                   ResultSet resultSetD = statementD.executeQuery();
                   if (resultSetD.next()){
                       PreparedStatement statement3 = connection.prepareStatement(query4);
                       statement3.setString(1,hallnumfd.getText());
                       statement3.executeUpdate();

                       PreparedStatement statement = connection.prepareStatement(query);
                       PreparedStatement statement1 = connection.prepareStatement(query2);
                       statement.setString(1,idfd.getText());
                       statement.setString(2,namefd.getText());
                       statement.setString(3,type);
                       statement.setString(4,purposefd.getText());
                       statement.setDate(5,Date.valueOf(datefd.getText()));
                       statement.setInt(6,Integer.valueOf(durationfd.getText()));
                       statement.setString(7,hallnumfd.getText());
                       statement.executeUpdate();

                       statement1.setString(1,idfd.getText());
                       statement1.setString(2,namefd.getText());
                       statement1.setString(3,type);
                       statement1.setString(4,purposefd.getText());
                       statement1.setDate(5,Date.valueOf(datefd.getText()));
                       statement1.setString(6,hallnumfd.getText());
                       statement1.executeUpdate();

                       JOptionPane.showMessageDialog(this,"Booking is Successful");
                       for (JTextField fid : new JTextField[]{idfd,namefd,purposefd,datefd,durationfd,hallnumfd}){
                           fid.setText("");
                       }
                   }
                   else {
                       JOptionPane.showMessageDialog(this,"No Hall Available From Hall Number Entered");
                   }

               }

           }else {
               JOptionPane.showMessageDialog(this,"Fields Can't Be Empty");
           }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void avilabledata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection1 = DriverManager.getConnection(url,user,pass);
            String ava = (String) Btypecombo.getSelectedItem();
             if (ava == "ROOM"){
                 String queryV = "SELECT room_number FROM rooms WHERE room_status != 'BOOKED' ";
                 Statement statement = connection1.createStatement();
                 ResultSet resultSet = statement.executeQuery(queryV);
                 StringBuilder results = new StringBuilder();
                 results.append("AVAILABLE ROOMS").append("\n").append("\n");

                 while (resultSet.next()){
                     results.append(resultSet.getString(1)).append("\n");
                 }
                 availablearea.setText(String.valueOf(results));
             }
             else if (ava == "HALL"){
                 String queryD = "SELECT hall_number FROM halls WHERE hall_status !='BOOKED' ";
                 Statement statement1 = connection1.createStatement();
                 ResultSet resultSet1 = statement1.executeQuery(queryD);
                 StringBuilder results1 = new StringBuilder();
                 results1.append("AVAILABLE HALLS").append("\n").append("\n");

                 while (resultSet1.next()){
                     results1.append(resultSet1.getString(1)).append("\n");
                 }
                 availablearea.setText(String.valueOf(results1));
             }


        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == bookbtn){
            bookdata();
        }
        if (e.getSource() == Btypecombo){
            avilabledata();
        }
    }

}