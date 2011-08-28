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
import com.qushua.html.vo.PageInfo;
import com.qushua.spider.util.GameType;
import com.qushua.spider.vo.Game;
/**
 * 生成随机点击数 
 * insert into db_qushua.game_hit (gameid, hit) select gameid, round(round(rand(),5)*100000) from game;
 * @author ling
 *
 */
public class GenListPage {
	private static int PAGE_SIZE = 66;
	private static Connection conn;
	public static void genType(int type, List<Game> gamelist, int orderbyflag) {
		Template template;
		try {
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			URL path = GenListPage.class.getResource("/");
			
			properties.setProperty(Velocity.OUTPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.INPUT_ENCODING, "GBK");//  
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path.getPath());//
			
			ve.init(properties); // 
			Velocity.init();
			
			VelocityContext context = new VelocityContext();
			//类别总游戏，按游戏更新时间，按游戏热门前40个，类别标签
			List<Game> topGameList = getTopGame(type);
			List<GameTypeTag> tagList = getGameTage(type);
			PageInfo pageinfo = new PageInfo();
			pageinfo.setAllnum(gamelist.size());
			List<HtmlGameType> gametypelist = getHtmlGameType(type);
			for (int i = 0; i < gamelist.size() / PAGE_SIZE + 1; i++){
				context.put("OrderByFlag", orderbyflag);
				if(gamelist.size()>1505){
					context.put("AllGameList", gamelist.subList(0, 1505));
				}else{
					context.put("AllGameList", gamelist);
				}
							
				context.put("GameTypeName", GameType.ALL_TYPE.get(type));
				int toIndex = (i+1)*PAGE_SIZE >= gamelist.size() ? gamelist.size():(i+1)*PAGE_SIZE;				
				context.put("GameList", gamelist.subList(i*PAGE_SIZE, toIndex));
				context.put("TopGameList", topGameList);
				pageinfo.setCurrentpage(i+1);
				context.put("PageInfo", pageinfo);
				context.put("GameTypeList",gametypelist );
				context.put("TagList",tagList );
				template = ve.getTemplate("/tpls/list.html", "GBK");

				StringWriter sw = new StringWriter();
				template.merge(context, sw);
				genHtmlFile(sw,type,i,orderbyflag);
				sw.flush();
				sw.close();
			}
			
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
	private static void genHtmlFile(StringWriter sw, int type, int pagenum, int orderbyflag) throws IOException {
		String path = "/website/qushua/webroot/" + GameType.ALL_TYPE_LINK.get(type);
		try {
			File f = new File(path);
			f.mkdir();
		} catch (Exception e) {

		}
		if (orderbyflag == 0) {
			if (pagenum == 0) {
				path += "index.html";
			} else {
				path += "index_" + (pagenum+1) + ".html";
			}
		} else {
			if (pagenum == 0) {
				path += "hot.html";
			} else {
				path += "hot_" + (pagenum+1) + ".html";
			}
		}
		File htmlFile = new File(path);
		FileOutputStream fos = new FileOutputStream(htmlFile);
        PrintStream ps = new PrintStream(fos, true, "GBK");//这里我们就可以设置编码了
        ps.print(sw.toString());
        ps.flush();
        ps.close();
        fos.close();             

	}
	 
    private static List<Game> getData(int type, int orderbyflag) throws SQLException{
    	List<Game> list = new ArrayList<Game>();
    	String sql = "select gameid,gamename,gametype,smallpic,pic,size,swfhost,tag,swf,intro," +
    			     " DATE_FORMAT(inputtime,'%Y-%m-%d %H:%m') as inputtime  from game where gametype="+type +
    			     " order by inputtime desc";
    	if(orderbyflag == 1){
    		sql = "select g.gameid,g.gamename,g.gametype,g.smallpic,g.pic,g.size,g.swfhost,g.tag,g.swf,g.intro," +
    			  " DATE_FORMAT(inputtime,'%Y-%m-%d %H:%m') as inputtime  from game g" +
    			  " left join game_hit gh on g.gameid=gh.gameid" +
    			  " where g.gametype="+type + " order by gh.hit desc";
    	}
    	
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
    private static List<Game> getTopGame(int type) throws SQLException{
    	List<Game> list = new ArrayList<Game>();
    	String sql = "select g.gameid, g.gamename from game g" +
    			     " left join game_hit gh on g.gameid=gh.gameid" +
    			     " where g.gametype="+type + " order by gh.hit desc limit 48";
    	
    	Statement st = conn.createStatement();
    	ResultSet rs = null;
    	rs = st.executeQuery(sql);
    	while(rs.next()){
    		Game one = new Game();
    		one.setGameid(rs.getInt("gameid"));
    		one.setGamename(rs.getString("gamename"));
    		list.add(one);
    	}
    	return list;
    }
    private static List<GameTypeTag> getGameTage(int type) throws SQLException{
    	List<GameTypeTag> list = new ArrayList<GameTypeTag>();
    	String sql = "select seqid,tag from gametype_tag " +    			     
    			     " where  gametype="+type;
    	
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
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_qushua?useUnicode=true&characterEncoding=GBK&allowMultiQueries=true", "root", "");
		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		GenListPage.initConnection();
		
		for(Map.Entry<Integer, String> entry : GameType.ALL_TYPE.entrySet()){
			GenListPage.genType(entry.getKey(),GenListPage.getData(entry.getKey(),0),0);
			GenListPage.genType(entry.getKey(),GenListPage.getData(entry.getKey(),1),1);
		}
	}
}
