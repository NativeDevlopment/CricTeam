package com.cricteam.netwokmodel;

import java.io.Serializable;
import java.util.List;

import com.google.gson.JsonObject;

public class FullTeamDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeamDetails teamdetails;
	private List<PlayerDetails> teamPlayerList;
	private double teamRating;
	private TeamCircleStatus teamCircleStatus;
	public TeamDetails getTeamdetails() {
		return teamdetails;
	}
	public void setTeamdetails(TeamDetails teamdetails) {
		this.teamdetails = teamdetails;
	}
	public List<PlayerDetails> getTeamPlayerList() {
		return teamPlayerList;
	}
	public void setTeamPlayerList(List<PlayerDetails> teamPlayerList) {
		this.teamPlayerList = teamPlayerList;
	}
	public double getTeamRating() {
		return teamRating;
	}
	public void setTeamRating(double teamRating) {
		this.teamRating = teamRating;
	}
	public TeamCircleStatus getTeamCircleStatus() {
		return teamCircleStatus;
	}
	public void setTeamCircleStatus(TeamCircleStatus teamCircleStatus) {
		this.teamCircleStatus = teamCircleStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
