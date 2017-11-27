package Game.map.elements;

import Game.map.ColorMultiplicator;

public class GraphicalElement extends BasicElement {
    // Fields
    public long Altitude;
    public long ElementId;
    public ColorMultiplicator FinalTeint;
    public ColorMultiplicator Hue;
    public long Identifier;
    public double OffsetX;
    public double OffsetY;
    public double PixelOffsetX;
    public double PixelOffsetY;
    public ColorMultiplicator Shadow;
    
	public long getAltitude() {
		return Altitude;
	}
	public void setAltitude(long altitude) {
		Altitude = altitude;
	}
	public long getElementId() {
		return ElementId;
	}
	public void setElementId(long elementId) {
		ElementId = elementId;
	}
	public ColorMultiplicator getFinalTeint() {
		return FinalTeint;
	}
	public void setFinalTeint(ColorMultiplicator finalTeint) {
		FinalTeint = finalTeint;
	}
	public ColorMultiplicator getHue() {
		return Hue;
	}
	public void setHue(ColorMultiplicator hue) {
		Hue = hue;
	}
	public long getIdentifier() {
		return Identifier;
	}
	public void setIdentifier(long identifier) {
		Identifier = identifier;
	}
	public double getOffsetX() {
		return OffsetX;
	}
	public void setOffsetX(double offsetX) {
		OffsetX = offsetX;
	}
	public double getOffsetY() {
		return OffsetY;
	}
	public void setOffsetY(double offsetY) {
		OffsetY = offsetY;
	}
	public double getPixelOffsetX() {
		return PixelOffsetX;
	}
	public void setPixelOffsetX(double pixelOffsetX) {
		PixelOffsetX = pixelOffsetX;
	}
	public double getPixelOffsetY() {
		return PixelOffsetY;
	}
	public void setPixelOffsetY(double pixelOffsetY) {
		PixelOffsetY = pixelOffsetY;
	}
	public ColorMultiplicator getShadow() {
		return Shadow;
	}
	public void setShadow(ColorMultiplicator shadow) {
		Shadow = shadow;
	}
}
