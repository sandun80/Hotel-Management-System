import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class reserve extends JFrame implements ActionListener {
    JTextField Cid,Cname,Cmail,Cnum,Cdate;
    JLabel idlbl,namelbl,maillbl,numlbl,typelbl,datelbl,personlbl,titlbl;
    JButton reservebtn;
    JComboBox typecombo,personcombo;
    JPanel uppan,midpan;

    reserve(){

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
        gbc.insets = new Insets(0,50,30,100);

        idlbl = new JLabel("CUSTOMER ID");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(idlbl,gbc);

        Cid = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(Cid,gbc);

        namelbl = new JLabel("CUSTOMER NAME");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(namelbl,gbc);

        Cname = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(Cname,gbc);

        maillbl = new JLabel("CUSTOMER EMAIL ADDRESS");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(maillbl,gbc);

        Cmail = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(Cmail,gbc);

        numlbl = new JLabel("CUSTOMER PHONE NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(numlbl,gbc);

        Cnum = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(Cnum,gbc);

        typelbl = new JLabel("RESERVATION TYPE");
        gbc.gridx = 0;
        gbc.gridy = 4;
        midpan.add(typelbl,gbc);

        String[] list = {"Weekdays Hightea Buffet","Weekend Hightea Buffet","Lunch Buffet","Dinner Buffet"};
        typecombo = new JComboBox<>(list);
        typecombo.setPreferredSize(new Dimension(400,40));
        typecombo.setFont(new Font("Cambria",Font.PLAIN,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(typecombo,gbc);

        datelbl = new JLabel("RESERVATION DATE");
        gbc.gridx = 0;
        gbc.gridy = 5;
        midpan.add(datelbl,gbc);

        Cdate = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        midpan.add(Cdate,gbc);

        personlbl = new JLabel("NUMBER OF PERSONS");
        gbc.gridx = 0;
        gbc.gridy = 6;
        midpan.add(personlbl,gbc);

        Integer[] persons = {2,4,6,8,10};
        personcombo = new JComboBox<>(persons);
        personcombo.setPreferredSize(new Dimension(400,40));
        personcombo.setFont(new Font("Cambria",Font.PLAIN,20));
        gbc.gridx = 1;
        gbc.gridy = 6;
        midpan.add(personcombo,gbc);

        reservebtn = new JButton("RESERVE");
        reservebtn.setFocusable(false);
        reservebtn.setPreferredSize(new Dimension(300,35));
        reservebtn.addActionListener(this);
        getRootPane().setDefaultButton(reservebtn);
        reservebtn.setBackground(Color.decode("#007d28"));
        reservebtn.setForeground(Color.WHITE);
        reservebtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 7;
        midpan.add(reservebtn,gbc);

        for (JLabel lbl : new JLabel[]{idlbl,namelbl,maillbl,numlbl,typelbl,datelbl,personlbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{Cid,Cname,Cmail,Cnum,Cdate}){
            fd.setPreferredSize(new Dimension(400,40));
            fd.setFont(new Font("Arial",Font.ITALIC,20));
        }

        setLayout(new BorderLayout());
        this.add(uppan,BorderLayout.NORTH);
        this.add(midpan,BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,800);
        setLocation(300,10);
        setTitle("Euro Hotel");
        setDefaultCloseOperation(addroom.DISPOSE_ON_CLOSE);
    }

    public void reserdata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            boolean empty = false;
            String type = (String) typecombo.getSelectedItem();
            Integer count = (Integer) personcombo.getSelectedItem();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);

            for (JTextField fd : new JTextField[]{Cid,Cname,Cmail,Cnum,Cdate}){
                if (fd.getText().isEmpty() || type.isEmpty() || count.equals(null)){
                    empty = true;
                }
            }
            if (!empty){

                String query = "INSERT INTO reservations VALUES (?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,Cid.getText());
                statement.setString(2,Cname.getText());
                statement.setString(3,Cmail.getText());
                statement.setString(4,Cnum.getText());
                statement.setString(5, (String) typecombo.getSelectedItem());
                statement.setDate(6, Date.valueOf(Cdate.getText()));
                statement.setInt(7, (Integer) personcombo.getSelectedItem());
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Reservation is Done");
                for (JTextField fds : new JTextField[]{Cid,Cname,Cmail,Cnum,Cdate}){
                    fds.setText(" ");
                }
            }
            else {
                JOptionPane.showMessageDialog(this,"Fields Can't Be Empty");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void income(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            int incomeval = 0;
            String query = "INSERT INTO income VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,(String) typecombo.getSelectedItem());
            for (int i =0; i<=10; i++){
                if (i == (Integer) personcombo.getSelectedItem()){
                    if ((String) typecombo.getSelectedItem() == "Weekdays Hightea Buffet" || (String) typecombo.getSelectedItem() == "Weekend Hightea Buffet" ){
                        incomeval = 3000*i;
                        break;
                    }
                    else {
                        incomeval = 4000*i;
                        break;
                    }
                }
            }
            statement.setInt(2,incomeval);
            statement.executeUpdate();

        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            reserdata();
            income();
    }
}
