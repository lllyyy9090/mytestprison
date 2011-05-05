package com.alibaba.test.oracle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.spi.orbutil.fsm.State;

public class ReadTableInfo {
	private Connection conn ;
    public void initDb(){
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@(description=(ADDRESS_LIST =(LOAD_BALANCE=OFF)(FAILOVER=ON)(ADDRESS = (PROTOCOL = TCP)(HOST = 10.20.147.1)(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.20.147.1)(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.20.147.1)(PORT = 1521)))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = itbutest)(failover_mode=(type=select)(method=basic))))","c2ctest","c2ctest");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public void showTableInfo() throws SQLException, IOException{
    	Statement st = conn.createStatement();
    	Statement st2 = conn.createStatement();
    	Statement st3 = conn.createStatement();
    	ResultSet rs = st.executeQuery("select table_name from all_tables where owner='C2CTEST' order by table_name");
    	List<String> tableNames = new ArrayList<String>();
    	Map<String, List<ColumnInfo>> map = new HashMap<String, List<ColumnInfo>>();
    	while(rs.next()){
    		tableNames.add(rs.getString(1));
    	}
    	File f = new File("/out.txt");
    	BufferedWriter bw = new BufferedWriter(new FileWriter(f));
    	String header = "h3. 一、修订记录\n" +
    			        "|| 时间 || 人物 || 内容 || 数量级 || 是否发布 || 是否重大变更 ||\n" +
    			        " | 2011-05-05 | 刘凌 | 添加表文档 |  |  |  |\n" +
    			        "h3. 二、表说明\n" + 
    			        "h3. 三、表结构明细\n" +
    			        "|| 字段 || 字段类型 || 描述 || 修订内容 || 修改时间 ||\n";
    	
    	for(String tablename : tableNames){
    		String sql = "select  a.column_name, a.data_type , b.comments from user_tab_columns a " +
    				     " left join user_col_comments  b on b.column_name=a.column_name and b.table_name='"+tablename+"' " +
    				     " where a.table_name = '"+tablename+"'  order by a.column_id";
    		ResultSet rs1 = st2.executeQuery(sql);
    		List<ColumnInfo> list = new ArrayList<ReadTableInfo.ColumnInfo>();
    		while(rs1.next()){
    			ColumnInfo info = new ColumnInfo();
    			info.setColumnName(rs1.getString("column_name"));
    			info.setColumnType(rs1.getString("data_type"));
    			info.setColumnMemo(rs1.getString("comments"));
    			list.add(info);
    		}
    		
    		bw.write("\n\nORACLE库 - 表结构 - "+tablename+"\n\n");
    		bw.write(header);
    		for(ColumnInfo columninfo : list){
    			bw.write("|"+columninfo.columnName+"|"+columninfo.columnType+"|"+columninfo.columnMemo+" | | |\n");
    		}
    		bw.flush();
    		
    	}
    	 
    	bw.close();
    	
    }
    class ColumnInfo{
    	private String columnName;
    	private String columnType;
    	private String columnMemo;
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			if(null == columnName){
				columnName = "";
			}
			this.columnName = columnName.trim();
		}
		public String getColumnType() {
			return columnType;
		}
		public void setColumnType(String columnType) {
			if(null == columnType){
				columnType = "";
			}
			this.columnType = columnType.trim();
		}
		public String getColumnMemo() {
			return columnMemo;
		}
		public void setColumnMemo(String columnMemo) {
			if(null == columnMemo){
				columnMemo = "";
			}
			this.columnMemo = columnMemo.trim();
		}
    }
    
    public static void main(String []args){
    	ReadTableInfo readTbInfo = new ReadTableInfo();
    	readTbInfo.initDb();
    	try {
			readTbInfo.showTableInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
