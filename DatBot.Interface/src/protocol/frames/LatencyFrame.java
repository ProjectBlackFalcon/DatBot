package protocol.frames;

import java.util.ArrayList;
import java.util.List;

public class LatencyFrame {
	
    private final int LatencyAvgBufferSize = 50;
    private List<Integer> _latencyBuffer;
    private long _latestSent;
    public int Sequence ;

    public LatencyFrame()
    {
        _latencyBuffer = new ArrayList<Integer>();
        Sequence = 1;
    }
    
    public void latestSent()
    {
        _latestSent = System.currentTimeMillis();
    }

    public void updateLatency()
    {
        long lastReceive = System.currentTimeMillis();

        if (_latestSent == 0) return;
        
        _latencyBuffer.add((int) (lastReceive - _latestSent));
        _latestSent = 0;

        if (_latencyBuffer.size() > LatencyAvgBufferSize)
            _latencyBuffer.remove(0);
    }

    public int getLatencyAvg()
    {
        if (_latencyBuffer.size() == 0)
            return 0;
        int _loc1_ = 0;
        for (Integer _loc2_ : _latencyBuffer) {
        	_loc1_ += _loc2_;
		}
        return _loc1_ / _latencyBuffer.size();
    }

    public int getSamplesCount()
    {
        return _latencyBuffer.size();
    }

    public int GetSamplesMax()
    {
        return (int) LatencyAvgBufferSize;
    }
}
