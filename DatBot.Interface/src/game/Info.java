package game;

import java.util.ArrayList;
import java.util.List;

import gamedata.d2o.modules.SpellLevel;

public class Info {
	/*
	 * Game Info
	 */

	public Info(String nameAccount, String password, String name, String server)
	{
		this.nameAccount = nameAccount;
		this.password = password;
		this.name = name;
		this.server = server;
	}

	// Map info
	private double mapId;
	private int[] coords = new int[2];
	private int cellId;
	private ArrayList<ArrayList<Integer>> cells = new ArrayList<ArrayList<Integer>>();
	private long worldmap;

	// Account info
	private String nameAccount = "";
	private String password = "";
	private String name = "";
	private String server = "";
	private long actorId = -1;
	private boolean isConnected = false;
	private boolean printDc = true;
	private long timeLeftSub = -1;

	// Bot info

	private int botInstance;
	private int msgIdModel = -1;
	private int msgIdFight = -1;
	
	// Archimonster
	private boolean archiOnMap = false;
	private String archiName = "";

	// Stats info
	private int lvl = -1;
	private int weigthMax;
	private int weight;
	private List<SpellLevel> spells;
	// Game utils
	private boolean isRiding = false;
	private boolean waitForMov = true;
	private boolean waitForHarvestSuccess = false;
	private boolean waitForHarvestFailure = false;
	private boolean harvestSuccess = false;
	private boolean harvestFailure = false;
	private boolean inHunt = false;
	private boolean inExchange = false;
	private boolean havenBag = false;
	private boolean currentMapTrigger = false;
	private boolean newStats = false;

	private boolean interactiveUsed = false;
	private boolean newMap = false;
	private boolean leaveDialog = false;
	private boolean Storage = false;
	private boolean StorageUpdate = false;
	private boolean basicNoOperationMsg = false;
	private boolean leaveExchange = false;
	private boolean joinedFight = false;
	private boolean isTurn = false;
	private boolean initFight = true;
	private boolean acknowledged = false;
	private boolean huntAnswered = false;
	private boolean stepSuccess = false;
	private boolean stepFailed = false;
	private boolean exchangeBidSeller = false;
	private boolean exchangeDD = false;
	private boolean emoteLaunched = false;
	private boolean mountxpmsg = false;
	private boolean mountSet = false;
	private boolean mountRiding = false;
	private boolean objectUse = false;
	private boolean textMessage = false;
	private boolean caracsAffected = false;
	private boolean movObject = false;


	public void setBooleanToFalse()
	{
		harvestSuccess = false;
		harvestFailure = false;
		exchangeBidSeller = false;
		interactiveUsed = false;
		newMap = false;
		leaveDialog = false;
		Storage = false;
		StorageUpdate = false;
		basicNoOperationMsg = false;
		leaveExchange = false;
		acknowledged = false;
		newStats = false;
		huntAnswered = false;
		stepSuccess = false;
		stepFailed = false;
		exchangeDD = false;
		emoteLaunched = false;
		mountxpmsg = false;
		mountSet = false;
		mountRiding = false;
		objectUse = false;
		textMessage = false;
		caracsAffected = false;
		movObject = false;
		currentMapTrigger = false;
		archiOnMap = false;
	}
	
	public boolean threadDone = true;

	public double getMapId()
	{
		return mapId;
	}

	public void setMapId(double mapId)
	{
		this.mapId = mapId;
	}

	public int[] getCoords()
	{
		return coords;
	}

	public void setCoords(int[] coords)
	{
		this.coords = coords;
	}

	public int getCellId()
	{
		return cellId;
	}

	public void setCellId(int cellId)
	{
		this.cellId = cellId;
	}

	public ArrayList<ArrayList<Integer>> getCells()
	{
		return cells;
	}

	public void setCells(ArrayList<ArrayList<Integer>> cells)
	{
		this.cells = cells;
	}

	public long getWorldmap()
	{
		return worldmap;
	}

	public void setWorldmap(long worldmap)
	{
		this.worldmap = worldmap;
	}

	public String getNameAccount()
	{
		return nameAccount;
	}

	public void setNameAccount(String nameAccount)
	{
		this.nameAccount = nameAccount;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public long getActorId()
	{
		return actorId;
	}

	public void setActorId(long actorId)
	{
		this.actorId = actorId;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}

	public int getBotInstance()
	{
		return botInstance;
	}

	public void setBotInstance(int botInstance)
	{
		this.botInstance = botInstance;
	}

	public int getMsgIdModel()
	{
		return msgIdModel;
	}
	
	public int addAndGetMsgIdModel(){
		return ++this.msgIdModel;
	}

	public void setMsgIdModel(int msgIdModel)
	{
		this.msgIdModel = msgIdModel;
	}

	public int getMsgIdFight()
	{
		return msgIdFight;
	}
	
	public int addAndGetMsgIdFight(){
		return ++this.msgIdFight;
	}

	public void setMsgIdFight(int msgIdFight)
	{
		this.msgIdFight = msgIdFight;
	}

	public int getLvl()
	{
		return lvl;
	}

	public void setLvl(int lvl)
	{
		this.lvl = lvl;
	}

	public int getWeigthMax()
	{
		return weigthMax;
	}

	public void setWeigthMax(int weigthMax)
	{
		this.weigthMax = weigthMax;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public boolean isWaitForMov()
	{
		return waitForMov;
	}

	public void setWaitForMov(boolean waitForMov)
	{
		this.waitForMov = waitForMov;
	}

	public boolean isWaitForHarvestSuccess()
	{
		return waitForHarvestSuccess;
	}

	public void setWaitForHarvestSuccess(boolean waitForHarvestSuccess)
	{
		this.waitForHarvestSuccess = waitForHarvestSuccess;
	}

	public boolean isWaitForHarvestFailure()
	{
		return waitForHarvestFailure;
	}

	public void setWaitForHarvestFailure(boolean waitForHarvestFailure)
	{
		this.waitForHarvestFailure = waitForHarvestFailure;
	}

	public boolean isInteractiveUsed()
	{
		return interactiveUsed;
	}

	public void setInteractiveUsed(boolean interactiveUsed)
	{
		this.interactiveUsed = interactiveUsed;
	}

	public boolean isNewMap()
	{
		return newMap;
	}

	public void setNewMap(boolean newMap)
	{
		this.newMap = newMap;
	}

	public boolean isLeaveDialog()
	{
		return leaveDialog;
	}

	public void setLeaveDialog(boolean leaveDialog)
	{
		this.leaveDialog = leaveDialog;
	}

	public boolean isStorage()
	{
		return Storage;
	}

	public void setStorage(boolean storage)
	{
		Storage = storage;
	}

	public boolean isStorageUpdate()
	{
		return StorageUpdate;
	}

	public void setStorageUpdate(boolean storageUpdate)
	{
		StorageUpdate = storageUpdate;
	}

	public boolean isBasicNoOperationMsg()
	{
		return basicNoOperationMsg;
	}

	public void setBasicNoOperationMsg(boolean basicNoOperationMsg)
	{
		this.basicNoOperationMsg = basicNoOperationMsg;
	}

	public boolean isLeaveExchange()
	{
		return leaveExchange;
	}

	public void setLeaveExchange(boolean leaveExchange)
	{
		this.leaveExchange = leaveExchange;
	}

	public boolean isJoinedFight()
	{
		return joinedFight;
	}

	public void setJoinedFight(boolean joinedFight)
	{
		this.joinedFight = joinedFight;
	}

	public boolean isTurn()
	{
		return isTurn;
	}

	public void setTurn(boolean isTurn)
	{
		this.isTurn = isTurn;
	}

	public boolean isInitFight()
	{
		return initFight;
	}

	public void setInitFight(boolean initFight)
	{
		this.initFight = initFight;
	}

	public boolean isAcknowledged()
	{
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged)
	{
		this.acknowledged = acknowledged;
	}

	public boolean isHuntAnswered() {
		return huntAnswered;
	}

	public void setHuntAnswered(boolean huntAnswered) {
		this.huntAnswered = huntAnswered;
	}

	public boolean isInHunt() {
		return inHunt;
	}

	public void setInHunt(boolean inHunt) {
		this.inHunt = inHunt;
	}

	public boolean isStepSuccess() {
		return stepSuccess;
	}

	public void setStepSuccess(boolean stepSuccess) {
		this.stepSuccess = stepSuccess;
	}

	public boolean isExchangeBidSeller() {
		return exchangeBidSeller;
	}

	public void setExchangeBidSeller(boolean exchangeBidSeller) {
		this.exchangeBidSeller = exchangeBidSeller;
	}

	public boolean isInExchange()
	{
		return inExchange;
	}

	public void setInExchange(boolean inExchange)
	{
		this.inExchange = inExchange;
	}

	public boolean isRiding()
	{
		return isRiding;
	}

	public void setRiding(boolean isRiding)
	{
		this.isRiding = isRiding;
	}

	public boolean isExchangeDD() {
		return exchangeDD;
	}

	public void setExchangeDD(boolean exchangeDD) {
		this.exchangeDD = exchangeDD;
	}

	public boolean isEmoteLaunched() {
		return emoteLaunched;
	}

	public void setEmoteLaunched(boolean emoteLaunched) {
		this.emoteLaunched = emoteLaunched;
	}

	public boolean isMountxpmsg() {
		return mountxpmsg;
	}

	public void setMountxpmsg(boolean mountxpmsg) {
		this.mountxpmsg = mountxpmsg;
	}

	public boolean isMountSet() {
		return mountSet;
	}

	public void setMountSet(boolean mountSet) {
		this.mountSet = mountSet;
	}

	public boolean isMountRiding() {
		return mountRiding;
	}

	public void setMountRiding(boolean mountRiding) {
		this.mountRiding = mountRiding;
	}

	public boolean isObjectUse() {
		return objectUse;
	}

	public void setObjectUse(boolean objectUse) {
		this.objectUse = objectUse;
	}
	
	public boolean isPrintDc() {
		return printDc;
	}

	public void setPrintDc(boolean printDc) {
		this.printDc = printDc;
	}

	public boolean isTextMessage() {
		return textMessage;
	}

	public void setTextMessage(boolean textMessage) {
		this.textMessage = textMessage;
	}

	public boolean isStepFailed() {
		return stepFailed;
	}

	public void setStepFailed(boolean stepFailed) {
		this.stepFailed = stepFailed;
	}

	public boolean isHarvestSuccess() {
		return harvestSuccess;
	}

	public void setHarvestSuccess(boolean harvestSuccess) {
		this.harvestSuccess = harvestSuccess;
	}

	public boolean isHarvestFailure() {
		return harvestFailure;
	}

	public void setHarvestFailure(boolean harvestFailure) {
		this.harvestFailure = harvestFailure;
	}

	public boolean isCaracsAffected() {
		return caracsAffected;
	}

	public void setCaracsAffected(boolean caracsAffected) {
		this.caracsAffected = caracsAffected;
	}

	public boolean isHavenBag() {
		return havenBag;
	}

	public void setHavenBag(boolean havenBag) {
		this.havenBag = havenBag;
	}

	public List<SpellLevel> getSpells() {
		return spells;
	}

	public void setSpells(List<SpellLevel> spells) {
		this.spells = spells;
	}

	public long getTimeLeftSub() {
		return timeLeftSub;
	}

	public void setTimeLeftSub(long timeLeftSub) {
		this.timeLeftSub = timeLeftSub;
	}

	public boolean isMovObject() {
		return movObject;
	}

	public void setMovObject(boolean movObject) {
		this.movObject = movObject;
	}

	public boolean isCurrentMapTrigger() {
		return currentMapTrigger;
	}

	public void setCurrentMapTrigger(boolean currentMapTrigger) {
		this.currentMapTrigger = currentMapTrigger;
	}

	public boolean isArchiOnMap() {
		return archiOnMap;
	}

	public void setArchiOnMap(boolean archiOnMap) {
		this.archiOnMap = archiOnMap;
	}

	public String getArchiName() {
		return archiName;
	}

	public void setArchiName(String archiName) {
		this.archiName = archiName;
	}

	public boolean isNewStats()
	{
		return newStats;
	}

	public void setNewStats(boolean newStats)
	{
		this.newStats = newStats;
	}
}
