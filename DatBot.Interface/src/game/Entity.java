package game;

public class Entity {
	
	private int cellId;
	private double id;
	private String name;
	
	public Entity(int cellId, double contextualId){
		this.cellId = cellId;
		this.id = contextualId;
	}

	public int getCellId()
	{
		return cellId;
	}

	public void setCellId(int cellId)
	{
		this.cellId = cellId;
	}

	public double getId()
	{
		return id;
	}

	public void setId(double id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
