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
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.qushua.html.vo.HtmlGameType;
import com.qushua.spider.util.Constants;
import com.qushua.spider.util.GameType;
import com.qushua.spider.vo.Game;

public class GenContentHtml {
	private static Connection conn;
	public static void genType(int type, List<Game> gamelist) {
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			URL path = GenContentHtml.class.getResource("/");
			
			properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.INPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.getPath());//
			
			ve.init(properties); // 
			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			List<HtmlGameType> gametypelist = GenListPage.getHtmlGameType(type);
			List<Game> allGame = getAllData(type);
			for(Game onegame : gamelist){
				Template template2 = ve.getTemplate("/tpls/content.html", "GBK");
				context.put("GameTypeName", GameType.ALL_TYPE.get(type));
				context.put("Game", onegame);
				context.put("GameTypeLink", GameType.ALL_TYPE_LINK.get(type));
				context.put("TopGameList",  getRandGame(onegame, allGame));
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
	 
      
	private static void genHtmlGameContent(StringWriter sw, int type, int Gameid) throws IOException {
		String path = "/website/qushua/webroot/" + GameType.ALL_TYPE_LINK.get(type);
		try{
		File f = new File(path);
		f.mkdir();
		}catch(Exception e){
			
		}
		path +=  Gameid+".html";
		File htmlFile = new File(path);
		FileOutputStream fos = new FileOutputStream(htmlFile);
        PrintStream ps = new PrintStream(fos, true, "GBK");//这里我们就可以设置编码了
        System.out.println("=== generate the content html page ："+ Gameid);
        ps.print(sw.toString());
        ps.flush();
        ps.close();
        fos.close();             

	}
	private static List<Game> getAllData(int type) throws SQLException{
    	List<Game> list = new ArrayList<Game>();
    	String sql = "select * from game where gametype="+type;
    	
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
    	String sql = "select * from game where gameid>"+Constants.MAX_ID+" and  gametype="+type;
    	
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
    public static void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "");
		
	}
    public static void initMaxId() throws SQLException, ClassNotFoundException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	Calendar now = Calendar.getInstance();
    	now.add(Calendar.DAY_OF_MONTH, -3);
    	Statement st = conn.createStatement();
		String sql = "select max(gameid) from game where inputtime<'" + sdf.format(now.getTime()) + "'";
    	ResultSet rs = st.executeQuery(sql);
    	if(null != rs && rs.next()){
    		Constants.MAX_ID = rs.getInt(1);
    	}
    }
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		GenContentHtml.initConnection();
		if(Constants.MAX_ID <= 0){
			GenContentHtml.initMaxId();
		}
		for(Map.Entry<Integer, String> entry : GameType.ALL_TYPE.entrySet()){
			GenContentHtml.genType(entry.getKey(),GenContentHtml.getData(entry.getKey()));
		}
		
	}
}
