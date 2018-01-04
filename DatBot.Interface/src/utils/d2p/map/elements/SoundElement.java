package utils.d2p.map.elements;

import java.io.IOException;

import protocol.network.util.DofusDataReader;

public class SoundElement extends BasicElement {

	
    private int BaseVolume;
    private int FullVolumeDistance;
    private int MaxDelayBetweenLoops;
    private int MinDelayBetweenLoops;
    private int NullVolumeDistance;
    private int SoundId;

	@Override
	public void Init(DofusDataReader reader, int mapVersion)
	{
        try
		{
			SoundId = reader.readInt();
	        BaseVolume = reader.readShort();
	        FullVolumeDistance = reader.readInt();
	        NullVolumeDistance = reader.readInt();
	        MinDelayBetweenLoops = reader.readShort();
	        MaxDelayBetweenLoops = reader.readShort();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getBaseVolume()
	{
		return BaseVolume;
	}

	public void setBaseVolume(int baseVolume)
	{
		BaseVolume = baseVolume;
	}

	public int getFullVolumeDistance()
	{
		return FullVolumeDistance;
	}

	public void setFullVolumeDistance(int fullVolumeDistance)
	{
		FullVolumeDistance = fullVolumeDistance;
	}

	public int getMaxDelayBetweenLoops()
	{
		return MaxDelayBetweenLoops;
	}

	public void setMaxDelayBetweenLoops(int maxDelayBetweenLoops)
	{
		MaxDelayBetweenLoops = maxDelayBetweenLoops;
	}

	public int getMinDelayBetweenLoops()
	{
		return MinDelayBetweenLoops;
	}

	public void setMinDelayBetweenLoops(int minDelayBetweenLoops)
	{
		MinDelayBetweenLoops = minDelayBetweenLoops;
	}

	public int getNullVolumeDistance()
	{
		return NullVolumeDistance;
	}

	public void setNullVolumeDistance(int nullVolumeDistance)
	{
		NullVolumeDistance = nullVolumeDistance;
	}

	public int getSoundId()
	{
		return SoundId;
	}

	public void setSoundId(int soundId)
	{
		SoundId = soundId;
	}

	@Override
	public String toString()
	{
		return "SoundElement [BaseVolume=" + BaseVolume + ", FullVolumeDistance=" + FullVolumeDistance + ", MaxDelayBetweenLoops=" + MaxDelayBetweenLoops + ", MinDelayBetweenLoops=" + MinDelayBetweenLoops + ", NullVolumeDistance=" + NullVolumeDistance + ", SoundId=" + SoundId + "]";
	}

}
