/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhongchuang.canting.easeui.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.HanziToPinyin;
import com.hyphenate.util.HanziToPinyin.Token;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.easeui.EaseConstant;
import com.zhongchuang.canting.easeui.bean.RedPacketInfo;
import com.zhongchuang.canting.easeui.domain.EaseUser;
import com.zhongchuang.canting.utils.HxMessageUtils;
import com.zhongchuang.canting.utils.SpUtil;
import com.zhongchuang.canting.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class EaseCommonUtils {
	private static final String TAG = "CommonUtils";
	/**
	 * check if network avalable
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
			}
		}

		return false;
	}

	/**
	 * check if sdcard exist
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public static EMMessage createExpressionMessage(String toChatUsername, String expressioName, String identityCode){
	    EMMessage message = EMMessage.createTxtSendMessage("["+expressioName+"]", toChatUsername);
        if(identityCode != null){
            message.setAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, identityCode);
        }
        message.setAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, true);
        return message;
	}


    public static EMMessage createExpressionRedMessage(String toChatUsername, RedPacketInfo info){
	    String content="";
	    if(TextUtil.isNotEmpty(info.grap_id)){
	        content="红包被领取";
        }else {
            content= SpUtil.getName(CanTingAppLication.getInstance())+"发了一个红包";
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        if(message==null){
            return null ;
        }
        message.setAttribute(EaseConstant.EXTRA_RED, true);
        if(TextUtil.isNotEmpty(info.type)){
            message.setAttribute(EaseConstant.EXTRA_RED_TYPE, info.type);
        }  if(TextUtil.isNotEmpty(info.userInfoId)){
            message.setAttribute(EaseConstant.EXTRA_RED_USERID, info.userInfoId);
        }  if(TextUtil.isNotEmpty(info.sendType)){
            message.setAttribute(EaseConstant.EXTRA_RED_SEND_TYPE, info.sendType);
        }  if(TextUtil.isNotEmpty(info.send_jf)){
            message.setAttribute(EaseConstant.EXTRA_RED_COUNT, info.send_jf);
        }  if(TextUtil.isNotEmpty(info.leavMessage)){
            message.setAttribute(EaseConstant.EXTRA_RED_NAME, info.leavMessage);
        }  if(TextUtil.isNotEmpty(info.send_red_name)){
            message.setAttribute(EaseConstant.EXTRA_SEND_RED_NAME, info.send_red_name);
        }  if(TextUtil.isNotEmpty(info.redEnvelopeId)){
            message.setAttribute(EaseConstant.EXTRA_REDENVELID, info.redEnvelopeId);
        }  if(TextUtil.isNotEmpty(info.send_red_name)){
            message.setAttribute(EaseConstant.EXTRA_RED_COUT, info.send_cout);
        } if(TextUtil.isNotEmpty(info.red_name)){
            message.setAttribute(EaseConstant.EXTRA_NAME, info.red_name);
        }if(TextUtil.isNotEmpty(info.send_name)){
            message.setAttribute(EaseConstant.EXTRA_SEND, info.send_name);
        }if(TextUtil.isNotEmpty(info.img_url)){
            message.setAttribute(EaseConstant.EXTRA_NAME, info.img_url);
        }if(TextUtil.isNotEmpty(info.video_url)){
            message.setAttribute(EaseConstant.EXTRA_SEND, info.video_url);
        }if(TextUtil.isNotEmpty(info.grap_id)){
            message.setAttribute(EaseConstant.EXTRA_GRAPID, info.grap_id);
        }

        message.setAttribute(EaseConstant.EXTRA_RED_TYPES, info.isSend);
        message.setAttribute(EaseConstant.EXTRA_RED_IS_ALL, info.isAll);
        message.setAttribute(EaseConstant.EXTRA_RED_IS_GRAP, info.isgrab);







        return message;
    }
	/**
     * Get digest according message type and content
     * 
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
        case LOCATION:
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                digest = getString(context, R.string.location_recv);
                digest = String.format(digest, HxMessageUtils.getFName(message)==null?message.getFrom():HxMessageUtils.getFName(message));
                return digest;
            } else {
                digest = getString(context, R.string.location_prefix);
            }
            break;
        case IMAGE:
            digest = getString(context, R.string.picture);
            break;
        case VOICE:
            digest = getString(context, R.string.voice_prefix);
            break;
        case VIDEO:
            digest = getString(context, R.string.video);
            break;
        case TXT:
            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
            if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                digest = getString(context, R.string.voice_call) + txtBody.getMessage();
            }else if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                digest = getString(context, R.string.video_call) + txtBody.getMessage();
            }else if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                if(!TextUtils.isEmpty(txtBody.getMessage())){
                    digest = txtBody.getMessage();
                }else{
                    digest = getString(context, R.string.dynamic_expression);
                }
            }else{
                digest = txtBody.getMessage();
            }
            break;
        case FILE:
            digest = getString(context, R.string.file);
            break;
        default:
            EMLog.e(TAG, "error, unknow type");
            return "";
        }

        return digest;
    }
    
    static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }
	
	/**
	 * get top activity
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}
	
	/**
     * set initial letter of according user's nickname( username if no nickname)
     * 
     * @param user
     */
    public static void setUserInitialLetter(EaseUser user) {
        final String DefaultLetter = "#";
        String letter = DefaultLetter;
        
        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return DefaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return DefaultLetter;
                }
                ArrayList<Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0)
                {
                    Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return DefaultLetter;
                    }
                    return letter;
                }
                return DefaultLetter;
            }
        }
        
        if ( !TextUtils.isEmpty(user.getNickname()) ) {
            letter = new GetInitialLetter().getLetter(user.getNickname());
            user.setInitialLetter(letter);
            return;
        } 
        if (letter.equals(DefaultLetter) && !TextUtils.isEmpty(user.getUsername())) {
            letter = new GetInitialLetter().getLetter(user.getUsername());
        }
        user.setInitialLetter(letter);
    }
    
    /**
     * change the chat type to EMConversationType
     * @param chatType
     * @return
     */
    public static EMConversationType getConversationType(int chatType) {
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            return EMConversationType.Chat;
        } else if (chatType == EaseConstant.CHATTYPE_GROUP) {
            return EMConversationType.GroupChat;
        } else {
            return EMConversationType.ChatRoom;
        }
    }

    /**
     * \~chinese
     * 判断是否是免打扰的消息,如果是app中应该不要给用户提示新消息
     * @param message
     * return
     *
     * \~english
     * check if the message is kind of slient message, if that's it, app should not play tone or vibrate
     *
     * @param message
     * @return
     */
    public static boolean isSilentMessage(EMMessage message){
        return message.getBooleanAttribute("em_ignore_notification", false);
    }

}
