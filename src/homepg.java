import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class homepg extends JFrame implements ActionListener, MouseListener {
        JPanel sidepan,midpan,cardpanel;
        JLabel namelbl;
        JButton roombtn,hallbtn,reserbtn,bookbtn,dashbtn,custombtn;
        JButton addroom,addhall,uphall,uproom,makereser,checkin,makebook,bookcheckin,bookcheckout;
        JPanel roompan,hallpan,reservepan,bookpan,dashpan,custompan;
        JLabel incomelbl,intitlbl;
        JLabel infocuslbl;
        JTextField infofd;
        JTextArea infoarea;
        JButton infobtn;
        CardLayout card;
    homepg(){
        card = new CardLayout();
        cardpanel = new JPanel(card);
        ImageIcon icon = new ImageIcon("logo2.png");
        Image icc = icon.getImage();
        this.setIconImage(icc);

        sidepan = new JPanel();
        sidepan.setLayout(new BoxLayout(sidepan,BoxLayout.Y_AXIS));
        sidepan.setBackground(Color.decode("#000369"));
        sidepan.setPreferredSize(new Dimension(300,200));

        roombtn = new JButton("ROOMS");
        roombtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(roombtn);

        hallbtn = new JButton("HALLS");
        hallbtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(hallbtn);

        reserbtn = new JButton("RESERVATIONS");
        reserbtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(reserbtn);

        bookbtn = new JButton("BOOKINGS");
        bookbtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(bookbtn);

        dashbtn = new JButton("DASHBOARD");
        dashbtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(dashbtn);

        custombtn = new JButton("CUSTOMER INFORMATION");
        custombtn.setFocusable(false);
        sidepan.add(Box.createVerticalStrut(30));
        sidepan.add(custombtn);

        namelbl = new JLabel("                      EURO HOTEL");
        namelbl.setFont(new Font("Freestyle Script",Font.BOLD,20));
        JLabel namelbl2 = new JLabel("                      COLOMBO 7");
        JLabel namelbl3 = new JLabel("                   +94 11 345 5423");
        namelbl3.setFont(new Font("Freestyle Script",Font.BOLD,20));
        namelbl3.setForeground(Color.WHITE);
        namelbl2.setFont(new Font("Freestyle Script",Font.BOLD,20));
        namelbl2.setForeground(Color.WHITE);
        sidepan.add(Box.createVerticalStrut(200));
        namelbl.setForeground(Color.WHITE);
        sidepan.add(namelbl);
        sidepan.add(namelbl2);
        sidepan.add(namelbl3);

        for (JButton btn : new JButton[]{roombtn,hallbtn,reserbtn,bookbtn,dashbtn,custombtn}){
            btn.addActionListener(this);
            btn.addMouseListener(this);
            btn.setMaximumSize(new Dimension(300,60));
            btn.setFont(new Font("Cascadia Mono SemiBold",Font.BOLD,20));
            btn.setForeground(Color.WHITE);
            btn.setBackground(Color.decode("#000369"));
            btn.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        }

        midpan = new JPanel();
        midpan.setBackground(Color.GRAY);
        cardpanel.add(midpan);

        roompan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,50,0);
        cardpanel.add(roompan,"room");
        addroom = new JButton("ADD ROOM");
        addroom.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        roompan.add(addroom,gbc);
        uproom = new JButton("UPDATE ROOM");
        uproom.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        roompan.add(uproom,gbc);

        for (JButton but : new JButton[]{addroom,uproom}){
            but.setPreferredSize(new Dimension(400,100));
            but.addActionListener(this);
            but.setForeground(Color.WHITE);
            but.setBackground(Color.decode("#003e6e"));
            but.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,20));
        }

        hallpan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(0,0,50,0);
        cardpanel.add(hallpan,"hall");
        addhall = new JButton("ADD HALL");
        addhall.setFocusable(false);
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        hallpan.add(addhall,gbc1);
        uphall = new JButton("UPDATE HALL");
        uphall.setFocusable(false);
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        hallpan.add(uphall,gbc1);

        for (JButton but : new JButton[]{addhall,uphall}){
            but.setPreferredSize(new Dimension(400,100));
            but.addActionListener(this);
            but.setForeground(Color.WHITE);
            but.setBackground(Color.decode("#003e6e"));
            but.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,20));
        }

        reservepan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(0,0,50,0);
        reservepan.setBackground(Color.BLUE);
        cardpanel.add(reservepan,"reserve");

        makereser = new JButton("MAKE A RESERVATION");
        makereser.setFocusable(false);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        reservepan.add(makereser,gbc2);
        checkin = new JButton("CHECK IN");
        checkin.setFocusable(false);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        reservepan.add(checkin,gbc2);

        for (JButton but : new JButton[]{makereser,checkin}){
            but.setPreferredSize(new Dimension(400,100));
            but.addActionListener(this);
            but.setForeground(Color.WHITE);
            but.setBackground(Color.decode("#003e6e"));
            but.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,20));
        }

        bookpan = new JPanel(new GridBagLayout());
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(0,0,50,0);
        cardpanel.add(bookpan,"book");

        makebook = new JButton("MAKE A BOOKING");
        makebook.setFocusable(false);
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        bookpan.add(makebook,gbc3);
        bookcheckin = new JButton("CHECK IN");
        bookcheckin.setFocusable(false);
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        bookpan.add(bookcheckin,gbc3);
        bookcheckout = new JButton("CHECK OUT");
        bookcheckout.setFocusable(false);
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        bookpan.add(bookcheckout,gbc3);

        for (JButton but : new JButton[]{makebook,bookcheckin,bookcheckout}){
            but.setPreferredSize(new Dimension(400,100));
            but.addActionListener(this);
            but.setForeground(Color.WHITE);
            but.setBackground(Color.decode("#003e6e"));
            but.setFont(new Font("Bahnschrift SemiBold",Font.PLAIN,20));
        }

        dashpan = new JPanel(new GridBagLayout());
        GridBagConstraints gbcd = new GridBagConstraints();
        gbcd.insets = new Insets(0,0,70,0);

        dashpan.setBackground(Color.BLACK);
        intitlbl = new JLabel("TOTAL INCOME");
        intitlbl.setFont(new Font("Arial Black",Font.PLAIN,60));
        gbcd.gridx = 0;
        gbcd.gridy = 0;
        dashpan.add(intitlbl,gbcd);

        incomelbl = new JLabel("000 000");
        incomelbl.setFont(new Font("Franklin Gothic Demi Cond",Font.PLAIN,70));
        gbcd.gridx = 0;
        gbcd.gridy = 1;
        dashpan.add(incomelbl,gbcd);
        cardpanel.add(dashpan,"dash");

        custompan = new JPanel(new GridBagLayout());
        GridBagConstraints gbv = new GridBagConstraints();
        gbv.insets = new Insets(0,0,50,50);

        infocuslbl = new JLabel("CUSTOMER NAME");
        infocuslbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        gbv.gridx = 0;
        gbv.gridy = 0;
        custompan.add(infocuslbl,gbv);

        infofd = new JTextField();
        infofd.setPreferredSize(new Dimension(400,40));
        infofd.setFont(new Font("Arial",Font.ITALIC,20));
        gbv.gridx = 1;
        gbv.gridy = 0;
        custompan.add(infofd,gbv);

        infoarea = new JTextArea();
        infoarea.setPreferredSize(new Dimension(400,300));
        infoarea.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,20));
        infoarea.setEditable(false);
        gbv.gridx = 1;
        gbv.gridy = 1;
        custompan.add(infoarea,gbv);

        infobtn = new JButton("GET INFORMATION");
        infobtn.setFocusable(false);
        infobtn.setPreferredSize(new Dimension(300,35));
        infobtn.addActionListener(this);
        getRootPane().setDefaultButton(infobtn);
        infobtn.setBackground(Color.decode("#007d28"));
        infobtn.setForeground(Color.WHITE);
        infobtn.setFont(new Font("Arial",Font.BOLD,20));
        gbv.gridx = 1;
        gbv.gridy = 3;
        custompan.add(infobtn,gbv);



        cardpanel.add(custompan,"custom");

       for (JPanel pan : new JPanel[]{roompan,hallpan,reservepan,bookpan,dashpan,custompan}){
            pan.setBackground(Color.decode("#c4c4c4"));
        }

        setLayout(new BorderLayout());
        this.add(sidepan,BorderLayout.WEST);
        this.add(cardpanel,BorderLayout.CENTER);

    }

    public void custominfo(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            String query = "SELECT * FROM customers WHERE custom_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,infofd.getText());
            ResultSet resp = statement.executeQuery();

            if (resp.next()){
                String query1 = "SELECT * FROM customers WHERE custom_name = ?";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setString(1,infofd.getText());
                ResultSet resultSet = statement1.executeQuery();

                if (resultSet.next()){
                    String id = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String type = resultSet.getString(3);
                    String purpose = resultSet.getString(4);
                    String date = resultSet.getString(5);
                    String HRnumber = resultSet.getString(6);

                    StringBuilder build = new StringBuilder();
                    build.append("CUSTOMER ID     :").append(id).append("\n");
                    build.append("CUSTOMER NAME     : ").append(name).append("\n");
                    build.append("RESERVATION TYPE     : ").append(type).append("\n");
                    build.append("RESERVATION PURPOSE     : ").append(purpose).append("\n");
                    build.append("RESERVATION DATE     : ").append(date).append("\n");
                    build.append("HALL OR ROOM NUMBER     : ").append(HRnumber).append("\n");

                    infoarea.setText(String.valueOf(build));

                }

            }else {
                JOptionPane.showMessageDialog(this,"No Record Found");
            }



        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void dashmethod() {
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try {
            int income;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            String query = "SELECT SUM(Value) AS total_income FROM income";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                income = resultSet.getInt("total_income");
                incomelbl.setText(String.valueOf(income + " LKR"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == roombtn){
            card.show(cardpanel,"room");
        }
        if (e.getSource() == hallbtn){
            card.show(cardpanel,"hall");
        }
        if (e.getSource() == reserbtn){
            card.show(cardpanel,"reserve");
        }
        if (e.getSource() == bookbtn){
            card.show(cardpanel,"book");
        }
        if (e.getSource() == dashbtn){
            card.show(cardpanel,"dash");
            dashmethod();
        }
        if (e.getSource() == custombtn){
            card.show(cardpanel,"custom");
        }
        if (e.getSource() == infobtn){
            custominfo();
        }
        if (e.getSource() == addroom){
            addroom ss = new addroom();
        }
        if (e.getSource() == uproom){
            updateroom bb = new updateroom();
        }
        if (e.getSource() == addhall){
            addhall cc = new addhall();
        }
        if (e.getSource() == uphall){
            updatehall dd = new updatehall();
        }
        if (e.getSource() == makereser){
            reserve bbm = new reserve();
        }
        if (e.getSource() == checkin){
            Rcheckin gg = new Rcheckin();
        }
        if (e.getSource() == makebook){
            Booking cv = new Booking();
        }
        if (e.getSource() == bookcheckin){
            Bcheckin og = new Bcheckin();
        }
        if (e.getSource() == bookcheckout){
            Bcheckout gg = new Bcheckout();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton sourse = (JButton) e.getSource();
        sourse.setBackground(Color.decode("#639fff"));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton sourse2 = (JButton) e.getSource();
        sourse2.setBackground(Color.decode("#000369"));
    }
}
