import java.util.ArrayList;
import java.util.Collections;
public class SongQueue extends Library {
	public void insertSong(int index1, int index2){
		if (index1 == index2)
			return;
		if (index2<lib.size()&&index1 < index2)
			index2--;
		Song song = (Song) lib.remove(index1);
		if(index2>=lib.size()){
			lib.add(song);
		}else
			lib.add(index2, song);
	}
	public void swapSongs(int index1, int index2){
		Collections.swap(lib, index1, index2);
	}
	public void moveUp(int index){
		if(index>0)
			Collections.swap(lib, index, index - 1);
	}
	public void moveDown(int index){
		if(index<lib.size()-1)
			Collections.swap(lib, index, index + 1);
	}
	public void moveToTop(int index){
		Song song = lib.remove(index);
		lib.add(0, song);
	}
	public void moveToBottom(int index){
		Song song = lib.remove(index);
		lib.add(song);
	}
	public void clearQueue(){
		lib.clear();
	}
	public void addSong(Song newSong){
		lib.add(newSong);
		//Backend.Frame.changeView(1);
		Backend.Frame.changeMode(1);
	}
	public ArrayList<Integer> getQueueIndexes()
	{
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (Song song : lib)
			indexes.add(Backend.StaticThis.getLibrary().lib.indexOf(song));
		return indexes;
	}
}