package Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

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

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.initLogs();
		game.initGame(95422468);
	}
}
