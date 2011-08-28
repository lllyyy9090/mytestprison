package com.qushua.spider.util;

import java.util.HashMap;
import java.util.Map;

public class GameType {

	public static Map<Integer, String> ALL_TYPE = new HashMap<Integer, String>();
	public static Map<Integer, String> ALL_TYPE_LINK = new HashMap<Integer, String>();
	static {
		ALL_TYPE.put(1, "搞笑");
		ALL_TYPE.put(2, "动作");
		ALL_TYPE.put(3, "体育");
		ALL_TYPE.put(4, "射击");
		ALL_TYPE.put(5, "益智");
		ALL_TYPE.put(6, "冒险");
		ALL_TYPE.put(7, "棋牌");
		ALL_TYPE.put(8, "策略");
		ALL_TYPE.put(9, "综合");
		//ALL_TYPE.put(10, "过关");
		ALL_TYPE.put(11, "敏捷");
		ALL_TYPE.put(12, "休闲");
		ALL_TYPE.put(13, "儿童");
		ALL_TYPE.put(14, "装扮");
		//ALL_TYPE.put(15, "经营");
	
		ALL_TYPE_LINK.put(1, "/gaoxiao/");
		ALL_TYPE_LINK.put(2, "/dongzuo/");
		ALL_TYPE_LINK.put(3, "/tiyu/");
		ALL_TYPE_LINK.put(4, "/sheji/");
		ALL_TYPE_LINK.put(5, "/yizhi/");
		ALL_TYPE_LINK.put(6, "/maoxian/");
		ALL_TYPE_LINK.put(7, "/qipai/");
		ALL_TYPE_LINK.put(8, "/cheyue/");
		ALL_TYPE_LINK.put(9, "/zhonghe/");
		//ALL_TYPE_LINK.put(10, "/guoguang/");
		ALL_TYPE_LINK.put(11, "/minjie/");
		ALL_TYPE_LINK.put(12, "/xiuxian/");
		ALL_TYPE_LINK.put(13, "/ertong/");
		ALL_TYPE_LINK.put(14, "/zhuangban/");
		//ALL_TYPE_LINK.put(15, "/jingying/");
	}
}
