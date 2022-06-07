import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class pop extends JFrame implements ActionListener {
    // popup
    Popup p;
    private PlayHandler pH;
    private JFrame f;
    // constructor
    pop(PlayHandler playHandler)
    {
        pH=playHandler;
        // create a frame
        f = new JFrame("empty queue");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        // create a label
        JLabel l = new JLabel("The queue is empty.");
 
        f.setSize(400, 400);
 
        PopupFactory pf = new PopupFactory();
 
        // create a panel
        

        JPanel p1 = new JPanel();
        
 // create a popup
 p = pf.getPopup(f, p1, 180, 100);
 

 p1.add(l);
 // create a button
 JButton dismiss = new JButton("dismiss");
 JButton switchToShuffle = new JButton("switch to shuffle");
 


 // add action listener
 dismiss.addActionListener(this);
 switchToShuffle.addActionListener(this);


 // create a panel
 

 p1.add(dismiss);
 p1.add(switchToShuffle);
 f.add(p1);
 f.show();
}

// if the button is pressed
public void actionPerformed(ActionEvent e)
{
    if (e.getActionCommand().equals("switch to shuffle")) {
        pH.switchToShuffle();
       
    }
    
    f.dispose();
 //p.show();
}
// main class
//public static void main(String args[])
//{
//    PlayHandler ph = new PlayHandler();
// pop p = new pop(ph);
//}
}