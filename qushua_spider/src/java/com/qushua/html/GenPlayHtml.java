package com.qushua.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.qushua.html.vo.GameTypeTag;
import com.qushua.html.vo.HtmlGameType;
import com.qushua.spider.util.Constants;
import com.qushua.spider.util.GameType;
import com.qushua.spider.vo.Game;

public class GenPlayHtml {
	private static Connection conn;
	public static void genType(int type, List<Game> gamelist) {
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			URL path = GenPlayHtml.class.getResource("/");
			
			properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.INPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.getPath());//
			
			ve.init(properties); // 
			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			Map<String,List<GameTypeTag>> gametypetags = getGameTypeTags();
			List<HtmlGameType> gametypelist = GenListPage.getHtmlGameType(type);
			List<Game> allGame = getAllData(type);
			for(Game onegame : gamelist){
				Template template2 = ve.getTemplate("/tpls/play.html", "GBK");
				context.put("GameTypeName", GameType.ALL_TYPE.get(type));
				context.put("Game", onegame);
				context.put("GameTypeLink", GameType.ALL_TYPE_LINK.get(type));
				context.put("TopGameList", getRandGame(onegame, allGame));
				context.put("GameTypeTags",gametypetags);
				context.put("GameTypeList",gametypelist );
				StringWriter sw2 = new StringWriter();				
				template2.merge(context, sw2);
				genHtmlGameContent(sw2,type,onegame.getGameid());
				sw2.flush();
				sw2.close();
			}

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Game> getRandGame(Game game, final List<Game> gamelist){
		Map<String, Game> map = new HashMap<String, Game>();
		Random random = new Random();
		int max = gamelist.size() - 1;
		for (int i = 0; i < 9;) {
			
			int idx = Math.abs(random.nextInt(max));
			Game randgame = gamelist.get(idx);
			if(!map.containsKey(randgame.getGamename())){
				map.put(randgame.getGamename(), randgame);
				i++;
			}
		}
		List<Game> result = new ArrayList<Game>();
		for(Map.Entry<String, Game> entry : map.entrySet()){
			result.add(entry.getValue());
		}
		return result;
	}
	 
      
	private static void genHtmlGameContent(StringWriter sw, int type, int Gameid) throws IOException {
		String path = "/website/qushua/webroot/" + GameType.ALL_TYPE_LINK.get(type)+"/play/";
		try {
			File f = new File("/website/qushua/webroot/" + GameType.ALL_TYPE_LINK.get(type));
			f.mkdir();
		} catch (Exception e) {
		}
		try{
		File f = new File(path);
		f.mkdir();
		}catch(Exception e){
			
		}
		path +=  Gameid+".html";
		System.out.println("生成播放页面："+ Gameid);
		File htmlFile = new File(path);
		FileOutputStream fos = new FileOutputStream(htmlFile);
        PrintStream ps = new PrintStream(fos, true, "GBK");//这里我们就可以设置编码了
        ps.print(sw.toString());
        ps.flush();
        ps.close();
        fos.close();             

	}
	private static List<Game> getAllData(int type) throws SQLException{
    	List<Game> list = new ArrayList<Game>();
    	String sql = "select * from game where  gametype="+type;
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		Game one = new Game();
    		one.setGameid(rs.getInt("gameid"));
    		one.setGamename(rs.getString("gamename"));
    		one.setGametype(rs.getShort("gametype"));
    		one.setSmallpic(rs.getString("smallpic"));
    		one.setPic(rs.getString("pic"));
    		one.setSize(rs.getString("size"));
    		one.setSwfhost(rs.getString("swfhost"));
    		one.setTag(rs.getString("tag"));
    		one.setSwf(rs.getString("swf"));
    		one.setIntro(rs.getString("intro"));
    		one.setInputtime(rs.getString("inputtime"));
    		list.add(one);
    	}
    	return list;
    }
    private static List<Game> getData(int type) throws SQLException{
    	List<Game> list = new ArrayList<Game>();
		String sql = "select * from game where gameid>" + Constants.MAX_ID + " and gametype=" + type;
    	//String sql = "select * from game where  gametype="+type;
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		Game one = new Game();
    		one.setGameid(rs.getInt("gameid"));
    		one.setGamename(rs.getString("gamename"));
    		one.setGametype(rs.getShort("gametype"));
    		one.setSmallpic(rs.getString("smallpic"));
    		one.setPic(rs.getString("pic"));
    		one.setSize(rs.getString("size"));
    		one.setSwfhost(rs.getString("swfhost"));
    		one.setTag(rs.getString("tag"));
    		one.setSwf(rs.getString("swf"));
    		one.setIntro(rs.getString("intro"));
    		one.setInputtime(rs.getString("inputtime"));
    		list.add(one);
    	}
    	return list;
    }
    
    private static Map<String,List<GameTypeTag>> getGameTypeTags() throws SQLException{
    	Map<String,List<GameTypeTag>> map = new HashMap<String,List<GameTypeTag>>();
    	String sql = "select gtt.*, g.typename,g.pinyin  from gametype_tag gtt" +
    			     " left join gametype g on gtt.gametype=g.gametype where gtt.gametype<>9 order by gtt.gametype";
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		List<GameTypeTag> list = new ArrayList<GameTypeTag>();
    		if(map.containsKey(rs.getString("typename"))){
    			list = map.get(rs.getString("typename"));
    		}
    		if(list.size() >= 16){
    			continue;
    		}
    		GameTypeTag one = new GameTypeTag();
    		one.setSeqid(rs.getInt("seqid"));
    		one.setTag(rs.getString("tag"));
    		one.setGametype(rs.getShort("gametype"));
    		one.setGametypename(rs.getString("typename"));
    		one.setPinyin(rs.getString("pinyin"));
    		list.add(one);
    		
    		map.put(rs.getString("typename"), list);
    	}
    	return map;
    }
    public static void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "");
		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		GenPlayHtml.initConnection();
		
		for(Map.Entry<Integer, String> entry : GameType.ALL_TYPE.entrySet()){
			GenPlayHtml.genType(entry.getKey(),GenPlayHtml.getData(entry.getKey()));
		}
		
	}
}
