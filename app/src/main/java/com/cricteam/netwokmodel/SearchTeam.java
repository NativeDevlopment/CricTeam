package com.cricteam.netwokmodel;

public class SearchTeam {
	
	public String getTeamId() {
		return teamId;
	}




	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}




	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getTeamName() {
		return teamName;
	}




	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}




	public String getTeamDesc() {
		return teamDesc;
	}




	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}




	public String getTeamLat() {
		return teamLat;
	}




	public void setTeamLat(String teamLat) {
		this.teamLat = teamLat;
	}




	public String getTeamLong() {
		return teamLong;
	}




	public void setTeamLong(String teamLong) {
		this.teamLong = teamLong;
	}




	public String getDistance() {
		return distance;
	}




	public void setDistance(String distance) {
		this.distance = distance;
	}




	public String getTeamAddress() {
		return teamAddress;
	}




	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}




	public String getTeamLogoUrl() {
		return teamLogoUrl;
	}




	public void setTeamLogoUrl(String teamLogoUrl) {
		this.teamLogoUrl = teamLogoUrl;
	}




	private String teamId;
	
	private String teamSenderId;
	private String teamReceiverId;
	private String teamRequestId;


	public String getTeamSenderId() {
		return teamSenderId;
	}




	public void setTeamSenderId(String teamSenderId) {
		this.teamSenderId = teamSenderId;
	}




	public String getTeamReceiverId() {
		return teamReceiverId;
	}




	public void setTeamReceiverId(String teamReceiverId) {
		this.teamReceiverId = teamReceiverId;
	}




	public String getTeamRequestId() {
		return teamRequestId;
	}




	public void setTeamRequestId(String teamRequestId) {
		this.teamRequestId = teamRequestId;
	}




	private String userId;
	
	
	private String teamName;
	
	
	private String teamDesc;
	
	
	public String getTeamCircleStatus() {
		return teamCircleStatus;
	}




	public void setTeamCircleStatus(String teamCircleStatus) {
		this.teamCircleStatus = teamCircleStatus;
	}




	private String teamLat;
	
	
	private String teamLong;

	
	private String distance;
	
	private String teamAddress;
	
	
	private String teamLogoUrl;
	private String teamCircleStatus;
	private boolean isAddView;

	public boolean isAddView() {
		return isAddView;
	}

	public void setAddView(boolean addView) {
		isAddView = addView;
	}

	@Override
	public String toString(){
		 return "{teamId:" + teamId + ", userId:" + userId + ", teamName:"
				+ teamName + ", teamDesc:" + teamDesc + ", teamLat:" 
				+ teamLat + ", teamAddress:" + teamAddress+", isAddView:" + isAddView + ", teamLong:"+ teamLong +", teamLogoUrl:" + teamLogoUrl+", teamCircleStatus:" + teamCircleStatus +", distance: "+distance+"}";
	}
}
