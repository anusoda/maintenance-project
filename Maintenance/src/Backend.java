import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
public class Backend {
	public static Backend StaticThis;
	private XMLHandler classXHandler;
	private Settings classSettings;
	private SongQueue classQueue;
	private Library classLiberry;
	private PlayHandler classPHandler;
	static Header Frame;

	public Backend(XMLHandler xHandler, Settings settings, SongQueue queue, Library liberry, PlayHandler pHandler) {
		classXHandler = xHandler;
		classSettings = settings;
		classQueue = queue;
		classLiberry = liberry;
		classPHandler = pHandler;
		StaticThis = this;
	}

	public XMLHandler getXMLHandler() {
		return classXHandler;
	}

	public Settings getSettings() {
		return classSettings;
	}

	public SongQueue getSongQueue() {
		return classQueue;
	}

	public Library getLibrary() {
		return classLiberry;
	}

	public PlayHandler getPlayHandler() {
		return classPHandler;
	}

	public static void main(String str[]){
		XMLHandler xHandler = new XMLHandler();
		SongQueue queue = new SongQueue();
		Library liberry = new Library();
		PlayHandler pHandler = new PlayHandler();
		Settings settings = null;
		try
		{	
			settings = xHandler.LoadSettings();
			if(settings != null)
			{
				queue.loadSongs(settings.queue);
				liberry.loadSongs(settings.library);
				pHandler.setPlayPercentage((int)settings.playPercentage);
			}
		}
		catch(Exception e)
		{	
			queue.loadSongs(new ArrayList<Song>());
			liberry.loadSongs(new ArrayList<Song>());
			pHandler.setPlayPercentage(50);
			xHandler.clearXML();
		}
		
		Backend backend = new Backend(xHandler, settings, queue, liberry, pHandler);
		Frame = new Header();
		pHandler.setHeader(Frame);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.pack();
		Frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public static void actionRecieved(Action e) {
		if (e.getType() == Action.Type.CreateSong) {
			String name, URL;
			System.out.println(e.getData().size());
			name = (String) e.getData().get(0);
			URL = (String) e.getData().get(1);
			int frequency = (Integer) e.getData().get(2);
			boolean dynamic = (Boolean) e.getData().get(3);
			Song song = new Song(name, URL, frequency, dynamic);
			if(dynamic)
				song.setArtist((String) e.getData().get(4));
			StaticThis.classLiberry.addSong(song);
		} else if(e.getType() == Action.Type.ClearQueue){
			StaticThis.classQueue.clearQueue();
		} else if(e.getType() == Action.Type.EditSong){
			String name = (String) e.getData().get(0);
			String URL = (String) e.getData().get(1);
			int index=(Integer) e.getData().get(2);
			System.out.println("index: "+index);
			int frequency = (Integer) e.getData().get(3);
			boolean dynamic = (Boolean) e.getData().get(4);
			Song song;
			if (index==-1)
				song = StaticThis.classLiberry.getSong((Song) e.getData().get(5));
			else
				song = StaticThis.classLiberry.getSong(index);
			song.setName(name);
			song.setUrl(URL);
			song.setFrequency(frequency);
			song.setDynamic(dynamic);
			if(dynamic)
				song.setArtist((String) e.getData().get(5));
		} else if(e.getType() == Action.Type.AddSongToQueue){
			int index=(Integer) e.getData().get(0);
			StaticThis.classQueue.addSong(StaticThis.classLiberry.getSong(index));
		} else if(e.getType() == Action.Type.PlayNext){
			StaticThis.classPHandler.playNext();
			Header.CurrSong.setText(Backend.StaticThis.getPlayHandler().getLastSongName());
		} else if(e.getType() == Action.Type.PlayNow){
			int index = (Integer) e.getData().get(0);
			StaticThis.classPHandler.playNow(index);
			Header.CurrSong.setText(Backend.StaticThis.getPlayHandler().getLastSongName());
		} else if(e.getType() == Action.Type.SaveSettings){
			StaticThis.classSettings = new Settings(StaticThis.classQueue.getQueueIndexes(), StaticThis.classLiberry.getSongs(), null, StaticThis.classPHandler.getPlayPercentage());
			StaticThis.classXHandler.SaveSettings(StaticThis.classSettings);
		} else if(e.getType() == Action.Type.SavePlayPercentage){
			Integer a = (Integer)e.getData().get(0);
			StaticThis.classPHandler.setPlayPercentage(a);
		} else if(e.getType() == Action.Type.ChangeMode){
			PlayHandler.Mode mode = (PlayHandler.Mode)e.getData().get(0);
			StaticThis.classPHandler.changeMode(mode);
		} else if(e.getType() == Action.Type.Remove){
			Integer index = (Integer)e.getData().get(0);
			Boolean from = (Boolean)e.getData().get(1);
			if (from)
			{
				Song song = StaticThis.classLiberry.removeSong(index);
				int removeIndex = StaticThis.classQueue.getSongs().indexOf(song);
				while(removeIndex != -1)
				{
					StaticThis.classQueue.removeSong(removeIndex).getName();
					removeIndex = StaticThis.classQueue.getSongs().indexOf(song);
				}
			}
			else
				StaticThis.classQueue.removeSong(index);
		} else if(e.getType() == Action.Type.MoveSongUp){
			Integer index = (Integer)e.getData().get(0);
			StaticThis.classQueue.moveUp(index);
		}else if(e.getType() == Action.Type.MoveSongDown){
			Integer index = (Integer)e.getData().get(0);
			StaticThis.classQueue.moveDown(index);
		}else if(e.getType() == Action.Type.MoveSongTop){
			Integer index = (Integer)e.getData().get(0);
			StaticThis.classQueue.moveToTop(index);
		}else if(e.getType() == Action.Type.MoveSongBottom){
			Integer index = (Integer)e.getData().get(0);
			StaticThis.classQueue.moveToBottom(index);
		}else if(e.getType() == Action.Type.InsertSong){
			Integer get = (Integer)e.getData().get(0), insertindex = (Integer)e.getData().get(1);
			StaticThis.classQueue.insertSong(get, insertindex);
		}else if(e.getType() == Action.Type.AddFrequentToQueue){
			List<Song> list = (List<Song>) StaticThis.classLiberry.lib.clone();
			Iterator<Song> i = list.iterator();
			while (i.hasNext())
				if (i.next().getFrequency() != 2)
					i.remove();
			if (list.size() > 0)
				Frame.changeMode(1);
			while (list.size() != 0)
				StaticThis.classQueue.lib.add(list.remove((int)(Math.random() * list.size())));
		}else if(e.getType() == Action.Type.SwapSongs){
			Integer index1 = (Integer)e.getData().get(0), index2 = (Integer)e.getData().get(1);
			StaticThis.classQueue.swapSongs(index1, index2);
		}
		StaticThis.classSettings = new Settings(StaticThis.classQueue.getQueueIndexes(), StaticThis.classLiberry.getSongs(), null, StaticThis.classPHandler.getPlayPercentage());
		StaticThis.classXHandler.SaveSettings(StaticThis.classSettings);
	}
}
