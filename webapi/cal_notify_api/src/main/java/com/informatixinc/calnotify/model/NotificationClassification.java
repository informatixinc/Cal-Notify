package com.informatixinc.calnotify.model;

public enum NotificationClassification {
	
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
	AdminNotification("Admin Notification",42),
	AvalancheWatch("Avalanche Watch",43),
	BlizzardWatch("Blizzard Watch",44),
	CoastalFloodWatch("Coastal Flood Watch",45),
	ExcessiveHeatWatch("Excessive Heat Watch",46),
	ExtremeColdWatch("Extreme Cold Watch",47),
	FireWeatherWatch("Fire Weather Watch",48),
	FlashFloodWatch("Flash Flood Watch",49),
	FloodWatch("Flood Watch",50),
	FreezeWatch("Freeze Watch",51),
	GaleWatch("Gale Watch",52),
	HardFreezeWatch("Hard Freeze Watch",53),
	HazardousSeasWatch("Hazardous Seas Watch",54),
	HeavyFreezingSprayWatch("Heavy Freezing Spray Watch",55),
	HighWindWatch("High Wind Watch",56),
	HurricaneForceWindWatch("Hurricane Force Wind Watch",57),
	HurricaneWatch("Hurricane Watch",58),
	LakeEffectSnowWatch("Lake Effect Snow Watch",59),
	LakeshoreFloodWatch("Lakeshore Flood Watch",60),
	SevereThunderstormWatch("Severe Thunderstorm Watch",61),
	StormWatch("Storm Watch",62),
	TornadoWatch("Tornado Watch",63),
	TropicalStormWatch("Tropical Storm Watch",64),
	TsunamiWatch("Tsunami Watch",65),
	TyphoonWatch("Typhoon Watch",66),
	WindChillWatch("Wind Chill Watch",67),
	WinterStormWatch("Winter Storm Watch",68),
	AirStagnationAdvisory("Air Stagnation Advisory",69),
	ArroyoandSmallStreamFloodAdvisory("Arroyo and Small Stream Flood Advisory",71),
	AshfallAdvisory("Ashfall Advisory",72),
	AvalancheAdvisory("Avalanche Advisory",73),
	BlowingDustAdvisory("Blowing Dust Advisory",74),
	BriskWindAdvisory("Brisk Wind Advisory",75),
	CoastalFloodAdvisory("Coastal Flood Advisory",76),
	DenseFogAdvisory("Dense Fog Advisory",77),
	DenseSmokeAdvisory("Dense Smoke Advisory",78),
	FloodAdvisory("Flood Advisory",79),
	FreezingFogAdvisory("Freezing Fog Advisory",80),
	FreezingRainAdvisory("Freezing Rain Advisory",81),
	FreezingSprayAdvisory("Freezing Spray Advisory",82),
	FrostAdvisory("Frost Advisory",83),
	HeatAdvisory("Heat Advisory",84),
	HighSurfAdvisory("High Surf Advisory",85),
	HydrologicAdvisory("Hydrologic Advisory",86),
	LakeEffectSnowAdvisory("Lake Effect Snow Advisory",87),
	LakeWindAdvisory("Lake Wind Advisory",88),
	LakeshoreFloodAdvisory("Lakeshore Flood Advisory",89),
	LowWaterAdvisory("Low Water Advisory",90),
	SmallCraftAdvisory("Small Craft Advisory",91),
	SmallCraftAdvisoryForHazardousSeas("Small Craft Advisory For Hazardous Seas",92),
	SmallCraftAdvisoryForRoughBar("Small Craft Advisory For Rough Bar",93),
	SmallCraftAdvisoryForWinds("Small Craft Advisory For Winds",94),
	SmallStreamFloodAdvisory("Small Stream Flood Advisory",95),
	TsunamiAdvisory("Tsunami Advisory",96),
	WindAdvisory("Wind Advisory",97),
	WindChillAdvisory("Wind Chill Advisory",98),
	WinterWeatherAdvisory("Winter Weather Advisory",99);
	
	NotificationClassification(String title, int id){
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
