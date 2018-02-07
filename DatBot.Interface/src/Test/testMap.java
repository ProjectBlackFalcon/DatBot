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
        long startAt1 = System.currentTimeMillis();
        d2iManager d2iManager = new d2iManager(Network.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
        System.out.println(utils.d2i.d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println(d2iManager.getText(1));
        System.out.println("Read in " + (System.currentTimeMillis() - startAt1) + "ms");
        System.gc();
        while(true){
        	try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}
