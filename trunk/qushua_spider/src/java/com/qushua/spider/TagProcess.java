package com.qushua.spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import java.sql.Statement;
import com.qushua.spider.util.SpiderUtil;
import com.qushua.spider.vo.*;
/**
 * 1、从列表页获取各个游戏的内容页链接和游戏名称
 * 2、从内容页获取play页链接和游戏图片，游戏大小
 * 3、从play页获取flash地址和server，游戏介绍
 * @author ling
 *
 */
public class TagProcess {
	private List<String> list = new ArrayList<String>();
	private String charset = "GBK";
	private Connection conn;
	private PreparedStatement ps;
	
	private void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "2ycf4jd");
		String sql = "insert ignore into db_qushua.game_tag (tag, gameid) values(?,?)";
		this.ps = this.conn.prepareStatement(sql);
	}
	 
	private void tag() throws SQLException{
		String sql = "select gametype, gameid,tag from `db_qushua`.`game` where tag is not null and tag <>''";
		Statement st = null;
		ResultSet rs = null;
		try{
			st = this.conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				 String gametag = rs.getString("tag");
				 int gameid = rs.getInt("gameid");
				 int gametype = rs.getInt("gametype");
				 this.tagIntoDb(gameid, gametag);
				 this.tagIntoGameTypeTagDb(gametype, gametag);
			}
		}finally{
			if(null != st){
				try{
					st.close();
				}catch(Exception e){}
			}
		}
	}
	
	private void tagIntoDb(int gameid , String gametag){
		String []tags = gametag.split(" ");
		if(null != tags && tags.length > 0){
			for(String one : tags){
				if(null != one && one.trim().length() > 0){
					one = one.trim();
					try {
						ps.setString(1, one);
						ps.setInt(2, gameid);
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	
	private void tagIntoGameTypeTagDb(int gametype , String gametag) throws SQLException{
		String sql = "insert ignore into db_qushua.gametype_tag (tag, gametype) values(?,?)";
		PreparedStatement ps = this.conn.prepareStatement(sql);
		String []tags = gametag.split(" ");
		if(null != tags && tags.length > 0){
			for(String one : tags){
				if(null != one && one.trim().length() > 0){
					one = one.trim();
					try {
						ps.setString(1, one);
						ps.setInt(2, gametype);
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void updateGameTypeName() throws SQLException{
		String sql = "update game_tag gt set gt.gametype=(select gametype from game where gameid=gt.gameid)," +
				     " pinyin=(select pinyin from game where gameid=gt.gameid)";
		Statement st  = this.conn.createStatement();
		st.execute(sql);
		 
	}
	 
	public void run() {
		try {
			this.initConnection();
			this.tag();
			this.updateGameTypeName(); 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.conn != null) {
				try {
					this.conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
	public static void main(String []args){
		TagProcess main = new TagProcess();
		main.run();
	}
}
