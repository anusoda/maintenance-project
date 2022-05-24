import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ClearQueueBox implements ActionListener
{
	public QueueView quVw;
	private JFrame frame = new JFrame("Clear Queue");
	
	public boolean isVisible()
	{
		return frame.isVisible();
	}
	
	public ClearQueueBox(QueueView qu)
	{
		frame.setResizable(false);
		quVw=qu;
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Are you sure you want to clear the queue?");
		JLabel label2 = new JLabel("                                                                               ");
		JButton ok = new JButton("Yes");
		ok.addActionListener(this);
		JButton cancel = new JButton("No");
		cancel.addActionListener(this);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,10,0);
		panel.add(label1, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(label2, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(ok, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(cancel, c);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 200));
		frame.pack();
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("Yes"))
		{
			ArrayList<Object> data = new ArrayList<Object>();
			Action a = new Action(Action.Type.ClearQueue, data);
			Backend.actionRecieved(a);
			quVw.updateList();
		}
		frame.setVisible(false);
	}
}
