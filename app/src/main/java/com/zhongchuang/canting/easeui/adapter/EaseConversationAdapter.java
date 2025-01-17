package com.zhongchuang.canting.easeui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;
import com.zhongchuang.canting.R;
import com.zhongchuang.canting.app.CanTingAppLication;
import com.zhongchuang.canting.easeui.EaseConstant;
import com.zhongchuang.canting.easeui.EaseUI;
import com.zhongchuang.canting.easeui.domain.EaseAvatarOptions;
import com.zhongchuang.canting.easeui.domain.EaseUser;
import com.zhongchuang.canting.easeui.model.EaseAtMessageHelper;
import com.zhongchuang.canting.easeui.utils.EaseCommonUtils;
import com.zhongchuang.canting.easeui.utils.EaseSmileUtils;
import com.zhongchuang.canting.easeui.utils.EaseUserUtils;
import com.zhongchuang.canting.easeui.widget.EaseConversationList.EaseConversationListHelper;
import com.zhongchuang.canting.easeui.widget.EaseImageView;
import com.zhongchuang.canting.easeui.widget.NineGridImageView;
import com.zhongchuang.canting.utils.HxMessageUtils;
import com.zhongchuang.canting.utils.StringUtil;
import com.zhongchuang.canting.utils.TextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * conversation list adapter
 *
 */
public class EaseConversationAdapter extends ArrayAdapter<EMConversation> {
    private static final String TAG = "ChatAllHistoryAdapter";
    private List<EMConversation> conversationList;
    private List<EMConversation> copyConversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;
    
    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;
    private List<EMConversation> datas=new ArrayList();
    public EaseConversationAdapter(Context context, int resource, List<EMConversation> objects) {
        super(context, resource, objects);
        datas.clear();
        for(EMConversation data:objects){
            String types = CanTingAppLication.list.get(data.conversationId());

                    datas.add(data);


        }
        conversationList = datas;
//        conversationList = objects;
        this.type=type;
        copyConversationList = new ArrayList<EMConversation>();
        copyConversationList.addAll(objects);
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public EMConversation getItem(int arg0) {
        if (arg0 < conversationList.size()) {
            return conversationList.get(arg0);
        }
        return null;
    }
    private String type;

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static final String EXETEND = "rb_extend";
    public static final String CONTENT = "re_content";
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_chat_history, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.unreadLabel = convertView.findViewById(R.id.unread_msg_number);
            holder.message = convertView.findViewById(R.id.message);
            holder.time = convertView.findViewById(R.id.time);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.group_avatar = convertView.findViewById(R.id.group_avatar);
            holder.list_itease_layout = convertView.findViewById(R.id.list_itease_layout);
            holder.motioned = convertView.findViewById(R.id.mentioned);
            convertView.setTag(holder);
        }
        holder.list_itease_layout.setBackgroundResource(R.drawable.ease_mm_listitem);


        // get conversation
        EMConversation conversation = getItem(position);


        // get username or group id
        String username = conversation.conversationId();
        EMMessage lastMessage = conversation.getLastMessage();
        if(lastMessage!=null){
            if (conversation.getType() == EMConversationType.GroupChat) {
                String groupId = conversation.conversationId();

                if (EaseAtMessageHelper.get().isAtMeMsg(lastMessage)) {
                    holder.motioned.setVisibility(View.VISIBLE);
//                    lastMessage.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG,"");
                } else {
                    holder.motioned.setVisibility(View.GONE);
                }
                // group message, show group avatar
                String groupAvatar = HxMessageUtils.getGroupAvater(lastMessage);
                holder.group_avatar.setImageStr(groupAvatar);
                holder.avatar.setVisibility(View.GONE);
                holder.group_avatar.setVisibility(View.VISIBLE);
                EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                String name = HxMessageUtils.getNick(lastMessage);
                if(TextUtil.isNotEmpty(name)){
                    holder.name.setText(name);
                }else {
                    holder.name.setText(group != null ? group.getGroupName() : username);
                }

            } else if(conversation.getType() == EMConversationType.ChatRoom){
                holder.avatar.setImageResource(R.drawable.ease_group_icon);
                EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
                holder.name.setText(room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : username);
                holder.motioned.setVisibility(View.GONE);
            }else {
                holder.avatar.setVisibility(View.VISIBLE);
                holder.group_avatar.setVisibility(View.GONE);
                EaseUserUtils.setUserAvatar(getContext(), username, holder.avatar);
//            EMMessage lastMessage = conversation.getLastMessage();
                EaseUserUtils.setUserNick(username, holder.name);
                String avatar = HxMessageUtils.getFAvater(lastMessage);
                String name = HxMessageUtils.getFName(lastMessage);
                holder.name.setText(name == null ? "" : name);
                Glide.with(getContext()).load(StringUtil.changeUrl(avatar)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(holder.avatar);


                holder.motioned.setVisibility(View.GONE);
            }

            EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
            if(avatarOptions != null && holder.avatar instanceof EaseImageView) {
                EaseImageView avatarView = ((EaseImageView) holder.avatar);
                if (avatarOptions.getAvatarShape() != 0)
                    avatarView.setShapeType(avatarOptions.getAvatarShape());
                if (avatarOptions.getAvatarBorderWidth() != 0)
                    avatarView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
                if (avatarOptions.getAvatarBorderColor() != 0)
                    avatarView.setBorderColor(avatarOptions.getAvatarBorderColor());
                if (avatarOptions.getAvatarRadius() != 0)
                    avatarView.setRadius(avatarOptions.getAvatarRadius());
            }
            if (conversation.getUnreadMsgCount() > 0) {
                // show unread message count
                holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
                holder.unreadLabel.setVisibility(View.VISIBLE);
            } else {
                holder.unreadLabel.setVisibility(View.INVISIBLE);
            }

            if (conversation.getAllMsgCount() != 0) {
                // show the content of latest message
//            EMMessage lastMessage = conversation.getLastMessage();
                String content = null;
                if(cvsListHelper != null){
                    content = cvsListHelper.onSetItemSecondaryText(lastMessage);
                }
                String type=lastMessage.getStringAttribute(EaseConstant.EXTRA_RED_TYPE,null);
                String contents=lastMessage.getStringAttribute(EXETEND,null);
                String cont=lastMessage.getStringAttribute(CONTENT,null);
                if(TextUtil.isNotEmpty(type)&&type.equals("3")){
                    content="[视频]";
                }else if(TextUtil.isNotEmpty(type)&&type.equals("4")){
                    if(TextUtil.isNotEmpty(contents)){
                        content=contents;
                    }else {
                        content=cont;
                    }

                }
                holder.message.setText(EaseSmileUtils.getSmiledText(getContext(), EaseCommonUtils.getMessageDigest(lastMessage, (this.getContext()))),
                        BufferType.SPANNABLE);
                if(content != null){
                    holder.message.setText(content);
                }

                holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
                if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                    holder.msgState.setVisibility(View.VISIBLE);
                } else {
                    holder.msgState.setVisibility(View.GONE);
                }
            }

            //set property
            holder.name.setTextColor(primaryColor);
            holder.message.setTextColor(secondaryColor);
            holder.time.setTextColor(timeColor);
            if(primarySize != 0)
                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
            if(secondarySize != 0)
                holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
            if(timeSize != 0)
                holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);
            if(username.equals("ifun")){
                holder.name.setText("IFun客服");
                Glide.with(getContext()).load("").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.kefuimg).into(holder.avatar);


            }

        }

        return convertView;
    }
    
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }
    
    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }
    

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }

    public void setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
    }

    public void setSecondarySize(int secondarySize) {
        this.secondarySize = secondarySize;
    }

    public void setTimeSize(float timeSize) {
        this.timeSize = timeSize;
    }


    private class ConversationFilter extends Filter {
        List<EMConversation> mOriginalValues = null;

        public ConversationFilter(List<EMConversation> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<EMConversation>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyConversationList;
                results.count = copyConversationList.size();
            } else {
                if (copyConversationList.size() > mOriginalValues.size()) {
                    mOriginalValues = copyConversationList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

                for (int i = 0; i < count; i++) {
                    final EMConversation value = mOriginalValues.get(i);
                    String username = value.conversationId();
                    
                    EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                    if(group != null){
                        username = group.getGroupName();
                    }else{
                        EaseUser user = EaseUserUtils.getUserInfo(username);
                        // TODO: not support Nick anymore
//                        if(user != null && user.getNick() != null)
//                            username = user.getNick();
                    }

                    // First match against the whole ,non-splitted value
                    if (username.startsWith(prefixString)) {
                        newValues.add(value);
                    } else{
                          final String[] words = username.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            conversationList.clear();
            if (results.values != null) {
                conversationList.addAll((List<EMConversation>) results.values);
            }
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private EaseConversationListHelper cvsListHelper;

    public void setCvsListHelper(EaseConversationListHelper cvsListHelper){
        this.cvsListHelper = cvsListHelper;
    }
    
    private static class ViewHolder {
        /** who you chat with */
        TextView name;
        /** unread message count */
        TextView unreadLabel;
        /** content of last message */
        TextView message;
        /** time of last message */
        TextView time;
        /** avatar */
        ImageView avatar;
        /** status of last message */
        View msgState;
        NineGridImageView group_avatar;
        /** layout */
        RelativeLayout list_itease_layout;
        TextView motioned;
    }
}

