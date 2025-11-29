import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addroom extends JFrame implements ActionListener {
    JLabel numlbl,typelbl,statuslbl,chargelbl,titlbl;
    JTextField numfd,typefd,statusfd,chargefd;
    JPanel uppan,midpan;
    JButton btn;

    addroom(){

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

        numlbl = new JLabel("ROOM NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(numlbl,gbc);

        numfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(numfd,gbc);

        typelbl = new JLabel("ROOM TYPE");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(typelbl,gbc);

        typefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(typefd,gbc);

        statuslbl = new JLabel("ROOM STATUS");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(statuslbl,gbc);

        statusfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(statusfd,gbc);

        chargelbl = new JLabel("CHARGE PER HOUR");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(chargelbl,gbc);

        chargefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(chargefd,gbc);

        btn = new JButton("ADD ROOM");
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(300,35));
        getRootPane().setDefaultButton(btn);
        btn.addActionListener(this);
        btn.setBackground(Color.decode("#007d28"));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(btn,gbc);

        for (JLabel lbl : new JLabel[]{numlbl,typelbl,statuslbl,chargelbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{numfd,typefd,statusfd,chargefd}){
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

    public void adddata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            boolean empty = false;
            for (JTextField fie : new JTextField[]{numfd,typefd,statusfd,chargefd}){
                if (fie.getText().isEmpty()){
                    empty = true;
                }
            }
            if (!empty){
                String query = "INSERT INTO rooms VALUES (?,?,?,?)";
                Integer charge = Integer.parseInt(chargefd.getText());
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,numfd.getText());
                statement.setString(2,typefd.getText());
                statement.setString(3,statusfd.getText());
                statement.setInt(4,charge);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Data Added Successfully");
                for (JTextField fid : new JTextField[]{numfd,typefd,statusfd,chargefd}){
                    fid.setText("");
                }

            }else {
                JOptionPane.showMessageDialog(this,"Fields can't be empty");
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn){
            adddata();
        }
    }

}
