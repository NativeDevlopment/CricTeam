package com.cricteam.netwokmodel;


import java.io.Serializable;
import java.util.Date;


public class TeamCircle  implements Serializable{
	public int getTeamCircleId() {
		return teamCircleId;
	}

	public void setTeamCircleId(int teamCircleId) {
		this.teamCircleId = teamCircleId;
	}

	public TeamDetails getTeamSenderId() {
		return teamSenderId;
	}

	public void setTeamSenderId(TeamDetails teamSenderId) {
		this.teamSenderId = teamSenderId;
	}

	public TeamDetails getTeamReceiverId() {
		return teamReceiverId;
	}

	public void setTeamReceiverId(TeamDetails teamReceiverId) {
		this.teamReceiverId = teamReceiverId;
	}

	public TeamCircleStatusType getTeamCircleStatusId() {
		return teamCircleStatusId;
	}

	public void setTeamCircleStatusId(TeamCircleStatusType teamCircleStatusId) {
		this.teamCircleStatusId = teamCircleStatusId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int teamCircleId;

	    TeamDetails teamSenderId;
	   
	    TeamDetails teamReceiverId;
		private TeamCircleStatusType teamCircleStatusId;
	private String created_time;
	private String updated_time;
public TeamCircle(){
	
}
}
