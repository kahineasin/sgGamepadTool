package com.sellgirl.gamepadtool.language;

import java.util.HashMap;
import java.util.Map;

/**
 * 英文大小写规范：
 * 按钮上的字， 全小写
 */
public class CN {
	public static Map<String,String> get(){
		Map<String,String> r=new HashMap<String,String>();
		//设置映射的页面
		r.put("add combin key","添加组合键");
		//模拟页面
		r.put("stop simulate (simulating...)","停止模拟(模拟中...)");
		r.put("start simulate","开始模拟");

		r.put("edit userInfo, or press TRIANGLE", "编辑用户信息,或者点 △");
		r.put("enter the game, or press X", "进入游戏,或者点 X");
		r.put("enter the game", "进入游戏");
		r.put("enter the 3d game, or press X", "进入3d游戏,或者点 X");
		r.put("enter the 3d game, or press", "进入3d游戏,或者点");
		r.put("enter the 2d game, or press □", "进入2d游戏,或者点 □");
		r.put("enter the KOF game, or press □", "进入格斗游戏模式,或者点 □");
		r.put("enter the 3d game, or press L1", "进入3d游戏模式,或者点 L1");
		r.put("enter the 3d cooperative game, or press L1", "进入3d多人合作模式,或者点 L1");
		r.put("enter the rhythm game, or press R1","进入音乐游戏,或者点 R1");
		r.put("game setting","游戏设置");
		r.put("key setting","按键设置");
		r.put("gamepad test","手柄测试");
		r.put("gamepad to keyboard input","手柄模拟键盘");
		r.put("setting","设置");
        r.put("save setting, press □","保存设置,按 □");
		r.put("save success","保存成功");
		r.put("restore default settings","恢复默认配置");


		
		r.put("edit email:", "编辑email:");
		r.put("send code", "发送验证码");
		r.put("valid code:","输入验证码:");
		r.put("confirm", "确定");
		r.put("cancel", "取消");
		r.put("return, press ○", "返回, 或者点 ○");
		r.put("join vip, or press □", "加入vip, 或者点 □");
//		r.put("This is a free game developed by BENJAMIN, and VIP will have a better experience in the game. You may pay 1 yuan or more to permanently activate VIP. The developer promises to permanently update and maintain this game. You can scan the QR code above to make payment, I will active your VIP in one day, thanks.",
//				"这是一款由BENJAMIN开发的免费游戏，VIP将在游戏中获得更好的体验。您可以支付1元或更多的费用永久激活VIP。开发商承诺将永久更新和维护这款游戏。你可以扫描上面的二维码付款，我会在一天内激活你的VIP，谢谢。"
//				);
		r.put("This is a free game developed by BENJAMIN and SASHA. VIP will have a better experience in the game. You may pay 1 yuan or more to permanently activate VIP.",
				"这是一款由 BENJAMIN 和 SASHA 开发的免费游戏，VIP将在游戏中获得更好的体验。您可以支付1元或更多的费用永久激活VIP。"
				);
		r.put("The developer promises to permanently update and maintain this game. I will active your VIP atfer you paid in one day, thanks.",
				"开发者承诺将永久更新和维护这款游戏。我会在你支付后的一天内激活你的VIP，谢谢。"
				);
		r.put("Welcome to contact us, email is sasha@sellgirl.com.",
				"欢迎联系我们，电子邮件是 sasha@sellgirl.com"
				);
		r.put("Purchase Way: ", "支付方式: ");
		r.put("Wechat Pay, press □", "微信支付, 或者点 □");
		r.put("Cancel Pay", "取消支付");
		r.put("Paid", "已支付");
		r.put("wrong valid code", "验证码错误");
		r.put("game continue □", "继续游戏 □");
		r.put("back to main menu ○", "回到主菜单 ○");
		r.put("exit game △", "退出游戏 △");
		r.put("exit game", "退出游戏");
		r.put("please input email first", "请先填写邮箱");
		r.put("backup or upload character data", "备份或上传角色数据");
		r.put("need become vip first", "需要先加入vip");
		r.put("confirm", "确定");
		r.put("close", "关闭");
		r.put("game guide X", "操作说明 X");

		r.put("auto setting","自动设置");
		r.put("caculating","计算中");

//		r.put("pay description",
//				"这是一款由BENJAMIN开发的免费游戏，VIP将在游戏中获得更好的体验。您可以支付1元或更多的费用永久激活VIP。开发商承诺将永久更新和维护这款游戏。你可以扫描上面的二维码付款，我会在一天内激活你的VIP，谢谢。"
//				);
		return r;
	}
}
