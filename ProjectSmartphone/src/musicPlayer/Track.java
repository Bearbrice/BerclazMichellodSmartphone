package musicPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

public class Track {
	
	private String title;
	

	private String path;
	
	public Track(String title, String path) {
		this.title=title;
		this.setPath(path);
	}
	
	//repris de la classe Photo
	public static String copy(File file)
	{
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		String newName = file.getName()+" "+timestamp.getTime();
		
		Path path2 = Paths.get("music/"+newName);
		try {
			Files.copy(file.toPath(),path2,StandardCopyOption.REPLACE_EXISTING );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
