package com.cricteam.netwokmodel;

public class TeamCircleRequest {
public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public int getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(int senderUserId) {
		this.senderUserId = senderUserId;
	}
	public int getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(int receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getRequestAction() {
		return requestAction;
	}
	public void setRequestAction(String requestAction) {
		this.requestAction = requestAction;
	}
private int senderId;
private int receiverId;
private int senderUserId;
private int receiverUserId;
private int requestId;
private String requestAction;
}
