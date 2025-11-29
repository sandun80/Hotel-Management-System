import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class updateroom extends JFrame implements ActionListener {
    JLabel unumlbl,utypelbl,ustatuslbl,uchargelbl,titlbl;
    JTextField unumfd,utypefd,ustatusfd,uchargefd;
    JButton ubtn;
    JPanel uppan,midpan;
    updateroom(){

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

        unumlbl = new JLabel("ROOM NUMBER");
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpan.add(unumlbl,gbc);

        unumfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpan.add(unumfd,gbc);

        utypelbl = new JLabel("NEW ROOM TYPE");
        gbc.gridx = 0;
        gbc.gridy = 1;
        midpan.add(utypelbl,gbc);

        utypefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        midpan.add(utypefd,gbc);

        ustatuslbl = new JLabel("NEW ROOM STATUS");
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpan.add(ustatuslbl,gbc);

        ustatusfd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpan.add(ustatusfd,gbc);

        uchargelbl = new JLabel("NEW CHARGE PER HOUR");
        gbc.gridx = 0;
        gbc.gridy = 3;
        midpan.add(uchargelbl,gbc);

        uchargefd = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpan.add(uchargefd,gbc);

        ubtn = new JButton("UPDATE ROOM");
        ubtn.setFocusable(false);
        ubtn.setPreferredSize(new Dimension(300,35));
        ubtn.addActionListener(this);
        getRootPane().setDefaultButton(ubtn);
        ubtn.setBackground(Color.decode("#007d28"));
        ubtn.setForeground(Color.WHITE);
        ubtn.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx = 1;
        gbc.gridy = 4;
        midpan.add(ubtn,gbc);

        for (JLabel lbl : new JLabel[]{unumlbl,utypelbl,ustatuslbl,uchargelbl}){
            lbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,25));
        }
        for (JTextField fd : new JTextField[]{unumfd,utypefd,ustatusfd,uchargefd}){
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

    public void updatedata(){
        String url = "jdbc:mysql://localhost:3306/hoteldb";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,user,pass);
            boolean ok = false;

            if (!unumfd.getText().isEmpty()){
                String query = "SELECT * FROM rooms WHERE room_number = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,unumfd.getText());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){
                    if (!utypefd.getText().isEmpty()){
                        String query1 = "UPDATE rooms SET room_type = ? WHERE room_number = ?";
                        PreparedStatement statement1 = connection.prepareStatement(query1);
                        statement1.setString(1,utypefd.getText());
                        statement1.setString(2,unumfd.getText());
                        statement1.executeUpdate();
                        ok = true;
                    }
                    if (!ustatusfd.getText().isEmpty()){
                        String query2 = "UPDATE rooms SET room_status = ? WHERE room_number = ?";
                        PreparedStatement statement2 = connection.prepareStatement(query2);
                        statement2.setString(1,ustatusfd.getText());
                        statement2.setString(2,unumfd.getText());
                        statement2.executeUpdate();
                        ok = true;
                    }
                    if (!uchargefd.getText().isEmpty()){
                        String query3 = "UPDATE rooms SET charge_perhour_Rupees = ? WHERE room_number = ?";
                        PreparedStatement statement3 = connection.prepareStatement(query3);
                        Integer charge = Integer.parseInt(uchargefd.getText());
                        statement3.setInt(1,charge);
                        statement3.setString(2,unumfd.getText());
                        statement3.executeUpdate();
                        ok = true;
                    }

                    if (ok){
                        JOptionPane.showMessageDialog(this,"Data updated successfully");
                        for (JTextField fo : new JTextField[]{unumfd,utypefd,ustatusfd,uchargefd}){
                            fo.setText("");
                        }
                    }else {
                        JOptionPane.showMessageDialog(this,"Nothing to be updated");
                    }


                }else {
                    JOptionPane.showMessageDialog(this,"No record found");
                }
            }
            else if(!utypefd.getText().isEmpty()){
                String query4 = "UPDATE rooms SET charge_perhour_Rupees = ? WHERE room_type = ?";
                PreparedStatement statement4 = connection.prepareStatement(query4);
                Integer charge = Integer.parseInt(uchargefd.getText());
                statement4.setInt(1,charge);
                statement4.setString(2,utypefd.getText());
                statement4.executeUpdate();
               JOptionPane.showMessageDialog(this,"Charges Updated Successfully");
               for (JTextField fg : new JTextField[]{utypefd,uchargefd}){
                   fg.setText("");
               }
            }
            else{
                JOptionPane.showMessageDialog(this,"Room number or type must be entered");
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ubtn){
            updatedata();
        }

    }

}
