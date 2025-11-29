import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class updatehall extends JFrame implements ActionListener {
    JLabel uhnumlbl,uhtypelbl,uhstatuslbl,uhchargelbl,titlbl;
    JTextField uhnumfd,uhtypefd,uhstatusfd,uhchargefd;
    JButton uhbtn;
    JPanel uppan,midpan;
    updatehall(){

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

        uhnumlbl = new JLabel("HALL NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(uhnumlbl,gbc);

        uhnumfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(uhnumfd,gbc);

        uhtypelbl = new JLabel("NEW HALL TYPE");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(uhtypelbl,gbc);

        uhtypefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(uhtypefd,gbc);

        uhstatuslbl = new JLabel("NEW HALL STATUS");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(uhstatuslbl,gbc);

        uhstatusfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(uhstatusfd,gbc);

        uhchargelbl = new JLabel("NEW CHARGE PER DAY");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(uhchargelbl,gbc);

        uhchargefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(uhchargefd,gbc);

        uhbtn = new JButton("UPDATE HALL");
        uhbtn.setFocusable(false);
        uhbtn.setPreferredSize(new Dimension(300,35));
        uhbtn.addActionListener(this);
        getRootPane().setDefaultButton(uhbtn);
        uhbtn.setBackground(Color.decode("#007d28"));
        uhbtn.setForeground(Color.WHITE);
        uhbtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(uhbtn,gbc);

        for (JLabel lbl : new JLabel[]{uhnumlbl,uhtypelbl,uhstatuslbl,uhchargelbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{uhnumfd,uhtypefd,uhstatusfd,uhchargefd}){
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

    public void updatehalldata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            boolean ok = false;

            if (!uhnumfd.getText().isEmpty()){
                String query = "SELECT * FROM halls WHERE hall_number = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,uhnumfd.getText());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){
                    if (!uhtypefd.getText().isEmpty()){
                        String query1 = "UPDATE halls SET hall_type = ? WHERE hall_number = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query1);
                        statement1.setString(1,uhtypefd.getText());
                        statement1.setString(2,uhnumfd.getText());
                        statement1.executeUpdate();
                        ok = true;
                    }
                    if (!uhstatusfd.getText().isEmpty()){
                        String query2 = "UPDATE halls SET hall_status = ? WHERE hall_number = ?";
                        PreparedStatement statement2 = connection.prepareStatement(query2);
                        statement2.setString(1,uhstatusfd.getText());
                        statement2.setString(2,uhnumfd.getText());
                        statement2.executeUpdate();
                        ok = true;
                    }
                    if (!uhchargefd.getText().isEmpty()){
                        String query3 = "UPDATE halls SET charge_per_day = ? WHERE hall_number = ?";
                        PreparedStatement statement3 = connection.prepareStatement(query3);
                        Integer charge = Integer.parseInt(uhchargefd.getText());
                        statement3.setInt(1,charge);
                        statement3.setString(2,uhnumfd.getText());
                        statement3.executeUpdate();
                        ok = true;
                    }

                    if (ok){
                        JOptionPane.showMessageDialog(this,"Data updated successfully");
                        for (JTextField fo : new JTextField[]{uhnumfd,uhtypefd,uhstatusfd,uhchargefd}){
                            fo.setText("");
                        }
                    }else {
                        JOptionPane.showMessageDialog(this,"Nothing to be updated");
                    }


                }else {
                    JOptionPane.showMessageDialog(this,"No record found");
                }
            }
            else if(!uhtypefd.getText().isEmpty()){
                String query4 = "UPDATE halls SET charge_per_day = ? WHERE hall_type = ?";
                PreparedStatement statement4 = connection.prepareStatement(query4);
                Integer charge = Integer.parseInt(uhchargefd.getText());
                statement4.setInt(1,charge);
                statement4.setString(2,uhtypefd.getText());
                statement4.executeUpdate();
                JOptionPane.showMessageDialog(this,"Charges Updated Successfully");
                for (JTextField fg : new JTextField[]{uhtypefd,uhchargefd}){
                    fg.setText("");
                }
            }
            else{
                JOptionPane.showMessageDialog(this,"Hall number or type must be entered");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uhbtn){
            updatehalldata();
        }

    }
}
