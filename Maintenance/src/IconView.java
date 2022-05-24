import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;


public class IconView implements MouseListener,MouseMotionListener {
	public DropDownMenu ddm; 
	private ImageIcon icon;
	private Drawer pane;
	private Header head;
	private JFrame frame;

	public IconView(Header header){

		head=header;

		icon = new ImageIcon(getClass().getClassLoader().getResource("ICON.JPG"));

		ddm=new DropDownMenu();
		//icon = new ImageIcon("Z:\\BTE.png");

		frame = new JFrame("Icon View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = new Drawer();
		icon = new ImageIcon(icon.getImage().getScaledInstance(64, 64, 0));
		pane.setBorder(BorderFactory.createEmptyBorder(0, 0, icon.getIconHeight() - 11, icon.getIconWidth() - 11));
		frame.setResizable(false);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.setUndecorated(true); 
		frame.setAlwaysOnTop(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);  
		frame.setContentPane(pane);
		frame.pack();

		frame.setLocation(100, 100);

	}
	public JFrame getFrame(){
		return frame;
	}
	@SuppressWarnings("serial")
	class Drawer extends JPanel 
	{
		@Override
		public void paintComponent(Graphics g){
			icon.paintIcon(this, g, 0, 0);
		}
	}

	@SuppressWarnings("serial")
	class DropDownMenu extends JPopupMenu{

		public DropDownMenu(){

			JMenu view = new JMenu("View");
			JMenuItem mainview = new JMenuItem("Main");
			JMenuItem queueview = new JMenuItem("Queue");
			view.add(mainview);
			view.add(queueview);
			JMenuItem icon = new JMenuItem("Icon");
			icon.setEnabled(false);
			view.add(icon);
			JMenu mode = new JMenu("Mode");
			JMenuItem shufflemode = new JMenuItem("Shuffle");
			JMenuItem queuemode = new JMenuItem("Queue");
			JMenuItem dormantmode = new JMenuItem("Dormant");
			mode.add(shufflemode);
			mode.add(queuemode);
			mode.add(dormantmode);
			add(view);
			add(mode);
			JMenuItem exit = new JMenuItem("Exit");
			exit.setActionCommand("EXIT");
			exit.addActionListener(head);
			add(exit);

			mainview.addActionListener(head);
			mainview.setActionCommand("ChMainView");
			queueview.addActionListener(head);
			queueview.setActionCommand("ChQueueView");
			shufflemode.addActionListener(head);
			shufflemode.setActionCommand("ChShuffleMode");
			queuemode.addActionListener(head);
			queuemode.setActionCommand("ChQueueMode");
			dormantmode.addActionListener(head);
			dormantmode.setActionCommand("ChDormantMode");
			exit.addActionListener(head);

		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3){
			ddm.show(e.getComponent(), e.getX(), e.getY());
		}else if (e.getButton() == 1&&head.mode!=2)
			head.firePlayNextSong();
	}

	public void mouseEntered(MouseEvent arg0) {

	}
	public void mouseExited(MouseEvent arg0) {

	}
	public void mousePressed(MouseEvent arg0) {

	}
	public void mouseReleased(MouseEvent e) {


	}
	@Override
	public void mouseDragged(MouseEvent e) {
		frame.setLocation(e.getXOnScreen()-32, e.getYOnScreen()-32);
	}
	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
