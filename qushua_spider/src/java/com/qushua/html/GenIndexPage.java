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
import org.htmlparser.lexer.PageIndex;

import com.qushua.html.vo.GameTypeTag;
import com.qushua.html.vo.HtmlGameType;
import com.qushua.html.vo.PageInfo;
import com.qushua.spider.util.GameType;
import com.qushua.spider.vo.Game;
/**
 * 生成随机点击数 
 * insert into db_qushua.game_hit (gameid, hit) select gameid, round(round(rand(),5)*100000) from game;
 * @author ling
 *
 */
public class GenIndexPage {
	private static Connection conn;
	public static void genType() {
		Template template;
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			URL path = GenIndexPage.class.getResource("/");
			
			properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.INPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.getPath());//
			
			ve.init(properties); // 
			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			//类别总游戏，按游戏更新时间，按游戏热门前40个，类别标签
			Map<String, List<Game>> allGameMap = getData();
			List<GameTypeTag> tagList = getGameTage();
			List<HtmlGameType> gametypelist = getHtmlGameType(0);
			context.put("GenIndexPage", true);
			context.put("AllGameMap", allGameMap);
			context.put("GameTypeList",gametypelist );
			context.put("TagList",tagList );
			template = ve.getTemplate("/tpls/index.html", "GBK");

			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			genHtmlFile(sw);
			sw.flush();
			sw.close();
			
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
    public static List<HtmlGameType> getHtmlGameType(int type){
    	List<HtmlGameType> list = new ArrayList<HtmlGameType>();
    	for(Map.Entry<Integer, String> entry : GameType.ALL_TYPE.entrySet()){
    		HtmlGameType one = new HtmlGameType();
    		if(type == entry.getKey()){
    			one.setSelected(true);
    		}
    		one.setTypename(entry.getValue());
    		one.setTypeurl(GameType.ALL_TYPE_LINK.get(entry.getKey()));
    		list.add(one);
    	}
    	return list;
    }
	private static void genHtmlFile(StringWriter sw) throws IOException {
		String path = "/website/qushua/webroot/";
		try {
			File f = new File(path);
			f.mkdir();
		} catch (Exception e) {

		}
		path += "index.html";
		File htmlFile = new File(path);
		FileOutputStream fos = new FileOutputStream(htmlFile);
        PrintStream ps = new PrintStream(fos, true, "GBK");//这里我们就可以设置编码了
        ps.print(sw.toString());
        ps.flush();
        ps.close();
        fos.close();             

	}
	 
    private static Map<String, List<Game>> getData() throws SQLException{
    	Map<String, List<Game>> map = new HashMap<String, List<Game>>();
    	
    	for(Map.Entry<Integer, String> entry : GameType.ALL_TYPE_LINK.entrySet()){
    		String sql = "select gameid,gamename,gametype,smallpic,pic,size,swfhost,tag,swf,intro," +
		     " DATE_FORMAT(inputtime,'%Y-%m-%d %H:%m') as inputtime  from game where gametype="+entry.getKey() +
		     "  order by inputtime desc limit 16";
    		Statement st = conn.createStatement();
        	ResultSet rs = null;
        	rs = st.executeQuery(sql);
        	List<Game> list = new ArrayList<Game>();
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
        		one.setGametypename(GameType.ALL_TYPE.get(entry.getKey()));
        		list.add(one);
        	}
        	map.put(GameType.ALL_TYPE.get(entry.getKey()), list);
    	}
    	return map;
    }
     
    private static List<GameTypeTag> getGameTage() throws SQLException{
    	List<GameTypeTag> list = new ArrayList<GameTypeTag>();
    	String sql = "select seqid,tag from gametype_tag  limit 60";
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		GameTypeTag one = new GameTypeTag();
    		one.setSeqid(rs.getInt("seqid"));
    		one.setTag(rs.getString("tag"));
    		list.add(one);
    	}
    	return list;
    }
    public static void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "");
		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		GenIndexPage.initConnection();
		GenIndexPage.genType();
	}
}
