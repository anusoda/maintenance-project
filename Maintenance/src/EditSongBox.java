import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;

import java.util.*;

public class EditSongBox implements ActionListener
{
	View vw;
	private JFrame frame = new JFrame("Edit Song");
	private JTextField box1 = new JTextField(20);
	private JTextField box2 = new JTextField(20);
	private JTextField box3 = new JTextField(20);
	JRadioButton dontPlay = new JRadioButton("Dont Play");
	JRadioButton play = new JRadioButton("Play");
	JRadioButton frequent = new JRadioButton("Frequent Playlist");
	private int index;
	Song thisSong;
	public boolean isVisible()
	{
		return frame.isVisible();
	}
	
	public EditSongBox(String name, String url, String artist, long time, int f, int songindex, View v)
	{
		frame.setResizable(false);
		vw=v;
		index = songindex;
		box1.setText(name);
		box2.setText(url);
		box3.setText(artist);
		if(f == 0)
			dontPlay.setSelected(true);
		else if(f ==1)
			play.setSelected(true);
		else if(f == 2)
			frequent.setSelected(true);
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Enter song name:                              ");
		JLabel label2 = new JLabel("Enter song url: ");
		JLabel label3 = new JLabel("Last Played: ");
		JLabel label5 = new JLabel("Enter song artist: ");
		Date date = new Date(time);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		String dateFormatted = formatter.format(date);
		JLabel label4 = new JLabel(dateFormatted.toString());
		JButton ok = new JButton("Done");
		ok.addActionListener(this);
		JButton cancel = new JButton("Cancel");
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
		//panel.add(label5, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		//panel.add(box3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(label3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		panel.add(label4, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(dontPlay, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		panel.add(play, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		panel.add(frequent, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(ok, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		panel.add(cancel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 5;
		//panel.add(search, c);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 200));
		frame.pack();
	}
	public EditSongBox(String name, String url, String artist, long time, int f, int songindex, View v, Song s)
	{
		thisSong = s;
		frame.setResizable(false);
		vw=v;
		index = songindex;
		box1.setText(name);
		box2.setText(url);
		box3.setText(artist);
		if(f == 0)
			dontPlay.setSelected(true);
		else if(f ==1)
			play.setSelected(true);
		else if(f == 2)
			frequent.setSelected(true);
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Enter song name:                              ");
		JLabel label2 = new JLabel("Enter song url: ");
		JLabel label3 = new JLabel("Last Played: ");
		JLabel label5 = new JLabel("Enter song artist: ");
		Date date = new Date(time);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a E");
		String dateFormatted = formatter.format(date);
		JLabel label4 = new JLabel(dateFormatted.toString());
		JButton ok = new JButton("Done");
		ok.addActionListener(this);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		//search.addActionListener(this);
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
		//panel.add(label5, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		//panel.add(box3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		panel.add(label3, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		panel.add(label4, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(dontPlay, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		panel.add(play, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		panel.add(frequent, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(ok, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		panel.add(cancel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 5;
		//panel.add(search, c);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 200));
		frame.pack();
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("  Search  ") || arg0.getActionCommand().equals(" Not Found ") || arg0.getActionCommand().equals("   Found   "))
		{
			String url = "";
			if(box2.getText().length()==0 && box3.getText().length()!=0 || box2.getText().contains("vkontakte.ru"))
			{
				url = SongRetriever.getUrl(box3.getText(), box1.getText());
				if(url.length()!=0)
				{
					box2.setText(url);
					
				}
				else
				{
				
				}
			}
			else
			{
			
			}
		}
		if(arg0.getActionCommand().equals("Cancel"))
		{
			frame.setVisible(false);
		}
		if(arg0.getActionCommand().equals("Done"))
		{
			ArrayList<Object> data = new ArrayList<Object>();

			String url = "";
			if(box2.getText().length()==0 && box3.getText().length()!=0  || box2.getText().contains("vkontakte.ru"))
			{
				url = SongRetriever.getUrl(box3.getText(), box1.getText());
				if(url.length()!=0)
					box2.setText(url);
			}
			data.add(box1.getText());
			data.add(box2.getText());
			data.add(index);
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
			if (index==-1){
				System.out.println(thisSong);
				data.add(thisSong);
			}
			Action a = new Action(Action.Type.EditSong, data);
			Backend.actionRecieved(a);
			vw.updateList();
			frame.setVisible(false);
		}
	}
}
