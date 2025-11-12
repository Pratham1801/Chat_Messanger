import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.color.*;

class ChatGui implements ActionListener
{
    JFrame fobj;
    JLabel MesLabel, RetLAbel;
    JTextField tobj;
    JButton Sendobj;

    PrintStream pobj;
    BufferedReader bobj1, bobj2;

    public ChatGui(String title, int width, int height, PrintStream po, BufferedReader bo1, BufferedReader bo2)
    {
        pobj = po;
        bobj1 = bo1;
        bobj2 = bo2;

        fobj = new JFrame(title);
        fobj.getContentPane().setBackground(Color.orange);

        MesLabel = new JLabel("Msg for Client: ");
        MesLabel.setBounds(30, 50, 100, 30);
        MesLabel.setFont(new Font("Arial",Font.BOLD,13));
        MesLabel.setForeground(Color.RED);

        tobj = new JTextField();
        tobj.setBounds(150, 50, 150, 30);
        tobj.setFont(new Font("Arial",Font.BOLD,13));
        tobj.setForeground(Color.RED);

        Sendobj = new JButton("SEND");
        Sendobj.setBounds(125, 150, 100, 40);
        Sendobj.setFont(new Font("Arial",Font.BOLD,13));
        Sendobj.setForeground(Color.RED);


        RetLAbel = new JLabel("");
        RetLAbel.setBounds(100, 200, 150, 50);
        RetLAbel.setFont(new Font("Arial",Font.BOLD,13));
        RetLAbel.setForeground(Color.RED);


        fobj.add(MesLabel);
        fobj.add(tobj);
        fobj.add(Sendobj);
        fobj.add(RetLAbel);

        Sendobj.addActionListener(this);
        tobj.addActionListener(aeobj -> Sendobj.doClick());

        fobj.setLayout(null);
        fobj.setSize(width,height);

        fobj.setVisible(true);
        fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(new Runnable(){
            public void run()
            {
                try
                {    
                    String ReceivedMsg;
                    while((ReceivedMsg = bobj1.readLine())!=null)
                    {
                        RetLAbel.setText("Client Says: "+ReceivedMsg);
                    }
                }
                catch(IOException ioobj)
                {}
            }
        }).start();
    }

    public void actionPerformed(ActionEvent aeobj)
    {
        String msg = tobj.getText();
        pobj.println(msg);
        tobj.setText("");
    }
}

class ChatServerGui
{
    public static void main(String A[]) throws Exception
    {
        ServerSocket ssobj = new ServerSocket(5100);
        System.out.println("Server is waiting at port 5100");

        Socket sobj = ssobj.accept();
        System.out.println("Client Request Gets accepted successfully");

        PrintStream pobj = new PrintStream(sobj.getOutputStream());
        BufferedReader bobj1 = new BufferedReader(new InputStreamReader(sobj.getInputStream()));
        BufferedReader bobj2 = new BufferedReader(new InputStreamReader(System.in));

        ChatGui cgobj = new ChatGui("Server", 400, 300, pobj, bobj1, bobj2);

        // String str1 = null, str2 = null;

        // while((str1 = bobj1.readLine())!=null)
        // {
        //     System.out.println("Client says: "+str1);
        //     System.out.println("Enter msg for client: ");
        //     str2 = bobj2.readLine();
        //     pobj.println(str2);
        // }   
    }
}