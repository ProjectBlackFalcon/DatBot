package game.plugin;

public class Hunt {
	
	private int[] startMapCoords;
	private String currentClue;
	private int currentIndex;
	private int numberOfIndex;
	private int currentDir;
	private int currentStep;
	private int numberOfSteps;
	private boolean rdyToFight = false; //untested
	private boolean phorror;
	private String phorrorName;
	
	/**
	 * Get the maximum hunt for the lvl
	 * @param lvl int : level of the character
	 * @return result[0] : elemId, result[1] : skillId
	 */
	public static int[] getHuntFromLvl(int lvl){
		int[] result = new int[2];
		if(lvl >= 20 && lvl < 40){
			result[0] = 484993;
			result[1] = 35426444;
		} else if(lvl >= 40 && lvl < 60){
			result[0] = 484993;
			result[1] = 35426445;
		} else if(lvl >= 60 && lvl < 80){
			result[0] = 484993;
			result[1] = 35426446;
		} else if(lvl >= 80 && lvl < 100){
			result[0] = 484993;
			result[1] = 35426447;
		} else if(lvl >= 100 && lvl < 120){
			result[0] = 484993;
			result[1] = 35426448;
		} else if(lvl >= 120 && lvl < 140){
			result[0] = 484993;
			result[1] = 35426449;
		} else if(lvl >= 140 && lvl < 160){
			result[0] = 484993;
			result[1] = 35426450;
		} else if(lvl >= 160 && lvl < 180){
			result[0] = 484993;
			result[1] = 35426451;
		} else if(lvl >= 180 && lvl < 200){
			result[0] = 484993;
			result[1] = 35426452;
		} else if(lvl >= 200){
			result[0] = 484993;
			result[1] = 35426453;
		}
		return result;
	}
	
	public static String getDirection(int cat){
		String s = "";
		switch(cat){
			case 2 :
				s = "s";
				break;
			case 6:
				s = "n";
				break;
			case 4:
				s = "w";
				break;
			case 0:
				s = "e";
				break;
		}
		return s;
	}

	public int[] getStartMapCoords() {
		return startMapCoords;
	}

	public void setStartMapCoords(int[] startMapCoords) {
		this.startMapCoords = startMapCoords;
	}

	public String getCurrentClue() {
		this.currentClue = this.currentClue.replaceAll("\"", "");
		return currentClue;
	}

	public void setCurrentClue(String currentClue) {
		this.currentClue = currentClue;
	}

	public int getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(int currentDir) {
		this.currentDir = currentDir;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public boolean isPhorror() {
		return phorror;
	}

	public void setPhorror(boolean phorror) {
		this.phorror = phorror;
	}

	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public int getNumberOfIndex() {
		return numberOfIndex;
	}

	public void setNumberOfIndex(int numberOfIndex) {
		this.numberOfIndex = numberOfIndex;
	}

	public String getPhorrorName() {
		return phorrorName;
	}

	public void setPhorrorName(String phorrorName) {
		this.phorrorName = phorrorName;
	}

	public boolean isRdyToFight() {
		return rdyToFight;
	}

	public void setRdyToFight(boolean rdyToFight) {
		this.rdyToFight = rdyToFight;
	}
}
