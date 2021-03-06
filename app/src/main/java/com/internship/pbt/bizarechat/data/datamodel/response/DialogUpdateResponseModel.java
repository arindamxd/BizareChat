package com.internship.pbt.bizarechat.data.datamodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DialogUpdateResponseModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("last_message")
    @Expose
    private Object lastMessage;
    @SerializedName("last_message_date_sent")
    @Expose
    private Object lastMessageDateSent;
    @SerializedName("last_message_user_id")
    @Expose
    private Object lastMessageUserId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("occupants_ids")
    @Expose
    private List<Integer> occupantsIds = null;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("xmpp_room_jid")
    @Expose
    private String xmppRoomJid;
    @SerializedName("unread_messages_count")
    @Expose
    private Integer unreadMessagesCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Object lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Object getLastMessageDateSent() {
        return lastMessageDateSent;
    }

    public void setLastMessageDateSent(Object lastMessageDateSent) {
        this.lastMessageDateSent = lastMessageDateSent;
    }

    public Object getLastMessageUserId() {
        return lastMessageUserId;
    }

    public void setLastMessageUserId(Object lastMessageUserId) {
        this.lastMessageUserId = lastMessageUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public List<Integer> getOccupantsIds() {
        return occupantsIds;
    }

    public void setOccupantsIds(List<Integer> occupantsIds) {
        this.occupantsIds = occupantsIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getXmppRoomJid() {
        return xmppRoomJid;
    }

    public void setXmppRoomJid(String xmppRoomJid) {
        this.xmppRoomJid = xmppRoomJid;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }
}