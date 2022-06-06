import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;


@SuppressWarnings("serial")
public class MainView extends View implements ChangeListener, MouseListener{
	private JButton NewSong;
	public JButton PlayNow;
	public JButton AddSongsToQueue;
	private JSlider ActiveSong;
	private JLabel percent;
	private int sliderValue;
	protected SongCreateBox createbox;
	protected EditSongBox editbox;
	protected DeleteSongBox deletebox;
	protected JLabel CurrSong;

	protected Song lastSelected = null;
	private long lastClicked = System.currentTimeMillis();;

	protected boolean checkBoxes()
	{
		if(createbox != null)
			if(createbox.isVisible())
				return false;
		if(editbox != null)
			if(editbox.isVisible())
				return false;
		if(deletebox != null)
			if(deletebox.isVisible())
				return false;
		return true;
	}

	public MainView() {
		super("Main View");
		NewSong=new JButton("New Song");
		NewSong.setActionCommand("NewSong");
		NewSong.addActionListener(this);

		AddSongsToQueue=new JButton("Add playlist to Queue");
		AddSongsToQueue.setActionCommand("AddSongsToQueue");
		AddSongsToQueue.addActionListener(this);

		PlayNow=new JButton("Play Now");
		PlayNow.setActionCommand("PlayNow");
		PlayNow.addActionListener(this);
		list.addMouseListener(this);

		sliderValue=Backend.StaticThis.getPlayHandler().getPlayPercentage();
		ActiveSong=new JSlider(JSlider.HORIZONTAL,0, 99, sliderValue-1);
		ActiveSong.setMajorTickSpacing(10);
		ActiveSong.setMinorTickSpacing(5);
		//ActiveSong.setMinorTickSpacing(1);
		ActiveSong.setPaintTicks(true);
		ActiveSong.setPaintLabels(true);
		ActiveSong.setOrientation(JSlider.HORIZONTAL);
		ActiveSong.addChangeListener(this);

		percent=new JLabel("Percentage:"+sliderValue+"%");

		JPanel SliderSec=new JPanel();
		SliderSec.add(ActiveSong);
		SliderSec.add(percent);

		JPanel Buttons=new JPanel();
		Buttons.add(PlayNow);
		Buttons.add(NewSong);
		Buttons.add(AddSongsToQueue);

		JPanel box=new JPanel();
		box.add(SliderSec);
		box.add(Buttons);
		this.add(box);

		list.setCellRenderer(new MyCellRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setDragEnabled(true);
		
		String[] n={"Add To Queue", "Play Now","Edit","Delete"};
		String[] a={"AddQueue", "PlayNow", "Edit","Delete"};
		this.setPopmenu(n,a);

		this.revalidate();
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) {

		this.revalidate();
		if(e.getActionCommand().equals("PlayNext")){
		}
		else if(e.getActionCommand().equals("PlayNow")){
			@SuppressWarnings("unused")
			ArrayList<Object> args = new ArrayList<Object>();
		}
		else if(e.getActionCommand().equals("NewSong")){
			if(checkBoxes())
				createbox = new SongCreateBox(this);
		}
		if(e.getActionCommand().equals("Edit")){
			if(checkBoxes())
				editbox=new EditSongBox(((Song)list.getSelectedValue()).getName(),
						((Song)list.getSelectedValue()).getURL(), ((Song)list.getSelectedValue()).getArtist(), ((Song)list.getSelectedValue()).getLastPlayed(),
						((Song)list.getSelectedValue()).getFrequency(), list.getSelectedIndex(), this);
		}else if(e.getActionCommand().equals("Delete")){
			if(checkBoxes())
				deletebox=new DeleteSongBox(list.getSelectedValue().toString(), 
						list.getSelectedIndex(), this);
		}else if(e.getActionCommand().equals("AddQueue")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Action a=new Action(Action.Type.AddSongToQueue,data);
			Backend.actionRecieved(a);
		}else if(e.getActionCommand().equals("PlayNow") && list.getSelectedIndex()!= -1){
			if(list.getSelectedIndex()<0)
				return;
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Header.CurrSong.setText(((Song)list.getSelectedValue()).getName());
			Backend.actionRecieved(new Action(Action.Type.PlayNow, data));
		}else if(e.getActionCommand().equals("AddSongsToQueue")){
			ArrayList<Object> data = new ArrayList<Object>();
			Backend.actionRecieved(new Action(Action.Type.AddFrequentToQueue, data));

			//Backend.actionRecieved(new Action(Action.Type.AddAllToQueue, data));
		}
		updateList();
	}


	class MyCellRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(final JList list,
				final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			
			Component c = super.getListCellRendererComponent(list, value, index,
		            isSelected, cellHasFocus);

			Song song = Backend.StaticThis.getLibrary().getSong(index);

			if (song.getFrequency() == 1)
				c.setFont(c.getFont().deriveFont(Font.PLAIN));
			else if (song.getFrequency() == 2)
				c.setFont(c.getFont().deriveFont(Font.BOLD));
			else if (song.getFrequency() == 0)
				c.setFont(c.getFont().deriveFont(Font.ITALIC));
			
			return c;
		}

	}

	public void stateChanged(ChangeEvent e){
		JSlider source=(JSlider)e.getSource();
		sliderValue=source.getValue()+1;
		percent.setText("Percentage:"+(sliderValue)+"%");

		ArrayList<Object> args = new ArrayList<Object>();
		args.add((Integer)sliderValue);
		Backend.actionRecieved(new Action(Action.Type.SavePlayPercentage, args));

	}

	public void valueChanged(ListSelectionEvent e) {
		JList list=(JList)e.getSource();
		list.ensureIndexIsVisible(list.getSelectedIndex());
	}
	public void updateList(){
		updateList(Backend.StaticThis.getLibrary().getSongs());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(System.currentTimeMillis()-lastClicked < 400)
		{
			if((Song)list.getSelectedValue()==lastSelected)
				if(checkBoxes())
					editbox=new EditSongBox(((Song)list.getSelectedValue()).getName(),
							((Song)list.getSelectedValue()).getURL(), ((Song)list.getSelectedValue()).getArtist(), ((Song)list.getSelectedValue()).getLastPlayed(),
							((Song)list.getSelectedValue()).getFrequency(), list.getSelectedIndex(), this);
		}
		else
		{
			lastSelected = (Song)list.getSelectedValue();
			lastClicked = System.currentTimeMillis();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
