package game.plugin;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import protocol.network.types.game.data.items.BidExchangerObjectInfo;
import protocol.network.types.game.data.items.effects.ObjectEffect;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;
import protocol.network.types.game.data.items.effects.ObjectEffectMinMax;

public class Hdv {
	
	private List<Integer> types;
	private List<Integer> typesInTypes;
	
	private List<BidExchangerObjectInfo> items; 
	private Long averagePrice;
	private Integer id;
	
	private int currentType;
	
	/**
	 * Get formatted json of all the items for the typeIdItem
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getItemsPrices(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		JSONArray array = new JSONArray();
		for (BidExchangerObjectInfo i : items) {
			for (Long price : i.getPrices()) {
				array.add(price);
			}
			array.add(averagePrice);
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
			array.add(arrayStats);
		}
		jsonObject.put("Item", array);
		return jsonObject.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	public String getItemNX(int id){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		JSONArray array = new JSONArray();
		array.add(-1);
		array.add(-1);
		array.add(-1);
		array.add(-1);
		array.add("None");
		jsonObject.put("Item", array);
		return jsonObject.toJSONString();
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
	
}
