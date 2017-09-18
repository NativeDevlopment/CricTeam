package com.cricteam.netwokmodel;

import java.util.List;

public class AddPlayerRequest {

	private int teamId;
	private int userId;
	private List<PlayerDetails> playerList;
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<PlayerDetails> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<PlayerDetails> playerList) {
		this.playerList = playerList;
	}
}
