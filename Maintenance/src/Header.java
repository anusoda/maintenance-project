import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Header extends JFrame implements ActionListener{
	JPanel inside;
	MainView mnVw;
	QueueView quVw;
	IconView icVw;
	JMenuBar menuBar;
	static JLabel CurrSong;
	JLabel PlayMode;
	JButton PlayNext;
	int view;
	int mode;
	boolean iconV;
	ClearQueueBox queuebox;
	SongCreateBox createbox;
	EditSongBox editbox;
	public Header(){
		super("Juke Box");
		iconV = false;
		inside=new JPanel();
		
		mnVw=new MainView();
		quVw=new QueueView();
		icVw=new IconView(this);
		
		menuBar = new JMenuBar();
		menuBar.setAlignmentX(LEFT_ALIGNMENT);
		this.setResizable(false);
		
		JMenu menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription(
		        "Change the view");
		
		String[] names={"Main","Queue","Icon",
				"Shuffle","Queue","Dormant"};
		int[] keys={KeyEvent.VK_M,KeyEvent.VK_Q,KeyEvent.VK_I,
				KeyEvent.VK_S,KeyEvent.VK_Q,KeyEvent.VK_D};
		String[] ActComs={"ChMainView","ChQueueView","ChIconView",
				"ChShuffleMode","ChQueueMode","ChDormantMode"};
		for(int r=0;r<3;r++){
			menu.add(Header.getMenuItem(names[r],keys[r],this,ActComs[r]));
		}
		menuBar.add(menu);
		
		menu = new JMenu("Mode");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription("Change the mode");
		for(int r=3;r<6;r++){
			menu.add(Header.getMenuItem(names[r],keys[r],this,ActComs[r]));
		}
		menuBar.add(menu);
		if (iconV){
			menu = new JMenu("Queue Actions");
			menu.setMnemonic(KeyEvent.VK_Q);
			menu.getAccessibleContext().setAccessibleDescription("Queue Actions");
			menu.add(Header.getMenuItem("Add Playlist to Queue",KeyEvent.VK_P,this,"addP"));
			menu.add(Header.getMenuItem("Clear Queue",KeyEvent.VK_C,this,"clearQ"));
			menuBar.add(menu);
			menuBar.add(menu.add(Header.getMenuItem("New Song",KeyEvent.VK_N,this,"New Song")));
		}
		menuBar.add(menu.add(Header.getMenuItem("Exit",KeyEvent.VK_X,this,"EXIT")));
		this.setJMenuBar(menuBar);
		
		CurrSong=new JLabel("");
		CurrSong.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				Song lSong = Backend.StaticThis.getPlayHandler().getLastSong();
				if (lSong!=null){
					editbox=new EditSongBox(lSong.getName(),
								lSong.getURL(), lSong.getArtist(), lSong.getLastPlayed(),
								mnVw.lastSelected.getFrequency(), mnVw.list.getSelectedIndex(), mnVw);
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		PlayMode=new JLabel("| Queue");
		PlayNext=new JButton("Play Next");
		PlayNext.setActionCommand("PlayNext");
		PlayNext.addActionListener(this);
		JPanel but=new JPanel();
		
		but.setLayout(new BoxLayout(but, BoxLayout.X_AXIS));
		but.add(CurrSong);
		but.add(PlayMode);
		JPanel pane=new JPanel();
		pane.add(but, BorderLayout.WEST);
		pane.add(PlayNext, BorderLayout.EAST);
		pane.setAlignmentX(LEFT_ALIGNMENT);
		JPanel allContent=new JPanel();
		allContent.setAlignmentX(LEFT_ALIGNMENT);
		allContent.setLayout(new BoxLayout(allContent, BoxLayout.Y_AXIS));
		allContent.add(pane);
		allContent.add(inside);
		allContent.add(Box.createGlue());
		this.setContentPane(allContent);
		this.changeMode(0);
		this.changeView(0);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("ICON.JPG")).getImage());
		
	}
	
	
	public static JMenuItem getMenuItem(String name,int key,ActionListener listner,String ActCom){
		JMenuItem menuItem = new JMenuItem(name,key);
		menuItem.addActionListener(listner);
		menuItem.setActionCommand(ActCom);
		return menuItem;
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New Song"))
			createbox = new SongCreateBox(mnVw);
		if (e.getActionCommand().equals("addP")){
			ArrayList<Object> data = new ArrayList<Object>();
			Backend.actionRecieved(new Action(Action.Type.AddFrequentToQueue, data));
		}	
		if (e.getActionCommand().equals("clearQ"))
			queuebox = new ClearQueueBox(quVw);
		if(e.getActionCommand().startsWith("Ch")){
			if(e.getActionCommand().equals("ChMainView")){
				iconV = false;
				changeView(0);
			}
			else if(e.getActionCommand().equals("ChQueueView")){
				iconV = false;
				changeView(1);
			}
			else if(e.getActionCommand().equals("ChIconView")){
				iconV = true;
				changeView(2);
			}
		}else if(e.getActionCommand().equals("EXIT")){
			System.exit(0);
		}
		if (e.getActionCommand().equals("ChShuffleMode")){
			this.changeMode(0);
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(PlayHandler.Mode.Shuffle);
			Backend.actionRecieved(new Action(Action.Type.ChangeMode, args));
		} else if (e.getActionCommand().equals("ChQueueMode")){
			this.changeMode(1);
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(PlayHandler.Mode.Queue);
			Backend.actionRecieved(new Action(Action.Type.ChangeMode, args));
		} else if (e.getActionCommand().equals("ChDormantMode")){
			this.changeMode(2);
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(PlayHandler.Mode.Dormant);
			Backend.actionRecieved(new Action(Action.Type.ChangeMode, args));
		}
		
		if(e.getActionCommand().equals("PlayNext")){
			firePlayNextSong();
			CurrSong.setText(Backend.StaticThis.getPlayHandler().getLastSongName());
		}
		quVw.updateList();
		mnVw.updateList();
	}
	public void firePlayNextSong(){
		if(mode!=2)
			Backend.actionRecieved(new Action(Action.Type.PlayNext, null));
	}
	
	public void changeView(int view){
		this.view=view;
		inside.removeAll();
		JMenu menu=((JMenu)menuBar.getComponent(0));
		menu.getMenuComponent(0).setEnabled(true);
		menu.getMenuComponent(1).setEnabled(true);
		menu.getMenuComponent(2).setEnabled(true);
		icVw.getFrame().setVisible(false);
		this.setVisible(true);
		if(view==0){
			mnVw.updateList();
			inside.add(mnVw);
			menu.getMenuComponent(0).setEnabled(false);
		}else if(view==1){
			quVw.updateList();
			inside.add(quVw);
			menu.getMenuComponent(1).setEnabled(false);
		}else if(view==2){
			this.setVisible(false);
			icVw.getFrame().setVisible(true);
			menu.getMenuComponent(2).setEnabled(false);
		}
		this.validate();
		this.repaint();
	}
	public void changeMode(int mode){
		this.mode=mode;
		Backend.StaticThis.getPlayHandler().changeMode(mode == 0 ? PlayHandler.Mode.Shuffle : (mode == 1 ? PlayHandler.Mode.Queue : PlayHandler.Mode.Dormant));
		JMenu menu=((JMenu)menuBar.getComponent(1));
		JMenu pmenu=((JMenu)icVw.ddm.getComponent(1));
		pmenu.getMenuComponent(0).setEnabled(true);
		pmenu.getMenuComponent(1).setEnabled(true);
		pmenu.getMenuComponent(2).setEnabled(true);
		menu.getMenuComponent(0).setEnabled(true);
		menu.getMenuComponent(1).setEnabled(true);
		menu.getMenuComponent(2).setEnabled(true);
		PlayNext.setEnabled(true);
		mnVw.PlayNow.setEnabled(true);
		if(mode==0){
			menu.getMenuComponent(0).setEnabled(false);
			pmenu.getMenuComponent(0).setEnabled(false);
			PlayMode.setText("| Shuffle");
		}else if(mode==1){
			menu.getMenuComponent(1).setEnabled(false);
			pmenu.getMenuComponent(1).setEnabled(false);
			PlayMode.setText("| Queue");
		}else if(mode==2){
			menu.getMenuComponent(2).setEnabled(false);
			pmenu.getMenuComponent(2).setEnabled(false);
			mnVw.PlayNow.setEnabled(false);
			PlayNext.setEnabled(false);
			PlayMode.setText("| Dormant");
			
		}
	}
}
