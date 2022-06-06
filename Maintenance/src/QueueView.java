import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;


@SuppressWarnings("serial")
public class QueueView extends View {
	
	protected ClearQueueBox queuebox;
	
	public QueueView() {
		super("Queue View");
		list.setCellRenderer(new MyCellRenderer());
		String[] n={"Move Up","Move Down","Move To Top","Move To Bottom","Remove"};
		String[] a={"MoveUp","MoveDown","MoveTop","MoveBottom","Remove"};
		this.setPopmenu(n,a);
		JButton clear=new JButton("Clear Queue");
		clear.setActionCommand("ClearQueue");
		clear.addActionListener(this);
		this.add(clear);
	}
	
	protected boolean checkBoxes()
	{
		if(queuebox != null)
			if(queuebox.isVisible())
				return false;
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("ClearQueue")){
			if(checkBoxes())
				queuebox = new ClearQueueBox(this);
		}else if(e.getActionCommand().equals("MoveUp")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Backend.actionRecieved(new Action(Action.Type.MoveSongUp, data));
		}else if(e.getActionCommand().equals("MoveDown")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Backend.actionRecieved(new Action(Action.Type.MoveSongDown, data));
		}else if(e.getActionCommand().equals("MoveTop")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Backend.actionRecieved(new Action(Action.Type.MoveSongTop, data));
		}else if(e.getActionCommand().equals("MoveBottom")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			Backend.actionRecieved(new Action(Action.Type.MoveSongBottom, data));
		}else if(e.getActionCommand().equals("Remove")){
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(list.getSelectedIndex());
			data.add(false);
			Backend.actionRecieved(new Action(Action.Type.Remove, data));
		}
		updateList();
		this.revalidate();
	}
	
	class MyCellRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(final JList list,
				final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			
			Component c = super.getListCellRendererComponent(list, value, index,
		            isSelected, cellHasFocus);

			Song song = Backend.StaticThis.getSongQueue().getSong(index);

			if (song.getFrequency() == 1)
				c.setFont(c.getFont().deriveFont(Font.PLAIN));
			else if (song.getFrequency() == 2)
				c.setFont(c.getFont().deriveFont(Font.BOLD));
			else if (song.getFrequency() == 0)
				c.setFont(c.getFont().deriveFont(Font.ITALIC));
			
			return c;
		}

	}
	
	public void valueChanged(ListSelectionEvent e) {
		JList list=(JList)e.getSource();
		list.ensureIndexIsVisible(list.getSelectedIndex());
	}
	public void updateList(){
		updateList(Backend.StaticThis.getSongQueue().getSongs());
	}
}
