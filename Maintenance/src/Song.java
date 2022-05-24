
public class Song {
	private String songName;
	private String songURL;
	private int frequent; //1 play, 0 dont play, 2 frequent
	private long lastPlayed=System.currentTimeMillis();
	private Boolean dynamic = false;
	private String artist = " ";
	Song(String name,String URL, int f, boolean d){
		songName=name;
		songURL=URL;
		frequent = f;
		dynamic = d;
	}
	Song(String name, String URL, long lastplayed, int f, boolean d)
	{
		songName = name;
		songURL = URL;
		lastPlayed = lastplayed;
		frequent = f;
		dynamic = d;
	}
	public void setDynamic(boolean d){
		dynamic = d;
	}
	public boolean getDynamic(){	
		return dynamic;
	}
	public String getArtist(){
		return artist;
	}
	public void setArtist(String a){
		artist = a;
	}
	public void setFrequency(int f){
		frequent = f;
	}
	public int getFrequency(){
		return frequent;
	}
	public void setName(String name){
		songName = name;
	}
	public void setUrl(String url){
		songURL = url;
	}
	public String getName(){
		return songName;
	}
	public String getURL(){
		return songURL;
	}
	public void play(){
		lastPlayed=System.currentTimeMillis();
	}
	public long getLastPlayed(){
		return lastPlayed;
	}
	public void setLastPlayed(long last){
		lastPlayed=last;
	}
	public String toString(){
		return songName;
	}
}
