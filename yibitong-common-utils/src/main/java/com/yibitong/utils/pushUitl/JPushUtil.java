package com.yibitong.utils.pushUitl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 包      名：  com.jqyong.modules.yyjq.utils.push
 * 创 建 人：   寻欢
 * 创建时间：  2016/11/11 16:41
 * 修 改 人：
 * 修改日期：
 *
 * 极光推送 - 推送工具类
 */
public class JPushUtil {
	protected static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);

	/**
	 * 配置appkey以及masterSecret
	 */
	private static final String appKey = "16afe6c412d22b860c184859";
	private static final String masterSecret = "60b99270bf19ac45f4f246fe";

	static JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);



	/**
	 * 用    于：	后端
	 * 方 法 名：	pushToOne
	 * 作    用：    给单个指定用户推送消息
	 * 创 建 人：	寻欢
	 * 创建时间：	2016/11/22 11:13
	 * 修 改 人：
	 * 修改日期：
	 */
	public static boolean pushToOne(String alias,String message){
		LOG.info("正在给"+alias+"推送信息。内容为："+message);
		boolean flag = false;
		try {
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.alert(message))
					.setNotification(Notification.newBuilder()
							.setAlert(message)
							.addPlatformNotification(AndroidNotification.newBuilder()
//									.addExtra("lockinfo", message)
									.build()
							)
							.addPlatformNotification(IosNotification.newBuilder()
//									.addExtra("lockinfo", message)
									.build()
							).build()
					).setOptions(Options.newBuilder()
						.setApnsProduction(false)
						.build()
					)
					.build();
			PushResult pushResult = jpushClient.sendPush(pushPayload);

			flag = pushResult.isResultOK();//推送是否成功 - 成功为True
		}catch (Exception e){
			LOG.error("推送给 "+alias+" 的推送出现错误。错误信息："+e.getMessage());
			flag = false;
		}
		LOG.info("推送给 "+alias+" 的信息已完成。状态："+flag);
		return flag;
	}





	/**
	 * 方 法 名：	pushLockInfoToOne
	 * 作    用：    专门给APP推开上锁信息
	 * 创 建 人：	寻欢 · 李
	 * 创建时间：	2017/2/21 0021 17:44
	 * 修 改 人：
	 * 修改日期：
	 */
	public static boolean pushLockInfoToOne(String alias,String imei, String status,String money){
		LOG.info("正在给"+alias+"推送信息。内容为：    锁ID："+imei + " ==== 锁状态："+(("0".equals(status))?"锁止":"开锁") + " ==== 金额："+ money);
		boolean flag = false;
		try {
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.android_ios())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.newBuilder()
							.setAlert("")
							.addPlatformNotification(AndroidNotification.newBuilder()
									.addExtra("imei", imei)
									.addExtra("status",status)
									.addExtra("money",money)
									.build()
							)
							.addPlatformNotification(IosNotification.newBuilder()
									.addExtra("imei", imei)
									.addExtra("status",status)
									.addExtra("money",money)
									.setContentAvailable(true)
									.build()
							).build()
					).setOptions(Options.newBuilder()
							.setApnsProduction(true)    // 生产环境
//							.setApnsProduction(false)    // 开发环境
							.build()
					)
					.build();
			PushResult pushResult = jpushClient.sendPush(pushPayload);

			flag = pushResult.isResultOK();//推送是否成功 - 成功为True
		}catch (Exception e){
			LOG.error("推送给 "+alias+" 的推送出现错误。错误信息："+e.getMessage());
			flag = false;
		}
		LOG.info("推送给 "+alias+" 的信息已完成。状态："+(flag?"成功":"失败"));
		return flag;
	}





	/**
	 * 用    于：	后端
	 * 方 法 名：	    pushToMore
	 * 作    用：    给指定用户推送消息
	 * 创 建 人：	    寻欢
	 * 创建时间：	    2016/11/15 下午5:35
	 * 修 改 人：
	 * 修改日期：
	 *
	 * @Pram alias:设备别名     message：推送内容
	 */

	public static boolean pushToMore(List<String> alias, String message){
		LOG.info("推送队列共有"+alias.size()+"个用户。推送内容为："+message);
		boolean flag = false;
		try {
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					.setNotification(Notification.alert(message))
					.setOptions(Options.newBuilder()
							.setApnsProduction(true)
							.build()
					)
					.build();

			PushResult pushResult = jpushClient.sendPush(pushPayload);

			flag = pushResult.isResultOK();//推送是否成功 - 成功为True
		}catch (Exception e){
			LOG.error("推送给 "+alias+" 的推送出现错误。错误信息："+e.getMessage());
			flag = false;
		}
		return flag;
	}




	/**
	 * 用    于：	后端
	 * 方 法 名：	    pushToAll
	 * 作    用：    给所有用户推送消息
	 * 创 建 人：	    寻欢
	 * 创建时间：	    2016/11/15 下午5:35
	 * 修 改 人：
	 * 修改日期：
	 *
	 * @Pram message：推送内容
	 */
	public static boolean pushToAll(String message){
		boolean flag = false;
		try {
			PushPayload pushPayload = PushPayload.alertAll(message);
			PushResult pushResult = jpushClient.sendPush(pushPayload);

			flag = pushResult.isResultOK();
		}catch (Exception e){
			flag = false;
		}
		return flag;
	}



	public static void main(String[] args) {
//		List<String> userList = new ArrayList<>();
//		userList.add("18223672897");
//		userList.add("18225001713");
//		LOG.info("多个别名推送测试："+pushToMore(userList,"告诉你们嗷。一元借钱最好用啦。n(*≧▽≦*)n"));
//		LOG.info("单个别名推送测试："+pushToOne("18523098660","{\"imei\":\"863725032613711\",\"status\":1}"));
		System.out.println("广播推送测试："+pushToAll("悟空单车感谢有你。。n(*≧▽≦*)n"));
	}

}
