import java.util.ArrayList;


public class Settings {
	Settings(ArrayList<Integer> indexes, ArrayList<Song> lib, ArrayList<Song> queue, double playPercent){
		setPlaypercentage(playPercent);
		setIndexes(indexes);
		setLibrary(lib);
		setSongQueue(queue);
	}
	double playPercentage=0;
	ArrayList<Song> queue =new ArrayList<Song>();
	ArrayList<Song> library =new ArrayList<Song>();
	ArrayList<Integer> indexes = new ArrayList<Integer>();
	public double getPlayPercentage(){
		return playPercentage;
	}
	public void setPlaypercentage(double d){
		playPercentage=d;
	}
	public void setSongQueue(ArrayList<Song> songqueue){
		queue=songqueue;
	}
	public ArrayList<Song> getSongQueue(){
		return queue;
	}
	public void setIndexes(ArrayList<Integer> indexes)
	{
		this.indexes = indexes;
	}
	public ArrayList<Integer> getIndexes()
	{
		return indexes;
	}
	public ArrayList<Song> getLibrary(){
		return library;
	}
	public void setLibrary(ArrayList<Song> lib){
		library=lib;
	}
}
