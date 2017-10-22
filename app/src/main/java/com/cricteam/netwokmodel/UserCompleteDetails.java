package com.cricteam.netwokmodel;

import java.util.List;

public class UserCompleteDetails {
private UserDetails userDetails;
private List<TeamDetails> userTeamList;
public UserDetails getUserDetails() {
	return userDetails;
}
public void setUserDetails(UserDetails userDetails) {
	this.userDetails = userDetails;
}
public List<TeamDetails> getUserTeamList() {
	return userTeamList;
}
public void setUserTeamList(List<TeamDetails> userTeamList) {
	this.userTeamList = userTeamList;
}

}
