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

import com.qushua.html.vo.GameTag;
import com.qushua.html.vo.GameTypeTag;
import com.qushua.html.vo.HtmlGameType;
import com.qushua.spider.util.GameType;
import com.qushua.spider.vo.Game;

public class GenTagPage {
	private static Connection conn;
	public static void getTagPage() {
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			URL path = GenTagPage.class.getResource("/");
			
			properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.INPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.getPath());//
			
			ve.init(properties); // 
			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			List<HtmlGameType> gametypelist = GenListPage.getHtmlGameType(0);
			
			List<GameTypeTag> list = getAllTag();
			List<Game> gamelist = getAllGame();
			for(GameTypeTag onetag : list){
				Template template2 = ve.getTemplate("/tpls/tag.html", "GBK");				 
				context.put("GameTypeTag", onetag.getTag());
				context.put("TopGameList",  getRandGame(gamelist));
				context.put("GameTypeList",gametypelist );
				context.put("GameByTag",getGameByTag(onetag.getTag()) );
				
				StringWriter sw2 = new StringWriter();				
				template2.merge(context, sw2);
				genHtmlGameContent(sw2,onetag.getSeqid());
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
	
	private static List<Game> getRandGame(final List<Game> gamelist){
		Map<String, Game> map = new HashMap<String, Game>();
		Random random = new Random();
		int max = gamelist.size() - 1;
		for (int i = 0; i < 32;) {
			
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
	 
      
	private static void genHtmlGameContent(StringWriter sw, int tagid) throws IOException {
		String path = "/website/qushua/webroot/tags/";
		try{
		File f = new File(path);
		f.mkdir();
		}catch(Exception e){
			
		}
		path +=  tagid+".html";
		File htmlFile = new File(path);
		FileOutputStream fos = new FileOutputStream(htmlFile);
        PrintStream ps = new PrintStream(fos, true, "GBK");//这里我们就可以设置编码了
        ps.print(sw.toString());
        ps.flush();
        ps.close();
        fos.close();             

	}
	private static List<Game> getAllGame() throws SQLException{
    	List<Game> list = new ArrayList<Game>();
    	String sql = "select * from game";
    	
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
    		one.setPinyin(GameType.ALL_TYPE_LINK.get((int)one.getGametype()));
    		list.add(one);
    	}
    	return list;
    }
    private static List<GameTypeTag> getAllTag() throws SQLException{
    	List<GameTypeTag> list = new ArrayList<GameTypeTag>();
    	String sql = "select * from gametype_tag";
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		GameTypeTag one = new GameTypeTag();
    		one.setGametype(rs.getInt("gametype"));
    		one.setTag(rs.getString("tag"));
    		one.setSeqid(rs.getInt("seqid"));
    		list.add(one);
    	}
    	return list;
    }
    
    private static List<GameTag> getGameByTag(String tag) throws SQLException{
    	List<GameTag> list = new ArrayList<GameTag>();
    	String sql = "select g.*,g2.gamename,g2.smallpic from game_tag g" +
    			     " left join game g2 on g.gameid=g2.gameid" +
    			     " where g.tag='"+tag+"'";
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		GameTag one = new GameTag();
    		one.setGameid(rs.getInt("gameid"));
    		one.setPinyin(rs.getString("pinyin"));
    		one.setGamename(rs.getString("gamename"));
    		one.setSmallpic(rs.getString("smallpic"));
    		if(list.size() > 101){
    			break;
    		}
    		list.add(one);    		
    	}
    	return list;
    }
    public static void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "");
		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		GenTagPage.initConnection();
		GenTagPage.getTagPage();		
	}
}
