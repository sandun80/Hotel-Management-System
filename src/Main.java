import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



class frame  extends JFrame implements ActionListener {
    JPanel uppnl,midpnl;
    JLabel titlbl,userlbl,passlbl;
    JTextField userfd,passfd;
    JButton logbtn;
    frame(){
        uppnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uppnl.setBackground(Color.decode("#8a5a00"));
        uppnl.setPreferredSize(new Dimension(100,100));
        titlbl = new JLabel("Welcome To Euro Hotel");
        titlbl.setForeground(Color.WHITE);
        titlbl.setFont(new Font("Franklin Gothic Medium Cond",Font.PLAIN,30));
        ImageIcon icon = new ImageIcon("logo2.png");
        Image icc = icon.getImage();
        this.setIconImage(icc);
        JLabel iconlbl = new JLabel(icon);
        uppnl.add(iconlbl);
        uppnl.add(titlbl);

        midpnl = new JPanel(new GridBagLayout());
        midpnl.setBackground(Color.decode("#dbdbdb"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,50,20,50);

        userlbl = new JLabel("USERNAME");
        userlbl.setFont(new Font("Arial",Font.BOLD,17));
        gbc.gridx = 0;
        gbc.gridy = 0;
        midpnl.add(userlbl,gbc);

        userfd = new JTextField(20);
        userfd.setPreferredSize(new Dimension(200,30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        midpnl.add(userfd,gbc);

        passlbl = new JLabel("PASSWORD");
        passlbl.setFont(new Font("Arial",Font.BOLD,17));
        gbc.gridx = 0;
        gbc.gridy = 2;
        midpnl.add(passlbl,gbc);

        passfd = new JTextField(20);
        passfd.setPreferredSize(new Dimension(200,30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        midpnl.add(passfd,gbc);

        logbtn = new JButton("LOG IN");
        logbtn.setFocusable(false);
        logbtn.addActionListener(this);
        logbtn.setForeground(Color.WHITE);
        logbtn.setPreferredSize(new Dimension(100,30));
        logbtn.setBackground(Color.decode("#002445"));
        getRootPane().setDefaultButton(logbtn);
        gbc.gridx = 1;
        gbc.gridy = 3;
        midpnl.add(logbtn,gbc);


        setLayout(new BorderLayout());
        this.add(uppnl,BorderLayout.NORTH);
        this.add(midpnl,BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == logbtn){
            if (userfd.getText().equals("Admin") && passfd.getText().equals("Euro@7hotel")){
                homepg bb = new homepg();
                bb.setVisible(true);
                bb.setSize(1000,800);
                bb.setTitle("Euro Hotel");
                bb.setDefaultCloseOperation(bb.DISPOSE_ON_CLOSE);
                bb.setLocation(300,10);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this,"Incorrect Username or Password");
            }
        }
    }
}
public class Main{
    public static void main(String[] args) {
        frame screen = new frame();
        screen.setVisible(true);
        screen.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        screen.setSize(800,500);
        screen.setLocation(350,200);
        screen.setTitle("Euro Hotel");
        screen.setResizable(false);

    }
}