package com.cricteam.netwokmodel;


import android.os.Parcel;
import android.os.Parcelable;

public class PlayerDetails implements Parcelable {
	/**
	 *  player details class for add player to team
	 */

	private int playerId;
	
	public PlayerDetails() {
		
		// TODO Auto-generated constructor stub
	}



	private TeamDetails teamDetails;
	
	private String playerName;
	
	private String playerMobile;
	
	private String playerImageUrl;

	private PlayerTypes playerTypes;
	
	private String leaderShip;
	
	public PlayerDetails(int playerId, TeamDetails teamDetails, PlayerTypes playerTypes, String playerName, String playerMobile, String playerImageUrl,String leaderShip) {
		super();
		this.playerId = playerId;
		this.teamDetails = teamDetails;
		this.playerName = playerName;
		this.playerTypes = playerTypes;
		this.leaderShip = leaderShip;
		this.playerImageUrl=playerImageUrl;
	}


	protected PlayerDetails(Parcel in) {
		playerId = in.readInt();
		playerName = in.readString();
		playerMobile = in.readString();
		playerImageUrl = in.readString();
		leaderShip = in.readString();
	}

	public static final Creator<PlayerDetails> CREATOR = new Creator<PlayerDetails>() {
		@Override
		public PlayerDetails createFromParcel(Parcel in) {
			return new PlayerDetails(in);
		}

		@Override
		public PlayerDetails[] newArray(int size) {
			return new PlayerDetails[size];
		}
	};

	@Override
	public String toString(){
		 return "{playerId:" + playerId + ", teamDetails:"
				+ teamDetails + ", playerName:" + playerName + ", playerMobile:" 
				+ playerMobile + ", playerImageUrl:" + playerImageUrl + ", leaderShip:" + leaderShip +", playerTypes:" + playerTypes +"}";
	}


	public String getLeaderShip() {
		return leaderShip;
	}


	public void setLeaderShip(String leaderShip) {
		this.leaderShip = leaderShip;
	}


	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}


	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the teamDetails
	 */
	public TeamDetails getTeamDetails() {
		return teamDetails;
	}


	/**
	 * @param teamDetails the teamDetails to set
	 */
	public void setTeamDetails(TeamDetails teamDetails) {
		this.teamDetails = teamDetails;
	}


	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}


	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	/**
	 * @return the playerMobile
	 */
	public String getPlayerMobile() {
		return playerMobile;
	}


	/**
	 * @param playerMobile the playerMobile to set
	 */
	public void setPlayerMobile(String playerMobile) {
		this.playerMobile = playerMobile;
	}


	/**
	 * @return the playerImageUrl
	 */
	public String getPlayerImageUrl() {
		return playerImageUrl;
	}


	/**
	 * @param playerImageUrl the playerImageUrl to set
	 */
	public void setPlayerImageUrl(String playerImageUrl) {
		this.playerImageUrl = playerImageUrl;
	}


	/**
	 * @return the playerTypes
	 */
	public PlayerTypes getPlayerTypes() {
		return playerTypes;
	}


	/**
	 * @param playerTypes the playerTypes to set
	 */
	public void setPlayerTypes(PlayerTypes playerTypes) {
		this.playerTypes = playerTypes;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(playerId);
		parcel.writeString(playerName);
		parcel.writeString(playerMobile);
		parcel.writeString(playerImageUrl);
		parcel.writeString(leaderShip);
	}
}
