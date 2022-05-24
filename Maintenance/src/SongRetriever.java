import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.json.simple.parser.*;

public class SongRetriever
{
	private static String encodeURIComponent(String s)
	{
		String result = null;
		try
		{
			result = URLEncoder.encode(s, "UTF-8")
			.replaceAll("\\+", "%20")
			.replaceAll("\\%21", "!")
			.replaceAll("\\%27", "'")
			.replaceAll("\\%28", "(")
			.replaceAll("\\%29", ")")
			.replaceAll("\\%7E", "~");
		}
		// This exception should never occur.
		catch (Exception e)
		{
			result = s;
		}
		return result;
	}  

	private static String toMD5(String plain)
	{
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(plain.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String parseResponse(String song, String input)
	{
		song = song.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
		input = input.toLowerCase().replaceAll("\\s", ""); 
		String regex = "title=" + song + ",duration=[0-9]{1,},url=(http://[a-zA-Z0-9]{1,}.vkontakte.ru/[a-zA-Z0-9]{1,}/audio/[a-zA-Z0-9]{1,}.mp3)";
		Pattern pattern = Pattern.compile(regex.replaceAll("\\s", ""));
		Matcher matcher = pattern.matcher(input);

		String jsonText = input;
		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory(){
			public List creatArrayContainer() {
				return new LinkedList();
			}

			public Map createObjectContainer() {
				return new LinkedHashMap();
			}
		};

		try{
			Map json = (Map)parser.parse(jsonText, containerFactory);
			Iterator iter = json.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry entry = (Map.Entry)iter.next();
				matcher = pattern.matcher(entry.getValue().toString().replaceAll("\\s", ""));
			}
		}
		catch(ParseException pe){
			System.out.println(pe);
		}
		
		if(matcher.find())
		{
			return matcher.group(1);
		}
		return "";
	}

	public static String getUrl(String artist, String song)
	{
		String track = artist + "  " + song;
		
		String url = "http://api.vk.com/api.php";
		String md5 = toMD5("327488" + "api_id=" + "525159" + "count=10format=jsonmethod=audio.searchq=" + track + "test_mode=1" + "g5vuj9EWFO");
		String data = "api_id=" + "525159" + "&method=audio.search&format=json&sig=" + md5 + "&test_mode=1&count=10&q=" +  encodeURIComponent(track);
		String search = url + "?" + data;
		try
		{
			URLConnection connection = new URL(search).openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			InputStream response = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
			String body = "";
			for(String line; (line = reader.readLine()) != null;)
			{
				body += line;
			}
			return parseResponse(song, body);
		}
		catch(Exception e)
		{
			return "";
		}
	}
}
