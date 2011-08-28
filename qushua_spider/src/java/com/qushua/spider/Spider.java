package com.qushua.spider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
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
public class Spider {
	private List<Spiderlink> spiderlinkList = new  ArrayList<Spiderlink>();
	private String charset = "GBK";
	private Connection conn;
	private PreparedStatement ps;
	private PreparedStatement psupdate;
	
	private void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://localhost/db_qushua?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true", "root", "");
		String sql = "insert ignore into db_qushua.game (gametype, gamename, pic,       swfhost, swf," +
				                                       " intro,    tag,      inputtime, size, smallpic)" +
				     " values(?,?,?,?,?, ?,?,?,?,?)";
		String update_sql = "update spiderlink set spiderflag=1 where link=?";
		this.ps = this.conn.prepareStatement(sql);
		this.psupdate = this.conn.prepareStatement(update_sql);
	}
	 
	private void initReadyLink() throws SQLException{
		String sql = "select gametype,link,page from spiderlink where spiderflag=0 ";
		Statement st = null;
		ResultSet rs = null;
		try{
			st = this.conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Spiderlink data = new Spiderlink();
				data.setGametype(rs.getShort("gametype"));
				data.setLink(rs.getString("link"));
				data.setPage(rs.getInt("page"));
				this.spiderlinkList.add(data);
			}
		}finally{
			if(null != st){
				try{
					st.close();
				}catch(Exception e){}
			}
		}
	}
	 
	private void intoDbHelper(Game game){
		if(null == game || game.getGamename() == null || game.getGamename().trim().length()==0
			|| game.getSwf() == null || game.getSwf().trim().length()==0){
				return;
		}
		try {
			ps.setShort(1, game.getGametype());
			ps.setString(2, game.getGamename().trim());
			ps.setString(3, game.getPic().trim());
			ps.setString(4, game.getSwfhost().trim());
			ps.setString(5, game.getSwf().trim());
			ps.setString(6, game.getIntro().trim());
			ps.setString(7, game.getTag());
			if(StringUtils.isNotBlank(game.getInputtime())){
				ps.setString(8, game.getInputtime());
			}else{
				ps.setDate(8, new Date(new java.util.Date().getTime()));
			}
			ps.setString(9, game.getSize());
			ps.setString(10, game.getSmallpic());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateGameTypeName() throws SQLException{
		String sql = "update game gt set gt.gametypename=(select typename from gametype where gametype=gt.gametype)," +
				     " gt.pinyin=(select pinyin from gametype where gametype=gt.gametype)";
		Statement st  = this.conn.createStatement();
		st.execute(sql);
		 
	}
	private void parseGameAllInfo(String url,Spiderlink slink){
		//http://www.4399.com/flash_fl/2_1.htm
		try {
			String listHtml = SpiderUtil.getContent(url, charset);
			String strFlag = "<div id=\"tab1\">";
			int bidx = listHtml.indexOf(strFlag);
			int eidx = 0;
			if (bidx > 0) {
				eidx = listHtml.indexOf("</div>", bidx);
			}
			if(eidx <= bidx || bidx < 0){
				System.out.println("The "+url+" is not found.");
				return;				
			}
			listHtml = listHtml.substring(bidx+strFlag.length(),eidx);
			Parser parser = Parser.createParser(listHtml, "GBK");
			NodeList nodeList = new NodeList();
			nodeList = parser.extractAllNodesThatMatch(new NodeFilter() {
				public boolean accept(Node node) {
					if (node instanceof LinkTag)
						return true;
					else{
						return false;
					}
				}
			});
			//解析Li中的小图片，链接
		     for (int i = 0; i < nodeList.size(); i++) {
				try {
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					if(node.getChild(0) instanceof ImageTag){
						
						Game game = new Game();		
						game.setGametype(slink.getGametype());
						game.setSmallpic(((ImageTag)node.getChild(0)).getImageURL());
						game.setGamename(((ImageTag)node.getChild(0)).getAttribute("alt"));
						String contenturl = "http://www.4399.com" + node.getLink();
						String content = SpiderUtil.getContent(contenturl, charset);
						game.setPic(this.getBigImg(content));
						game.setInputtime(this.getUpdateDate(content));
						game.setSize(this.getFlashSize(content));
						game.setTag(this.getFlashTag(content));
						String playurl = this.getPlayUrl(content);
						if(this.getGameSwfAndIntro(playurl, game)){
							if(null == game.getSwf() 
									|| game.getSwf().trim()==""
									|| game.getSwf().indexOf("htm")>0){
								continue;
							}
							if (game.getSwf().lastIndexOf(".swf") == game.getSwf().length() - 4) {
								this.intoDbHelper(game);
							}
						}						
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}  
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	private boolean getGameSwfAndIntro(final String url, final Game game)
			throws ParserException {

		String content = SpiderUtil.getContent(url, charset);

		int bidx = content.indexOf("/upload_swf/");
		if (bidx > 0) {
			int eidx = content.indexOf(".swf", bidx);
			if (eidx > 0) {
				String swfhost = this.getSwfHost(content);
				if (swfhost == null || swfhost.trim().length() == 0) {
					return false;
				}
				game.setSwf(content.substring(bidx, eidx + 4));
				game.setSwfhost(swfhost);
				game.setPic(this.getBigImg(content));
				Parser parser = new Parser();
				
				NodeList nodeList = new NodeList();
				parser.setInputHTML(content);
				nodeList = parser.extractAllNodesThatMatch(new NodeFilter() {
					public boolean accept(Node node) {
						if (node instanceof Div && null != ((Div)node).getAttribute("id")
								&& ((Div)node).getAttribute("id").trim().equalsIgnoreCase("gameIntro"))
							return true;
						else{
							return false;
						}
					}
				});
				
				for (int i = 0; i < nodeList.size(); i++) {
					Div node = (Div) nodeList.elementAt(i);
					game.setIntro(game.getIntro() + node.getChildrenHTML());
				}
				return true;
			}
		}
		return false;
	}
	private String getSwfHost(String content){
		//<SCRIPT LANGUAGE="JavaScript" src="/js/server12.gif"></SCRIPT>
		if(content.indexOf("/js/server")>0){
			int bidx = content.indexOf("/js/server");
			if(bidx > 0){
				int eidx  = content.indexOf("\"></SCRIPT>",bidx);
				if(eidx <= 0){
					eidx = content.indexOf("\"></script>",bidx);
				}
				if(eidx > bidx && eidx-bidx < 100)
				return "http://www.4399.com" + content.substring(bidx,eidx);
			}
		}
		return "";

	}
	private String getPlayUrl(String content){
		int bidx = content.indexOf("<div class=\"listart\">");
		int eidx = 0;
		if(bidx > 0){
			eidx = content.indexOf("</div>",bidx);
		}
		if(eidx > bidx && bidx > 0){
			String aplay = content.substring(bidx,eidx);
			bidx = aplay.indexOf("href=\"");
			eidx = aplay.indexOf("?",bidx);
			if(eidx <=0 ){
				eidx = aplay.indexOf("\"",bidx+6);
			}
			if(eidx > bidx){
				return "http://www.4399.com" + aplay.substring(bidx+6,eidx);
			}
		}
		return "";		
	}
	private String getFlashSize(String content){
		int bidx = content.indexOf("大小:</span>");
		int eidx = 0;
		if(bidx > 0){
			eidx = content.indexOf("</li>",bidx);
		}
		if(eidx > bidx && bidx > 0){
			return content.substring(bidx+12,eidx);
		}
		return "";		
	}
	private String getUpdateDate(String content){
		int bidx = content.indexOf("日期:</span>");
		int eidx = 0;
		if(bidx > 0){
			eidx = content.indexOf("</li>",bidx);
		}
		if(eidx > bidx && bidx > 0){
			return content.substring(bidx+12,eidx);
		}
		return "";		
	}
	private String getFlashTag(String content){
		int bidx = content.indexOf(">专题:</span>");
		int eidx = 0;
		if(bidx > 0){
			eidx = content.indexOf("</li>",bidx);
		}
		if(eidx > bidx && bidx > 0){
			String taghtml = content.substring(bidx+11,eidx);
			Parser parser = Parser.createParser(taghtml, charset);
			try {
				NodeList nodeList = parser.extractAllNodesThatMatch(new NodeFilter() {
					public boolean accept(Node node) {
						if (node instanceof LinkTag)
							return true;
						else{
							return false;
						}
					}
				});
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < nodeList.size(); i++) {
					LinkTag node = (LinkTag) nodeList.elementAt(i);
					sb.append(node.getLinkText()).append(" ");
				}
				return sb.toString();
			} catch (ParserException e) {
				e.printStackTrace();
			}
			
		}
		return "";		
	}
	public String getBigImg(String content){
		int idx = content.indexOf("http://imga999.4399.com/upload_pic");
		if(idx > 0){
			int edx = content.indexOf("\"", idx);
			if(edx > idx){
				return content.substring(idx, edx);
			}
		}
		return "";
	}

	public void run() throws SQLException, ClassNotFoundException {
		this.initConnection();
		this.initReadyLink();
		for (Spiderlink data : this.spiderlinkList) {
			String url = data.getLink().substring(0, data.getLink().length() - 5);// 更新缩略图
			for (int i = 1; i <= data.getPage(); i++) {
				this.parseGameAllInfo(url+i+".htm",data);
			}
		}
		this.updateGameTypeName();

	}
	public static void main(String []args){
		Spider main = new Spider();
		try {
			main.run();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
