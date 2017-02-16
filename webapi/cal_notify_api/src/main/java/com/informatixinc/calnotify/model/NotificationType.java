package com.informatixinc.calnotify.model;

public enum NotificationType {
	
	AshfallWarning("Ashfall Warning",1),
	AvalancheWarning("Avalanche Warning",2),
	BlizzardWarning("Blizzard Warning",3),
	CivilDangerWarning("Civil Danger Warning",4),
	CoastalFloodWarning("Coastal Flood Warning",5),
	DustStormWarning("Dust Storm Warning",6),
	EarthquakeWarning("Earthquake Warning",7),
	ExcessiveHeatWarning("Excessive Heat Warning",8),
	ExtremeColdWarning("Extreme Cold Warning",9),
	ExtremeWindWarning("Extreme Wind Warning",10),
	FireWarning("Fire Warning",11),
	FlashFloodWarning("Flash Flood Warning",12),
	FloodWarning("Flood Warning",13),
	FreezeWarning("Freeze Warning",14),
	GaleWarning("Gale Warning",15),
	HardFreezeWarning("Hard Freeze Warning",16),
	HazardousMaterialsWarning("Hazardous Materials Warning",17),
	HazardousSeasWarning("Hazardous Seas Warning",18),
	HeavyFreezingSprayWarning("Heavy Freezing Spray Warning",19),
	HighSurfWarning("High Surf Warning",20),
	HighWindWarning("High Wind Warning",21),
	HurricaneForceWindWarning("Hurricane Force Wind Warning",22),
	HurricaneWarning("Hurricane Warning",23),
	IceStormWarning("Ice Storm Warning",24),
	LakeEffectSnowWarning("Lake Effect Snow Warning",25),
	LakeshoreFloodWarning("Lakeshore Flood Warning",26),
	LawEnforcementWarning("Law Enforcement Warning",27),
	NuclearPowerPlantWarning("Nuclear Power Plant Warning",28),
	RadiologicalHazardWarning("Radiological Hazard Warning",29),
	RedFlagWarning("Red Flag Warning",30),
	SevereThunderstormWarning("Severe Thunderstorm Warning",31),
	ShelterInPlaceWarning("Shelter In Place Warning",32),
	SpecialMarineWarning("Special Marine Warning",33),
	StormWarning("Storm Warning",34),
	TornadoWarning("Tornado Warning",35),
	TropicalStormWarning("Tropical Storm Warning",36),
	TsunamiWarning("Tsunami Warning",37),
	TyphoonWarning("Typhoon Warning",38),
	VolcanoWarning("Volcano Warning",39),
	WindChillWarning("Wind Chill Warning",40),
	WinterStormWarning("Winter Storm Warning",41),
	AdminNotification("Admin Notification",42);
	
	NotificationType(String title, int id){
		this.title =  title;
		this.id = id;
	}
	
	private String title;
	private int id;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
