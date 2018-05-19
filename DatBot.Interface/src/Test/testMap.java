package Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;

import ia.fight.brain.Game;
import main.communication.DisplayInfo;
import protocol.frames.LatencyFrame;
import protocol.network.Network;
import protocol.network.util.DofusDataReader;
import utils.GameData;
import utils.d2i.d2iManager;
import utils.d2o.D2oManager;
import utils.d2p.MapManager;
import utils.d2p.map.Map;

public class testMap {

	public static void main(String[] args) throws IOException, InterruptedException {
		for (int i = 0; i < 200 ; i++){
			System.out.println(stop(1));
		}
	}
	
	private static long stop(int deviation) throws InterruptedException{
		double gauss = new Random().nextGaussian();
		return (long) (Math.abs(gauss*deviation) * 1000);
	}
}
