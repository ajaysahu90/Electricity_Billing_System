import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class paybiill extends JFrame implements ActionListener {

    Choice cmonth;
    JButton Pay, Back;
    String meter;
    paybiill(String meter){
        this.meter = meter;
       setLayout(null);
       setBounds(300,150,900,600);
       JLabel heading = new JLabel("Electricity Bill");
       heading.setFont(new Font("Tohma",Font.BOLD,24));
       heading.setBounds(120,5,400,30);
       add(heading);

        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(35,80,200,20);
        add(lblmeterno);

        JLabel lblmeternumber = new JLabel(" ");
        lblmeternumber.setBounds(300,80,200,20);
        add(lblmeternumber);

        JLabel lblname  = new JLabel(" Name");
        lblname.setBounds(35,140,200,20);
        add(lblname);

        JLabel lablename  = new JLabel(" ");
        lablename.setBounds(300,140,200,20);
        add(lablename);

        JLabel lblmonth  = new JLabel(" Month");
        lblmonth.setBounds(35,200,200,20);
        add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(300,200,200,20);
        cmonth.add("Jenuary");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);

        JLabel lblunits = new JLabel("Units");
        lblunits.setBounds(35,260,200,20);
        add(lblunits);

        JLabel labelunits = new JLabel(" ");
        labelunits.setBounds(300,260,200,20);
        add(labelunits);

        JLabel lbltotalbill = new JLabel("Total Bill");
        lbltotalbill.setBounds(35,320,200,20);
        add(lbltotalbill);

        JLabel labeltotalbill = new JLabel(" ");
        labeltotalbill.setBounds(300,320,200,20);
        add(labeltotalbill);

        JLabel lblstatus = new JLabel("Status");
        lblstatus.setBounds(35,380,200,20);
        add(lblstatus);

        JLabel labelstatus = new JLabel(" ");
        labelstatus.setBounds(300,380,200,20);
        labelstatus.setForeground(Color.RED);
        add(labelstatus);

        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no= '"+meter+"'");
            while(rs.next()){
                lblmeternumber.setText(meter);
                lablename.setText(rs.getString("name"));
            }
             rs = c.s.executeQuery("select * from bill where meter_no= '"+meter+"' AND month= 'January'");
            while(rs.next()){
                labelunits.setText(rs.getString("units"));
                labeltotalbill.setText(rs.getString("totalbill"));
                labelstatus.setText(rs.getString("status"));
            }

        }catch (Exception e){
            e.printStackTrace();

        }
         cmonth.addItemListener(new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent ae) {
                 try{
                     Conn c = new Conn();

                     ResultSet rs = c.s.executeQuery("select * from bill where meter_no= '"+meter+"' AND month= '"+cmonth.getSelectedItem()+"'");
                     while(rs.next()){
                         labelunits.setText(rs.getString("units"));
                         labeltotalbill.setText(rs.getString("totalbill"));
                         labelstatus.setText(rs.getString("status"));
                     }

                 }catch (Exception e){
                     e.printStackTrace();

                 }
             }
         });

        Pay = new JButton("Pay");
        Pay.setBackground(Color.BLACK);
        Pay.setForeground(Color.WHITE);
        Pay.setBounds(100,460,100,25);
        Pay.addActionListener(this);
        add(Pay);

        Back = new JButton("Back");
        Back.setBackground(Color.BLACK);
        Back.setForeground(Color.WHITE);
        Back.setBounds(230,460,100,25);
        Back.addActionListener(this);
        add(Back);

        getContentPane().setBackground(Color.WHITE);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(600,300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400,120,600,300);
        add(image);



        setVisible(true);
    }


    public  void actionPerformed(ActionEvent ae){
        if (ae.getSource()==Pay){
            try {
               Conn c = new Conn();
               c.s.executeUpdate("update bill set status = 'Paid' where meter_no= '"+meter+"' AND month = '"+cmonth.getSelectedItem()+"'");
            }catch (Exception e){
                e.printStackTrace();
            }
            setVisible(false);
            new Paytm(meter);

        }else {
            setVisible(false);
        }

    }
    public static void main(String[] args) {
        new paybiill("");
    }
}
