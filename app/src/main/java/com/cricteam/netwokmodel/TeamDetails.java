package com.cricteam.netwokmodel;

import java.io.Serializable;






public class TeamDetails implements Serializable {

	/**
	 *  team details class for team information
	 */
	private static final long serialVersionUID = 1L;


	private int teamId;
	

	private UserDetails userDetails;
	
	private String teamName;
	
	private String teamDesc;
	
	private double teamLat;
	
	private double teamLong;

	private double distance;
	private String teamAddress;
	
	private String teamLogoUrl;
	
	
	
	public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

	public TeamDetails(int teamId, UserDetails userDetails, String teamName, String teamDesc, double teamLat, double teamLong, String teamAddress, String teamLogoUrl ) {
		super();
		this.teamId = teamId;
		this.userDetails = userDetails;
		this.teamName = teamName;
		this.teamDesc = teamDesc;
		this.teamLat = teamLat;
		this.teamLong = teamLong;
		this.teamAddress = teamAddress;
		this.teamLogoUrl = teamLogoUrl;
	}
public TeamDetails(){
	
}

	@Override
	public String toString(){
		 return "{teamId:" + teamId + ", userDetails:" + userDetails + ", teamName:"
				+ teamName + ", teamDesc:" + teamDesc + ", teamLat:" 
				+ teamLat + ", teamAddress:" + teamAddress + ", teamLong:"+ teamLong +", teamLogoUrl:" + teamLogoUrl +", distance: "+distance+"}";
	}


	/**
	 * @return the teamId
	 */
	public int getTeamId() {
		return teamId;
	}


	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}


	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}


	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}


	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}


	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	/**
	 * @return the teamDesc
	 */
	public String getTeamDesc() {
		return teamDesc;
	}


	/**
	 * @param teamDesc the teamDesc to set
	 */
	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}


	/**
	 * @return the teamLat
	 */
	public double getTeamLat() {
		return teamLat;
	}


	/**
	 * @param teamLat the teamLat to set
	 */
	public void setTeamLat(double teamLat) {
		this.teamLat = teamLat;
	}


	/**
	 * @return the teamLong
	 */
	public double getTeamLong() {
		return teamLong;
	}


	/**
	 * @param teamLong the teamLong to set
	 */
	public void setTeamLong(double teamLong) {
		this.teamLong = teamLong;
	}


	/**
	 * @return the teamAddress
	 */
	public String getTeamAddress() {
		return teamAddress;
	}


	/**
	 * @param teamAddress the teamAddress to set
	 */
	public void setTeamAddress(String teamAddress) {
		this.teamAddress = teamAddress;
	}


	/**
	 * @return the teamLogoUrl
	 */
	public String getTeamLogoUrl() {
		return teamLogoUrl;
	}


	/**
	 * @param teamLogoUrl the teamLogoUrl to set
	 */
	public void setTeamLogoUrl(String teamLogoUrl) {
		this.teamLogoUrl = teamLogoUrl;
	}
	

		  

	
}
