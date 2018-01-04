package utils.d2p.map.elements;

import protocol.network.util.DofusDataReader;

public abstract class BasicElement
{
    // Methods

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

    public abstract void Init(DofusDataReader reader, int mapVersion);
}
