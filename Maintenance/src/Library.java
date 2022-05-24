import java.util.ArrayList;

public class Library {
	protected ArrayList<Song> lib;
	protected int selectIndex = 0;

	public Library() {
		lib = new ArrayList<Song>();
	}

	public ArrayList<Song> getSongs() {
		return lib;
	}

	public void loadSongs(ArrayList<Song> songs){
		lib = songs;
	}
	
	public void addSong(Song newSong) {
		int counter = 0;
		boolean loop = true;

		try {
			while (loop && counter < lib.size()) {
				if (newSong.getName().compareToIgnoreCase(lib.get(counter).getName())<0) {
					loop = false;
				} else {
					counter++;
				}
			}
			if (counter < 0)
				counter = 0;
			lib.add(counter, newSong);
		} catch (Exception e) {
			lib.add(0, newSong);
		}
	}

	public Song removeSong(int index) {
		return lib.remove(index);
	}

	public Song getSong(int index) {
		return lib.get(index);
	}
	/*
	 * public void selectSong(int index){ selectIndex=index; } public int
	 * getSelectedSongIndex(){ return selectIndex; } public Song
	 * getSelectedSong(){ return lib.get(selectIndex); }
	 */
}
