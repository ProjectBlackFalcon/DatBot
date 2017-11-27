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

import ia.fight.brain.PlayingEntity;

public class GameViz extends JFrame{
	
	Panel panel;

	public GameViz(Map map) {
		
		panel = new Panel(map);
		
		setTitle("Visualizer");
		setSize(662, 662);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		add(panel);
		setVisible(true);
	}

	public void update(ArrayList<PlayingEntity> playingEntities) {
		panel.update(playingEntities);
	}
}
