import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public abstract class View extends JPanel implements ActionListener ,ListSelectionListener{
	protected JList list;
	protected DefaultListModel listMod;
	protected PopupListener popup;
	protected JPopupMenu popMenu;
	public View(String title){
		JLabel Title=new JLabel(title);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.add(Title);
		
		listMod=new DefaultListModel();
		list=new JList(listMod);
		list.setAlignmentY(LEFT_ALIGNMENT);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(true);
        if(title.equals("Queue View")){
        	list.setDropMode(DropMode.INSERT);
        	list.setTransferHandler(new TransferHandler() {
                public boolean canImport(TransferHandler.TransferSupport info) {
                	// we only import Strings
                    if (!info.isDataFlavorSupported(new SongDataFlavor())) {
                        return false;
                    }
                    JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
                    if (dl.getIndex() == -1) {
                        return false;
                    }
                    return true;
                }
     
                public boolean importData(TransferHandler.TransferSupport info) {
                	//System.out.println("Importing");
                	if (!info.isDrop()) {
                        return false;
                    }
                    // Check for String flavor
                    if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        displayDropLocation("List doesn't accept a drop of this type.");
                        return false;
                    }
                    
                    JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
                    DefaultListModel listModel = (DefaultListModel)list.getModel();
                    int index = dl.getIndex();
                    boolean insert = dl.isInsert();
                    //System.out.println("insert:"+insert);
                    // Get the current string under the drop.
//                    String value = listModel.getElementAt(index).toString();
     
                    // Get the string that is being dropped.
//                    Transferable t = info.getTransferable();
//                    String data="Song Trans";
//                    try {
//                        data = t.getTransferData(new SongDataFlavor()).toString();
//                    }
//                    catch (Exception e) { return false; }
//                     
//                    // Display a dialog with the drop information.
//                    String dropValue = "\"" + data + "\" dropped ";
                    //System.out.println("STarinnkgsdklajwrfhasjkfhaweklrje");
                    if (dl.isInsert()) {
                    	//System.out.println("enz:"+dl.getIndex());
                        if (dl.getIndex() <= 0) {
                        	//System.out.println("0");
                        	ArrayList<Object> objs=new ArrayList<Object>(2);
                        	objs.add((list.getSelectedIndex()));
                            objs.add((0));
                            Backend.actionRecieved(new Action(Action.Type.InsertSong,objs));
//                        	displayDropLocation(dropValue + "at beginning of list");
                        } else if (dl.getIndex() >= list.getModel().getSize()) {
                        	//System.out.println("large");
                        	ArrayList<Object> objs=new ArrayList<Object>(2);
                        	objs.add((list.getSelectedIndex()));
                            objs.add((list.getModel().getSize()));
                            Backend.actionRecieved(new Action(Action.Type.InsertSong,objs));
//                            displayDropLocation(dropValue + "at end of list");
                        } else {
                        	//System.out.println("between");
                            String value1 = list.getModel().getElementAt(dl.getIndex() - 1).toString();
                            String value2 = list.getModel().getElementAt(dl.getIndex()).toString();
                            ArrayList<Object> objs=new ArrayList<Object>(2);
                            objs.add((list.getSelectedIndex()));
                            objs.add((dl.getIndex()));
                            Backend.actionRecieved(new Action(Action.Type.InsertSong,objs));
//                            displayDropLocation(dropValue + "between \"" + value1 + "\" and \"" + value2 + "\"");
                        }
                        updateList();
                    } else {
                    	System.out.println("on top");
//                    	ArrayList<Object> objs=new ArrayList<Object>(2);
//                        objs.add((list.getSelectedIndex()));
//                        objs.add((dl.getIndex()));
//                        Backend.actionRecieved(new Action(Action.Type.SwapSongs,objs));
//                        updateList();
//                        displayDropLocation(dropValue + "on top of " + "\"" + value + "\"");
                    }
                     
            /**  This is commented out for the basicdemo.html tutorial page.
                     **  If you add this code snippet back and delete the
                     **  "return false;" line, the list will accept drops
                     **  of type string.
                    // Perform the actual import. 
                    if (insert) {
                        listModel.add(index, data);
                    } else {
                        listModel.set(index, data);
                    }
                    return true;
            */
                    return false;
                }
                 
                public int getSourceActions(JComponent c) {
                	//System.out.println("Getting source actions");
                    return COPY;
                }
                 
//                protected Transferable createTransferable(JComponent c) {
//                	System.out.println("creating transferable");
//                    JList list = (JList)c;
//                    Object[] values = list.getSelectedValues();
//             
//                    StringBuffer buff = new StringBuffer();
//                    for (int i = 0; i < values.length; i++) {
//                        Object val = values[i];
//                        buff.append(val == null ? "" : val.toString());
//                        if (i != values.length - 1) {
//                            buff.append("\n");
//                        }
//                    }
//                    return new StringSelection(buff.toString());
//                }
                protected Transferable createTransferable(JComponent c) {
        	    	//System.out.println("creating Transf");
        	        JList list = (JList)c;
        	        //int index = list.getSelectedIndex();
        	        Song value = (Song)list.getSelectedValue();
        	        //System.out.println("Happy");
        	        return new SongTransferable(value);
        	    }
        	    class SongTransferable implements Transferable{
        	    	Song song;
        	    	public SongTransferable(Song son){
        	    		song=son;
        	    	}
					public Object getTransferData(DataFlavor f)
							throws UnsupportedFlavorException, IOException {
						if(f.getHumanPresentableName().equals(f.stringFlavor))
							return song.toString();
						return song;
					}

					@Override
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[]{new SongDataFlavor(),DataFlavor.stringFlavor};
					}
					@Override
					public boolean isDataFlavorSupported(DataFlavor f) {
						if(f.getHumanPresentableName().equals("Song")){
							return true;
						}
//						else if(f.getHumanPresentableName().equals(f.stringFlavor.getHumanPresentableName())){
//							return true;
//						}
						return false;
					}
        	    	
        	    }
                class SongDataFlavor extends DataFlavor{
        	    	public SongDataFlavor(){
        	    		super(Song.class,"Song");
        	   		}
        	    }
                
            });
        	
        }
        
        popMenu=new JPopupMenu();
        popup=new PopupListener(popMenu);
        list.addMouseListener(popup);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(600, 300));
	
		this.add(listScroller);
	}
	private void displayDropLocation(final String string) {
		return;
//		System.out.println("disp:"+string);
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JOptionPane.showMessageDialog(null, string);
//            }
//        });
    }
	
	
	protected void setPopmenu(String[] names,String[] ActCom){
		for(int r=0;r<names.length;r++){
			JMenuItem item=new JMenuItem(names[r]);
			item.setActionCommand(ActCom[r]);
			item.addActionListener(this);
			popMenu.add(item);
		}
		
		
	}
	public void updateList(ArrayList<Song> songs){
		listMod.clear();
		for(int rounds=0;rounds<songs.size();rounds+=1){
			listMod.addElement(songs.get(rounds));
		}
		list.setModel(listMod);
		list.repaint();
		this.revalidate();
		this.repaint();
	}
	abstract void updateList();
	
	class PopupListener extends MouseAdapter {
		JPopupMenu popup;
		
		PopupListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				list.setSelectedIndex(list.locationToIndex(e.getPoint()));
				if(list.getSelectedIndex()!=-1)
					popup.show(e.getComponent(),e.getX(), e.getY());
			}
		}
	}
}