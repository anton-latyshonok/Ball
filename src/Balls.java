package ball;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Balls extends JFrame 
{
    int i;
    JPanel frame = new JPanel();
    Graphics g;
    JButton button1 = new JButton("Add ball");
    JButton button2 = new JButton("Pause");
    JButton button3 = new JButton("Delete all balls");
    
    public Balls() { 
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        setBounds(300, 80, 870, 650);       
        button1.setBounds(20, 20, 250, 40);
        contentPane.add(button1);
        button2.setBounds(300, 20, 250, 40);
        contentPane.add(button2);
        button3.setBounds(580, 20, 250, 40);
        contentPane.add(button3); 
        
        contentPane.add(frame);
        frame.setBounds(10, 80, 835, 500);        
        frame.setBackground(Color.BLUE);
       
        button1.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event1) 
            {
                new BALL_MOVEMENT(frame).start();
                i = BALL_MOVEMENT.number;
                repaint();
            }
        });
        
        button2.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event2) 
            {
                if(BALL_MOVEMENT.pause == true)
                {
                  BALL_MOVEMENT.pause = false;
                  button2.setText("Pause");
                } 
                else 
                {
                  BALL_MOVEMENT.pause = true;
                  button2.setText("Continue");
                }
            }
        });
        
        button3.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event3) 
            {
                BALL_MOVEMENT.number = 0;            
            }
        });
    }
    
    public void DRAW(Graphics g)
    {
        super.paint(g);
    }
    
    public static void main(String[] args) 
    {
    	Balls frame = new Balls();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class BALL_MOVEMENT extends Thread 
{  
    public static boolean ind;    
    public static boolean pause = false;
    public static int number = 0;
    private final int BALL_SIZE = 20;
    private int X_POS, Y_POS;    
    private double Angle;
    Random random = new Random();
    private  int SPEED = 15 + random.nextInt(10);
    JPanel frame;
    BALL_MOVEMENT(JPanel p) 
    {
        this.frame = p;
        Angle = Math.random() * 10;
        X_POS = (int)((frame.getWidth() - BALL_SIZE) * Math.random());
        Y_POS = (int)((frame.getHeight() - BALL_SIZE) * Math.random());
    }
    
    public void DRAW(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.drawArc(X_POS + 1, Y_POS + 1, BALL_SIZE, BALL_SIZE, 120, 30);
        g.setColor(Color.RED);
        g.fillArc(X_POS, Y_POS, BALL_SIZE, BALL_SIZE, 0, 360);        
        try 
        {
            sleep(25);
        } 
        catch(InterruptedException e) 
        {
            e.printStackTrace();
        }
       g.setColor(frame.getBackground());
       g.fillArc(X_POS, Y_POS, BALL_SIZE, BALL_SIZE, 0, 360); 
    }
    
    public void run() 
    {
        number++;
        float Speed = SPEED;        
        while(number != 0 && Speed>=2) 
        {
            if(pause == true)
            {
                DRAW(frame.getGraphics());          
            } 
            else 
            {
                Speed *= 0.993;
                X_POS += (int)(SPEED * Math.cos(Angle));
                Y_POS += (int)(SPEED * Math.sin(Angle));
                if( Y_POS >= frame.getHeight() - BALL_SIZE )
                    Angle = -Angle;
                else if( Y_POS <= 0 )
                    Angle = -Angle;    
                if( X_POS >= frame.getWidth() - BALL_SIZE )
                    Angle = Angle + Math.PI - 2 * Angle;
                else if( X_POS <= 0 )
                    Angle = Math.PI - Angle; 
                DRAW(frame.getGraphics());                           
                SPEED = Math.round(Speed);  
            }
        }        
        number--;
        
        if(BALL_MOVEMENT.number <= 0 && BALL_MOVEMENT.ind==false)
        {    
            BALL_MOVEMENT.number = 0;      
            System.out.println("Balls are over!");
        }        
    }
}
