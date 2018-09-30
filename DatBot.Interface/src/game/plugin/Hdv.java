package game.plugin;

import java.util.List;

import org.json.simple.JSONArray;

import protocol.network.types.game.data.items.BidExchangerObjectInfo;
import protocol.network.types.game.data.items.effects.ObjectEffect;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;
import protocol.network.types.game.data.items.effects.ObjectEffectMinMax;

public class Hdv {
	
	private List<Integer> types;
	private List<Integer> typesInTypes;
	
	private List<Long> minimalPrices;
	
	private List<BidExchangerObjectInfo> items; 
	private Long averagePrice;
	private Integer id;
	
	private int currentType;
	
	@SuppressWarnings("unchecked")
	public JSONArray getRessourcesPrices(){
		JSONArray array = new JSONArray();
		array.add(id);
		for (Long l : minimalPrices) {
			if(l == 0){
				array.add(-1);
			} else {
				array.add(l);
			}
		}
		array.add(averagePrice);
		return array;
	}
	
	/**
	 * Get formatted json of all the items for the typeIdItem
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getItemsPrices(){
		JSONArray array = new JSONArray();
		for (BidExchangerObjectInfo i : items) {
			JSONArray bigArray = new JSONArray();
			for (Long price : i.getPrices()) {
				if(price == 0){
					bigArray.add(-1);
				} else {
					bigArray.add(price);
				}
			}
//			bigArray.add(averagePrice);
			JSONArray arrayStats = new JSONArray();
			for (ObjectEffect effect : i.getEffects()) {
				JSONArray arraySuperStats = new JSONArray();
				arraySuperStats.add(effect.getActionId());
				if(effect instanceof ObjectEffectMinMax){
					JSONArray arraySuperSuperStats = new JSONArray();
					ObjectEffectMinMax effectMinMax = (ObjectEffectMinMax) effect;
					arraySuperSuperStats.add(effectMinMax.getMin());
					arraySuperSuperStats.add(effectMinMax.getMax());
					arraySuperStats.add(arraySuperSuperStats);
				} else if (effect instanceof ObjectEffectInteger) {
					ObjectEffectInteger effectInt = (ObjectEffectInteger) effect;
					arraySuperStats.add(effectInt.getValue());
				}
				arrayStats.add(arraySuperStats);
			}
			bigArray.add(arrayStats);	
			array.add(bigArray);
		}
		return array;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getItemNX(int id){
		JSONArray bigArray = new JSONArray();
		JSONArray array = new JSONArray();
		array.add(-1);
		array.add(-1);
		array.add(-1);
		array.add(-1);
		array.add("None");
		bigArray.add(array);
		return bigArray;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public List<Integer> getTypesInTypes() {
		return typesInTypes;
	}

	public void setTypesInTypes(List<Integer> typesInTypes) {
		this.typesInTypes = typesInTypes;
	}

	public int getCurrentType() {
		return currentType;
	}

	public void setCurrentType(int currentType) {
		this.currentType = currentType;
	}

	public List<BidExchangerObjectInfo> getItems() {
		return items;
	}

	public void setItems(List<BidExchangerObjectInfo> items) {
		this.items = items;
	}

	public Long getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Long averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Long> getMinimalPrices() {
		return minimalPrices;
	}

	public void setMinimalPrices(List<Long> list) {
		this.minimalPrices = list;
	}
	
}
