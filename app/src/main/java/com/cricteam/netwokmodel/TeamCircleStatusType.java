package com.cricteam.netwokmodel;




public class TeamCircleStatusType {


	private int teamCircleStatusId;
	
	private String teamCircleStatusName;
	
	public TeamCircleStatusType(){
		
	}
	
	public TeamCircleStatusType(int teamCircleStatusId, String teamCircleStatusName ) {
		super();
		this.teamCircleStatusId = teamCircleStatusId;
		this.teamCircleStatusName = teamCircleStatusName;
	}

	@Override
	public String toString(){
		 return "{teamCircleStatusId:" + teamCircleStatusId + ", teamCircleStatusName:" + teamCircleStatusName +  "}";
	}

	public int getTeamCircleStatusId() {
		return teamCircleStatusId;
	}

	public void setTeamCircleStatusId(int teamCircleStatusId) {
		this.teamCircleStatusId = teamCircleStatusId;
	}

	public String getTeamCircleStatusName() {
		return teamCircleStatusName;
	}

	public void setTeamCircleStatusName(String teamCircleStatusName) {
		this.teamCircleStatusName = teamCircleStatusName;
	}

	

}
