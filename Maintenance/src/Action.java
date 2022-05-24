import java.util.ArrayList;

public class Action 
{
	public static enum Type
	{
		CreateSong, //String name, String url
		ClearQueue, //null
		ChangeMode, //Mode mode
		EditSong, //String name, String url, int index
		MoveSongUp, //int index
		MoveSongDown, //int index
		MoveSongTop, //int index
		MoveSongBottom, //int index
		AddSongToQueue, //int index
		InsertSong, //int movesong, int insertindex
		PlayNext, //Mode mode, View view, (int index), 
		PlayNow, //int index
		SaveSettings, //null
		SavePlayPercentage, //int percentage
		Remove, //int index, boolean (true = library, false = queue)
		AddAllToQueue,
		AddFrequentToQueue,
		SwapSongs
	}
	
	private Type type = null;
	private ArrayList<Object> data = null;
	
	public Action(Type type, ArrayList<Object> data)
	{
		this.type = type;
		this.data = data;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public ArrayList<Object> getData()
	{
		return data;
	}
}