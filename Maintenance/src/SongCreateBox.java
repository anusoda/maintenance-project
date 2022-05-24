import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class SongCreateBox implements ActionListener, KeyListener
{
	public MainView mnVw;
	private JFrame frame = new JFrame("Create Song");
	private JTextField box1 = new JTextField(20);
	private JTextField box2 = new JTextField(20);
	private JTextField box3 = new JTextField(20);
	JRadioButton dontPlay = new JRadioButton("Dont Play");
	JRadioButton play = new JRadioButton("Play");
	JRadioButton frequent = new JRadioButton("Frequent Playlist");
	JButton search = new JButton("  Search  ");
	
	public boolean isVisible()
	{
		return frame.isVisible();
	}

	public SongCreateBox(MainView mn)
	{
		frame.setResizable(false);
		mnVw=mn;
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Enter song name:                              ");
		JLabel label2 = new JLabel("Enter song url: ");
		JLabel label3 = new JLabel("Enter artist:");
		JButton ok = new JButton("Create");
		ok.addActionListener(this);
		search.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		play.setSelected(true);
		cancel.addActionListener(this);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		ButtonGroup group = new ButtonGroup();
		group.add(dontPlay);
		group.add(play);
		group.add(frequent);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,10,0);
		panel.add(label1, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(box1, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label2, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(box2, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(label3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		panel.add(box3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(dontPlay, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		panel.add(play, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		panel.add(frequent, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(ok, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		panel.add(cancel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		panel.add(search, c);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 200));
		box1.addKeyListener(this);
		box2.addKeyListener(this);
		frame.pack();
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("Cancel"))
		{
			frame.setVisible(false);
		}
		
		if(arg0.getActionCommand().equals("  Search  ") || arg0.getActionCommand().equals(" Not Found ") || arg0.getActionCommand().equals("   Found   "))
		{
			String url = "";
			if(box2.getText().length()==0 && box3.getText().length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				url = SongRetriever.getUrl(box3.getText(), box1.getText());
				if(url.length()!=0)
				{
					box2.setText(url);
					search.setText("   Found   ");
				}
				else
				{
					search.setText(" Not Found ");
				}
			}
			else
			{
				search.setText("  Search  ");
			}
		}
		if(arg0.getActionCommand().equals("Create"))
		{
			ArrayList<Object> data = new ArrayList<Object>();

			String url = "";
			if(box2.getText().length()==0 && box3.getText().length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				url = SongRetriever.getUrl(box3.getText(), box1.getText());
				if(url.length()!=0)
					box2.setText(url);
			}

			if(box1.getText().length()==0)
			{
				box1.setText(" ");
			}
			if(!box2.getText().contains("http://"))
			{
				box2.setText("http://" + box2.getText());
			}
			data.add(box1.getText());
			data.add(box2.getText());
			int frequency=1;
			if(dontPlay.isSelected())
				frequency = 0;
			else if(play.isSelected())
				frequency = 1;
			else if(frequent.isSelected())
				frequency = 2;
			data.add(frequency);
			if(url.length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				data.add(true);
				data.add(box3.getText());
			}
			else
				data.add(false);
			Action a = new Action(Action.Type.CreateSong, data);
			Backend.actionRecieved(a);
			mnVw.updateList();
			frame.setVisible(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyChar() == KeyEvent.VK_ENTER)
		{
			ArrayList<Object> data = new ArrayList<Object>();

			String url = "";
			if(box2.getText().length()==0 && box3.getText().length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				url = SongRetriever.getUrl(box3.getText(), box1.getText());
				if(url.length()!=0)
					box2.setText(url);
			}

			if(box1.getText().length()==0)
			{
				box1.setText(" ");
			}
			if(!box2.getText().contains("http://"))
			{
				box2.setText("http://" + box2.getText());
			}
			data.add(box1.getText());
			data.add(box2.getText());
			int frequency=1;
			if(dontPlay.isSelected())
				frequency = 0;
			else if(play.isSelected())
				frequency = 1;
			else if(frequent.isSelected())
				frequency = 2;
			data.add(frequency);
			if(url.length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				data.add(true);
				data.add(box3.getText());
			}
			else
				data.add(false);
			Action a = new Action(Action.Type.CreateSong, data);
			Backend.actionRecieved(a);
			mnVw.updateList();
			frame.setVisible(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
