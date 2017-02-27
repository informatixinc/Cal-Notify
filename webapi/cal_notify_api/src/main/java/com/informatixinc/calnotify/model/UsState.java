package com.informatixinc.calnotify.model;

public enum UsState {
	
	Alabama(1,"Alabama","AL"),
	Alaska(2,"Alaska","AK"),
	Arizona(3,"Arizona","AZ"),
	Arkansas(4,"Arkansas","AR"),
	California(5,"California","CA"),
	Colorado(6,"Colorado","CO"),
	Connecticut(7,"Connecticut","CT"),
	Delaware(8,"Delaware","DE"),
	Florida(9,"Florida","FL"),
	Georgia(10,"Georgia","GA"),
	Hawaii(11,"Hawaii","HI"),
	Idaho(12,"Idaho","ID"),
	Illinois(13,"Illinois","IL"),
	Indiana(14,"Indiana","IN"),
	Iowa(15,"Iowa","IA"),
	Kansas(16,"Kansas","KS"),
	Kentucky(17,"Kentucky","KY"),
	Louisiana(18,"Louisiana","LA"),
	Maine(19,"Maine","MA"),
	Maryland(20,"Maryland","MD"),
	Massachusetts(21,"Massachusetts","MA"),
	Michigan(22,"Michigan","MI"),
	Minnesota(23,"Minnesota","MN"),
	Mississippi(24,"Mississippi","MS"),
	Missouri(25,"Missouri","MO"),
	Montana(26,"Montana","MT"),
	Nebraska(27,"Nebraska","NE"),
	Nevada(28,"Nevada","NV"),
	NewHampshire(29,"New Hampshire","NH"),
	NewJersey(30,"New Jersey","NJ"),
	NewMexico(31,"New Mexico","NM"),
	NewYork(32,"New York","NY"),
	NorthCarolina(33,"North Carolina","NC"),
	NorthDakota(34,"North Dakota","ND"),
	Ohio(35,"Ohio","OH"),
	Oklahoma(36,"Oklahoma","OK"),
	Oregon(37,"Oregon","OR"),
	Pennsylvania(38,"Pennsylvania","PA"),
	RhodeIsland(39,"Rhode Island","RI"),
	SouthCarolina(40,"South Carolina","SC"),
	SouthDakota(41,"South Dakota","SD"),
	Tennessee(42,"Tennessee","TN"),
	Texas(43,"Texas","TX"),
	Utah(44,"Utah","UT"),
	Vermont(45,"Vermont","VT"),
	Virginia(46,"Virginia","VA"),
	Washington(47,"Washington","WA"),
	WestVirginia(48,"West Virginia","WV"),
	Wisconsin(49,"Wisconsin","WI"),
	Wyoming(50,"Wyoming","WY");
	
	private int stateId;
	private String stateName;
	private String stateAbbreviation;
	
	UsState(int stateId, String stateName, String stateAbbreviation){
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateAbbreviation = stateAbbreviation;
	}
	
	public int getStateId() {
		return stateId;
	}

	public String getStateName() {
		return stateName;
	}
	
	public static String getStateAbbreviation(int id){
		for(UsState instance: values()){
			if(instance.getStateId() == id){
				return instance.getStateAbbreviation();
			}
		}
		return null;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	
	public static String getStateName(int id){
		for(UsState instance: values()){
			if(instance.getStateId() == id){
				return instance.getStateName();
			}
		}
		
		return null;
	}
	
	public static Integer getStateId(String state){
		for(UsState instance: values()){
			if(instance.getStateAbbreviation().toLowerCase().equals(state.toLowerCase())){
				return instance.getStateId();
			}
		}
		
		return null;
	}

}
