package ia.fight.map;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;

public class GameViz extends JFrame{
	
	public Panel panel;

	public GameViz(Map map, String botName) {
		panel = new Panel(map);
		setTitle(botName);
		setSize(1015, 700);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setUndecorated(false);
		add(panel);
//		setVisible(Game.DISPLAY_GUI);
	}

	public void update(ArrayList<PlayingEntity> playingEntities) {
		panel.update(playingEntities);
	}
	
	public void update(ArrayList<Position> challengers, ArrayList<Position> defenders) {
		panel.update(challengers, defenders);
	}
}
