import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


public class PlayHandler {
	public static enum Mode
	{
		Shuffle,
		Dormant,
		Queue
	}
	private int playPercentage = 50;
	private PlayHandler.Mode mode = PlayHandler.Mode.Shuffle;
	private Song lastSong = null;

	public String getLastSongName()
	{
		if(lastSong != null)
			return lastSong.getName();
		else
			return "";
	}

	public void changeMode(PlayHandler.Mode newmode)
	{
		mode = newmode;
	}

	public void setPlayPercentage(int val)
	{
		playPercentage = val;
	}

	public int getPlayPercentage()
	{
		return playPercentage;
	}

	public void openBrowser(Song song)
	{
		if (song == null)
			return;

		lastSong = song;

		song.play();
		try {
			if(song.getDynamic())
			{
				String url = SongRetriever.getUrl(song.getArtist(), song.getName());
				if(url.length()!=0)
				{
					song.setUrl(url);
					@SuppressWarnings("unused")
					Process p = Runtime.getRuntime().exec("open " + song.getURL());
				}
				else
				{
					@SuppressWarnings("unused")
					Process p = Runtime.getRuntime().exec("open " + song.getURL());
				}
			}
			else {
				@SuppressWarnings("unused")
				Process p = Runtime.getRuntime().exec("open " + song.getURL());
			}
		} catch(IOException e1) { e1.printStackTrace(); }
	}

	public void playNext()
	{
		if(mode == Mode.Shuffle)
		{
			openBrowser(getRandomSong());
		}
		else if(mode == Mode.Queue && Backend.StaticThis.getSongQueue().getSongs().size()>0)
		{
			openBrowser(Backend.StaticThis.getSongQueue().getSong(0));
			Backend.StaticThis.getSongQueue().removeSong(0);
		}
		else if(mode == Mode.Dormant)
		{
			return;
		}
	}

	public void playNow(int selectedIndex)
	{
		openBrowser(Backend.StaticThis.getLibrary().getSong(selectedIndex));
	}

	public Song getRandomSong(){

		if (Backend.StaticThis.getLibrary().getSongs().size() == 0)
			return null;


		ArrayList<Song> queue = new ArrayList<Song>();
		for(Song isong : Backend.StaticThis.getLibrary().getSongs())
		{
			if(!(isong.getFrequency() == 0))
			{
				queue.add(isong);
			}
		}
		ArrayList<Song> random = new ArrayList<Song>();

		for(Song s : queue){

			long play = s.getLastPlayed();
			int i;
			for (i = 0; i < random.size() && random.get(i).getLastPlayed() < play; i++);
			random.add(i, s);
		}
		if (random.size() == 0)
			return null;

		Song song;

		int index = (int) (random.size()*Math.random() * (playPercentage / 100D));
		if (index >= random.size())
			index = random.size() - 1;
		song = random.get(index);

		return song;
	}
}
