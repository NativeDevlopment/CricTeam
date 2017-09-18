package com.cricteam.netwokmodel;

public class PlayerTypes {

	public PlayerTypes() {
		
		// TODO Auto-generated constructor stub
	}


	private int playerTypeId;
	
	private String playerTypeNm;
	
	
	
	public PlayerTypes(int playerTypeId, String playerTypeNm ) {
		super();
		this.playerTypeId = playerTypeId;
		this.playerTypeNm = playerTypeNm;
	}

	@Override
	public String toString(){
		 return "{playerTypeId:" + playerTypeId + ", playerTypeNm:" + playerTypeNm +  "}";
	}

	/**
	 * @return the playerTypeId
	 */
	public int getPlayerTypeId() {
		return playerTypeId;
	}

	/**
	 * @param playerTypeId the playerTypeId to set
	 */
	public void setPlayerTypeId(int playerTypeId) {
		this.playerTypeId = playerTypeId;
	}

	/**
	 * @return the playerTypeNm
	 */
	public String getPlayerTypeNm() {
		return playerTypeNm;
	}

	/**
	 * @param playerTypeNm the playerTypeNm to set
	 */
	public void setPlayerTypeNm(String playerTypeNm) {
		this.playerTypeNm = playerTypeNm;
	}

}
