<?php
require_once("./class.mydb.php");
require_once("./config.php");

$db = new MyDb($C_MYSQL["dbhost"],$C_MYSQL["dbuser"],$C_MYSQL["dbpsw"],$C_MYSQL["dbname"]);
 
$db->execute("set names GBK");
$gamename = "";
if (isset($_GET["name"]) && $_GET["name"] != ""){
    $gamename = trim(addslashes($_GET["name"]));
}
$sql = "select gameid, smallpic, gamename, pinyin from game where gamename like '%$gamename%' limit 84";
//echo $sql;
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
 <meta name="DESCRIPTION" content="去耍小游戏, 4399$小游戏,3366小游戏">
 <meta name="keywords" content="去耍小游戏, 4399$小游戏,3366小游戏,游戏排行,新游戏排行,游戏怎么玩">
 <meta http-equiv="x-ua-compatible" content="ie=7" /> 
 <title>搜索结果 - www.qushua.com 去耍小游戏 4399中文游戏精选小游戏 </title>
 <script src="http://res.qushua.com/js/allad.js" charset="utf-8"></script>
 <script src="http://res.qushua.com/js/base.js" charset="utf-8"></script>
 <link rel="shortcut icon" href="/favicon.ico" /> 
 <link href="http://res.qushua.com/css/style.css" rel="stylesheet" media="screen" type="text/css" />
</head>
<body>
	<div id="header"> 
    <div class="logo">
       <div class="logo_img"><a href="http://www.qushua.com" title="去耍小游戏，玩的岂是寂寞"><img src="http://res.qushua.com/images/logo.png" alt="去耍小游戏，玩的岂是寂寞" width="165" height="60" border="0"/></a></div>
       <div class="logo_right">
	     <div style="height:30px;">
		 <span style="color:red;float:left;margin-right:20px;">qushua.com是最大的在线休闲游戏中心，最好玩的Flash游戏社区</span> 		 
		 <span style="float:right;"><a href="javascript:;" onclick="setHome(this);return false;">设为首页</a> | <a href="javascript:;" onclick="addSiteToFav();return false;">收藏去耍</a> | <a href="http://res.qushua.com/小游戏中心.url">放到桌面</a>&nbsp;</span>

		 </div>
         <div class="search2">
         <form action="search.php" style="float:left;margin-right:10px;">
           <input type="text" class="btn2" id="name" name="name" maxlength="16" value="<?php echo $gamename;?>"> 
           <input type="submit" value="搜 索" class="btn" onclick="goSearch();">
         </form>  
         <a href="/" title="点击查看-最新小游戏">最新小游戏</a>&nbsp;&nbsp;
         <a href="/" title="点击查看-人气最旺游戏">人气最旺游戏</a> &nbsp;
		 </div>
	   </div>
    </div><!--End logo-->
    <div class="menu">

    <ul>
      <li><a href="/" title="首页">首页</a></li>
      <li><a href="/gaoxiao/" title="搞笑小游戏">搞笑</span></a></li>
      <li><a href="/dongzuo/" title="动作小游戏">动作</span></a></li>
      <li><a href="/tiyu/" title="体育小游戏">体育</span></a></li>
      <li><a href="/sheji/" title="射击小游戏">射击</span></a></li>
      <li><a href="/yizhi/" title="益智小游戏">益智</span></a></li>
      <li><a href="/maoxian/" title="冒险小游戏">冒险</span></a></li>
      <li><a href="/qipai/" title="棋牌小游戏">棋牌</span></a></li>
      <li><a href="/cheyue/" title="策略小游戏">策略</span></a></li>
      <li><a href="/zhonghe/" title="综合小游戏">综合</span></a></li>
      <li><a href="/minjie/" title="敏捷小游戏">敏捷</span></a></li>
      <li><a href="/xiuxian/" title="休闲小游戏">休闲</span></a></li>
      <li><a href="/ertong/" title="儿童小游戏">儿童</span></a></li>
      <li><a href="/zhuangban/" title="装扮小游戏">装扮</span></a></li>
      <li><a href="http://www.dyhk.net" target="_blank" title="九点半影院">影院</li>
      <li><a href="http://www.4462.com" target="_blank" title="4462网址导航">导航</a></li>
    </ul></div>    
    </div><!--End header-->    <div id="header_ad" style="height:90px;"><center><script>ad_tag_top();</script></center></div>

	<div id="main">
		<div id="left" style="height:1600px;">
		  <div class="title"><h1>搜索结果</h1></div>
		    <?php		    
		    $result = $db->queryObjectArray($sql);
		    if(!$result){
		    	$sql = "select gameid, smallpic, gamename, pinyin from game limit 84";		
		    	$result = $db->queryObjectArray($sql);    
		    }
			if($result){	
				foreach($result as $row){ 								
			?>
		    <div class="one_game">
              <a target="_blank" href="/<?php echo $row->pinyin?>/<?php echo $row->gameid?>.html" title="在线玩<?php echo $row->gamename;?>小游戏">
			  <img src="<?php echo $row->smallpic;?>" width="92" height="70" border="0" /></a>
              <a target="_blank" href="/<?php echo $row->pinyin?>/<?php echo $row->gameid?>.html"> <?php echo $row->gamename;?> </a>
            </div>
            <?php } }?>
            <div style="clear:both;"></div>            
		</div><!--end left-->
		<div id="right">
		  <div class="border4">
		    <center><script>ad_tag_right();</script></center>
		  </div> 
		  <div class="border3 mt_10">
            <div class="title"><h3>热门推荐</h3></div>
			<div class="clear"></div>
		    <ul>
		      		      <li><a href="/cheyue/16892.html" title="点击玩耍-小径塔防2">小径塔防2</a></li> 
		      		      <li><a href="/xiuxian/25661.html" title="点击玩耍-地下矿工">地下矿工</a></li> 
		      		      <li><a href="/minjie/18410.html" title="点击玩耍-怪物一百层">怪物一百层</a></li> 
		      		      <li><a href="/sheji/5146.html" title="点击玩耍-枪杀骷髅">枪杀骷髅</a></li> 
		      		      <li><a href="/zhuangban/28273.html" title="点击玩耍-女孩的骑马装">女孩的骑马装</a></li> 
		      		      <li><a href="/yizhi/10326.html" title="点击玩耍-生气脸变色">生气脸变色</a></li> 
		      		      <li><a href="/minjie/22691.html" title="点击玩耍-接元宵">接元宵</a></li> 
		      		      <li><a href="/maoxian/15679.html" title="点击玩耍-拍照尼斯湖水怪">拍照尼斯湖水怪</a></li> 
		      		      <li><a href="/sheji/4661.html" title="点击玩耍-弹跳射杀2">弹跳射杀2</a></li> 
		      		      <li><a href="/yizhi/11651.html" title="点击玩耍-粘粘世界-精灵垒塔">粘粘世界-精灵垒塔</a></li> 
		      		      <li><a href="/cheyue/16753.html" title="点击玩耍-剑客守城">剑客守城</a></li> 
		      		      <li><a href="/sheji/5399.html" title="点击玩耍-机器人猎人">机器人猎人</a></li> 
		      		      <li><a href="/zhuangban/30308.html" title="点击玩耍-海边女生化妆">海边女生化妆</a></li> 
		      		      <li><a href="/zhuangban/29561.html" title="点击玩耍-时尚姐妹花2">时尚姐妹花2</a></li> 
		      		      <li><a href="/sheji/4783.html" title="点击玩耍-反恐陆战队">反恐陆战队</a></li> 
		      		      <li><a href="/sheji/5632.html" title="点击玩耍-蓝猫保护饼干">蓝猫保护饼干</a></li> 
		      		      <li><a href="/minjie/22555.html" title="点击玩耍-许愿池">许愿池</a></li> 
		      		      <li><a href="/xiuxian/24153.html" title="点击玩耍-芭比美人鱼">芭比美人鱼</a></li> 
		      		      <li><a href="/sheji/7130.html" title="点击玩耍-兄弟太空连">兄弟太空连</a></li> 
		      		      <li><a href="/zhuangban/28511.html" title="点击玩耍-人马兽女生">人马兽女生</a></li> 
		      		      <li><a href="/zhuangban/31589.html" title="点击玩耍-劳动节的装扮秀">劳动节的装扮秀</a></li> 
		      		      <li><a href="/maoxian/15890.html" title="点击玩耍-加州淘金者">加州淘金者</a></li> 
		      		      <li><a href="/minjie/20756.html" title="点击玩耍-快餐店帮忙">快餐店帮忙</a></li> 
		      		      <li><a href="/dongzuo/528.html" title="点击玩耍-火影忍者之鸣人格斗">火影忍者之鸣人格斗</a></li> 
		      		      <li><a href="/tiyu/3140.html" title="点击玩耍-疯狂半兽人版越野车">疯狂半兽人版越野车</a></li> 
		      		      <li><a href="/minjie/20963.html" title="点击玩耍-萤火虫抓抓乐">萤火虫抓抓乐</a></li> 
		      		      <li><a href="/xiuxian/25363.html" title="点击玩耍-番茄比萨大厨">番茄比萨大厨</a></li> 
		      		      <li><a href="/zhuangban/28384.html" title="点击玩耍-七彩礼服">七彩礼服</a></li> 
		      		      <li><a href="/minjie/20084.html" title="点击玩耍-赛勒斯的病毒">赛勒斯的病毒</a></li> 
		      		      <li><a href="/yizhi/14619.html" title="点击玩耍-A3MM自白">A3MM自白</a></li> 
		      		      <li><a href="/dongzuo/298.html" title="点击玩耍-功夫周星驰">功夫周星驰</a></li> 
		      		      <li><a href="/yizhi/14136.html" title="点击玩耍-逃出黑色小卧室">逃出黑色小卧室</a></li> 
		      			</ul>		    
		  </div>
		  <div style="clear:both;"></div>
	    </div><!--end right-->
    </div><!--end main-->
	<div class="clear"></div>	
    <div id="bottom_ad"><center><script>ad_tag_bottom();</script></center></div>    
	<div id="footer">
	  <div align="left"><div style="width:100%;" align="center"><table width="558">
	    <tr><td colspan="2" align="left">作品版权归作者所有，如果无意之中侵犯了您的版权，请来信告知，本站将在3个工作日内删除</td></tr>
	    <tr ><td width="103" rowspan="2" ><img src="http://res.qushua.com/images/hel.gif" /></td><td width="443" align="left" style="color:#ff6600">抵制不良游戏，拒绝盗版游戏 注意自我保护，谨防受骗上当.....</td></tr>
        <tr><td align="left" style="color:#ff6600"> 适度游戏益脑，沉迷游戏伤身 合理安排时间，享受健康生活.....</td></tr>
	    <tr><td colspan="2" align="left"> 管理邮箱：1000dou#gmail.com(请将#替换成@) 站长QQ:277161889 渝ICP备06009696号</td></tr>
        </table></div>    
      </div>
      <div id="site_analysis" style="display:none;"><script>stat();</script></div>
   </body>
</html>
