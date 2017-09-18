package com.cricteam.netwokmodel;

public class TeamRequest {
	public TeamRequest(String teamName, String teamLat, String teamLong, String teamAddress, String teamLogoUrl,
			String teamDesc, int userId) {
		super();
		this.teamName = teamName;
		this.teamLat = teamLat;
		this.teamLong = teamLong;
		this.teamAddress = teamAddress;
		this.teamLogoUrl = teamLogoUrl;
		this.teamDesc = teamDesc;
		this.userId = userId;
	}
	public TeamRequest(){
		
	}
	 String teamName;
	 String teamLat;
	 String teamLong;
	 String teamAddress;
	 String teamLogoUrl;
	 String teamDesc;

	private int userId;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	public String getTeamAddress() {
		return teamAddress;
	}
	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}
	public String getTeamDesc() {
		return teamDesc;
	}
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}
	public String getTeamLogoUrl() {
		return teamLogoUrl;
	}
	public void setTeamLogoUrl(String teamLogoUrl) {
		this.teamLogoUrl = teamLogoUrl;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
}
