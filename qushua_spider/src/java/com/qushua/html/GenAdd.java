package com.qushua.html;

import java.sql.SQLException;

import com.qushua.spider.util.Constants;

public class GenAdd {
	public static void main(String[] args) {
		Constants.MAX_ID = 0;
		try {
			if (null != args && args.length > 0) {
				Constants.MAX_ID = Integer.parseInt(args[0]);
			}
			GenContentHtml.main(args);
			GenPlayHtml.main(args);
			GenListPage.main(args);
			GenTagPage.main(args);
			GenIndexPage.main(args);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
