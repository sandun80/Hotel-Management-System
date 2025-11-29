import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addhall extends JFrame implements ActionListener {
    JLabel hnumlbl,htypelbl,hstslbl,hchargelbl,titlbl;
    JTextField hnumfd,htypefd,hstsfd,hchargefd;
    JPanel uppan,midpan;
    JButton btn;

    addhall(){

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

        hnumlbl = new JLabel("HALL NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(hnumlbl,gbc);

        hnumfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(hnumfd,gbc);

        htypelbl = new JLabel("HALL TYPE");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(htypelbl,gbc);

        htypefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(htypefd,gbc);

        hstslbl = new JLabel("HALL STATUS");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(hstslbl,gbc);

        hstsfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(hstsfd,gbc);

        hchargelbl = new JLabel("CHARGE PER DAY");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(hchargelbl,gbc);

        hchargefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(hchargefd,gbc);

        btn = new JButton("ADD HALL");
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

        for (JLabel lbl : new JLabel[]{hnumlbl,htypelbl,hstslbl,hchargelbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{hnumfd,htypefd,hstsfd,hchargefd}){
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

    public void addhalldata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            boolean empty = false;
            for (JTextField fie : new JTextField[]{hnumfd,htypefd,hstsfd,hchargefd}){
                if (fie.getText().isEmpty()){
                    empty = true;
                }
            }
            if (!empty){
                String query = "INSERT INTO halls VALUES (?,?,?,?)";
                Integer charge = Integer.parseInt(hchargefd.getText());
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,hnumfd.getText());
                statement.setString(2,htypefd.getText());
                statement.setString(3,hstsfd.getText());
                statement.setInt(4,charge);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Data Added Successfully");
                for (JTextField fid : new JTextField[]{hnumfd,htypefd,hstsfd,hchargefd}){
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
            addhalldata();
        }
    }
}
