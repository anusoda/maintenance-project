import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class pop implements ActionListener {
    // popup
    Popup p;
    private PlayHandler pH;
    JFrame f;
    // constructor
    pop(PlayHandler playHandler, boolean visible)
    {
        pH=playHandler;
        // create a frame
        f = new JFrame("empty queue");
        f.setResizable(false);

 
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
 f.setVisible(visible);
}

// if the button is pressed
public void actionPerformed(ActionEvent e)
{
    if (e.getActionCommand().equals("switch to shuffle")) {
        pH.switchToShuffle();
    }
    f.setVisible(false);
 //p.show();
}

public boolean isVisible() {
    return f.isVisible();
}

public void show() {
    f.setVisible(true);
}
// main class

}