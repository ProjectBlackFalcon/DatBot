package game.map.elements;

public abstract class BasicElement {

    public static BasicElement GetElementFromType(int typeId)
    {
        switch (typeId)
        {
            case 2:
                return new GraphicalElement();
            case 33:
                return new SoundElement();
        }
        return null;
    }
}
