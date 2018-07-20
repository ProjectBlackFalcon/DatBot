package utils.d2o.modules;

import java.util.Arrays;
import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class Item {
	public static final String MODULE = "Items";
	private static final boolean[] FILTER_EQUIPEMENT = {false, true, true, true, true, true, false, true, true, false, true, true, true, true, false, false, false, false, false, false, false, false, true, true};
	private static final boolean[] FILTER_CONSUMABLES = {false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	private static final boolean[] FILTER_RESSOURCES = {false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	private static final boolean[] FILTER_QUEST = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false};
	public static final int EQUIPEMENT_CATEGORY = 0;
	public static final int CONSUMABLES_CATEGORY = 1;
	public static final int RESSOURCES_CATEGORY = 2;
	public static final int QUEST_CATEGORY = 3;
	public static final int OTHER_CATEGORY = 4;
	public static final int MAX_JOB_LEVEL_GAP = 100;
	
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int id;
	public int nameId;
	public boolean isSaleable;
	public boolean objectIsDisplayOnWeb;
	public int typeId;
	public int descriptionId;
	public int iconId;
	public int level;
	public int realWeight;
	public boolean cursed;
	public int useAnimationId;
	public boolean usable;
	public boolean targetable;
	public boolean exchangeable;
	public double price;
	public boolean twoHanded;
	public boolean etheral;
	public int itemSetId;
	public String criteria;
	public String criteriaTarget;
	public boolean hideEffects;
	public boolean enhanceable;
	public boolean nonUsableOnAnother;
	public int appearanceId;
	public boolean secretRecipe;
	public Vector<Integer> dropMonsterIds;
	public int recipeSlots;
	public Vector<Integer> recipeIds;
	public boolean bonusIsSecret;
	public Vector<EffectInstance> possibleEffects;
	public Vector<Integer> favoriteSubAreas;
	public int favoriteSubAreasBonus;
	public int craftXpRatio;
	public boolean needUseConfirm;
	public boolean isDestructible;
	public Vector<Vector<Double>> nuggetsBySubarea;
	public Vector<Integer> containerIds;
	public Vector<Vector<Integer>> resourcesBySubarea;
	private String _name;
	private String _description;
	private boolean craftVisible;
	private boolean craftFeasible;
	private ItemType _type;
	private int _weight;
	private ItemSet _itemSet;
	private double _nuggetsQuantity = 0;

	public static synchronized Item getItemById(int id) {
		Item item = (Item) GameData.getObject(MODULE, id);
		if(item != null)
			return item;
		return (Item) GameData.getObject(MODULE, 666);
	}

	public static Item[] getItems() {
		Object[] objArray = GameData.getObjects(MODULE);
		return Arrays.copyOf(objArray, objArray.length, Item[].class);
	}

	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}

	public String getDescription() {
		if(this._description == null)
			this._description = d2iManager.getText(this.descriptionId);
		return this._description;
	}

	public int getWeight() {
		return this._weight;
	}

	public void setWeight(int weight) {
		this._weight = weight;
	}

	public ItemType getType() {
		if(this._type == null)
			this._type = ItemType.getItemTypeById(this.typeId);
		return this._type;
	}

	public boolean isWeapon() {
		return false;
	}

	public ItemSet getItemSet() {
		if(this._itemSet == null)
			this._itemSet = ItemSet.getItemSetById(this.itemSetId);
		return this._itemSet;
	}
	
	public int getCategory() {
		ItemType type = getType();
		if(FILTER_EQUIPEMENT[type.superTypeId])
			return EQUIPEMENT_CATEGORY;
		else if(FILTER_CONSUMABLES[type.superTypeId])
			return CONSUMABLES_CATEGORY;
		else if(FILTER_RESSOURCES[type.superTypeId])
			return RESSOURCES_CATEGORY;
		else if(FILTER_QUEST[type.superTypeId])
			return QUEST_CATEGORY;
		else
			return OTHER_CATEGORY;
	}	
	
	public double getNuggetsQuantity() {
		if(this._nuggetsQuantity == 0)
			for(Vector<Double> nuggets : this.nuggetsBySubarea)
				this._nuggetsQuantity += nuggets.get(1);
		return this._nuggetsQuantity;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", nameId=" + nameId + ", isSaleable=" + isSaleable + ", objectIsDisplayOnWeb=" + objectIsDisplayOnWeb + ", typeId=" + typeId + ", descriptionId=" + descriptionId + ", iconId=" + iconId + ", level=" + level + ", realWeight=" + realWeight + ", cursed=" + cursed + ", useAnimationId=" + useAnimationId + ", usable="
			+ usable + ", targetable=" + targetable + ", exchangeable=" + exchangeable + ", price=" + price + ", twoHanded=" + twoHanded + ", etheral=" + etheral + ", itemSetId=" + itemSetId + ", criteria=" + criteria + ", criteriaTarget=" + criteriaTarget + ", hideEffects=" + hideEffects + ", enhanceable=" + enhanceable + ", nonUsableOnAnother="
			+ nonUsableOnAnother + ", appearanceId=" + appearanceId + ", secretRecipe=" + secretRecipe + ", dropMonsterIds=" + dropMonsterIds + ", recipeSlots=" + recipeSlots + ", recipeIds=" + recipeIds + ", bonusIsSecret=" + bonusIsSecret + ", possibleEffects=" + possibleEffects + ", favoriteSubAreas=" + favoriteSubAreas
			+ ", favoriteSubAreasBonus=" + favoriteSubAreasBonus + ", craftXpRatio=" + craftXpRatio + ", needUseConfirm=" + needUseConfirm + ", isDestructible=" + isDestructible + ", nuggetsBySubarea=" + nuggetsBySubarea + ", containerIds=" + containerIds + ", resourcesBySubarea=" + resourcesBySubarea + ", _name=" + _name + ", _description="
			+ _description + ", craftVisible=" + craftVisible + ", craftFeasible=" + craftFeasible + ", _type=" + _type + ", _weight=" + _weight + ", _itemSet=" + _itemSet + ", _nuggetsQuantity=" + _nuggetsQuantity + "]";
	}
}