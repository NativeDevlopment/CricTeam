package com.cricteam.netwokmodel;

public class SendTeamRequest {
private int teamSenderId;
private int teamSenderUserId;
private int teamReceiverId;
public SendTeamRequest(int teamSenderId, int teamSenderUserId, int teamReceiverId, int teamReceiverUserId,
		String action) {
	super();
	this.teamSenderId = teamSenderId;
	this.teamSenderUserId = teamSenderUserId;
	this.teamReceiverId = teamReceiverId;
	this.teamReceiverUserId = teamReceiverUserId;
	this.action = action;
}
public SendTeamRequest(){
	
}
private int teamReceiverUserId;
private String action;
public int getTeamSenderId() {
	return teamSenderId;
}
public void setTeamSenderId(int teamSenderId) {
	this.teamSenderId = teamSenderId;
}
public int getTeamSenderUserId() {
	return teamSenderUserId;
}
public void setTeamSenderUserId(int teamSenderUserId) {
	this.teamSenderUserId = teamSenderUserId;
}
public int getTeamReceiverId() {
	return teamReceiverId;
}
public void setTeamReceiverId(int teamReceiverId) {
	this.teamReceiverId = teamReceiverId;
}
public int getTeamReceiverUserId() {
	return teamReceiverUserId;
}
public void setTeamReceiverUserId(int teamReceiverUserId) {
	this.teamReceiverUserId = teamReceiverUserId;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
}
