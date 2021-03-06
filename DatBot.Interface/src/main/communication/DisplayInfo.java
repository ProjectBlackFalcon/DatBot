package main.communication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import ia.Log;
import utils.GameData;

public class DisplayInfo {

	private int botInstance;
	private String name;
	private Log logIa;
	protected boolean displayPacket;

	protected JFrame f;
	private FileOutputStream fileOutputStream;
	private JTextPane text;

	private PrintStream log;
	public PrintStream debug;
	File fileNetwork;

	public DisplayInfo(int botInstance, boolean displayPacket, String name) {
		this.name = name;
		this.fileNetwork = DisplayInfo.createOrGetFile(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Network.txt");
		this.botInstance = botInstance;
		this.displayPacket = displayPacket;
		if (displayPacket) {
			initComponent();
		}
		initLogs();
		this.logIa = new Log(botInstance, name);
//		this.logIa.initLogs();
	}

	private void initComponent() {
		// Jframe Creation
		f = new JFrame("Log");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(950, 25);
		f.setPreferredSize(new Dimension(900, 600));
		// JPanel creation
		JPanel panel = new JPanel();
		f.getContentPane().add(panel);
		// JtextArea creation
		text = new JTextPane();
		text.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		text.setFont(new Font("Lucida Console", text.getFont().getStyle(), 12));
		panel.add(text);
		// Scroll
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		f.add(scroll);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * Inits all the logs.
	 * 
	 * @author baptiste
	 */
	private void initLogs() {
//					fileOutputStream = new FileOutputStream(createOrGetFile(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Network.txt"));
//					log = new PrintStream(fileOutputStream);
//					debug = System.out;
	}

	/**
	 * @return String timestamp
	 */
	public static String getTiming() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		String timing = formatter.format(time);
		return timing;
	}
	
	public static String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    s = s.replaceAll("œ", "oe");
	    return s;
	}

	public static void appendDebugLog(String errorType, String s) {
		File file = createOrGetFile(GameData.getPathDatBot() + "//packetErrors.txt");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File(file.getAbsolutePath()),
				true));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		writer.append(LocalDateTime.now() + " : " + errorType + "\n");
		writer.append("\t" + s + "\n");
		writer.append("\n");
		writer.close();
	}

	public static File createOrGetFile(String s) {
		File file = new File(s);
		if (!file.exists()) {
			System.out.println("File do not exist : " +s);
			try {
				file.createNewFile();

			}
			catch (IOException e) {
				System.out.println("File not created");
				e.printStackTrace();
			}
		} else {
			System.out.println("File exist : " +s);
		}
		return file;
	}

	/**
	 * Append the text eiher on the panel or System.out
	 * 
	 * @param String str
	 * @param boolean b ; True:panel ; False:System.out
	 */
	public void appendLog(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		String timing = formatter.format(time);
		if (displayPacket) {
			appendToPane(text, "[" + timing + "] ", Color.black);
			if (str.contains("Envoi")) {
				appendToPane(text, str + "\n", new Color(0, 0, 140));
			}
			else {
				appendToPane(text, str + "\n", new Color(0, 110, 0));
			}
		}
//		List<String> lines;
//		try {
//			lines = Files.readAllLines(Paths.get(GameData.getPathDatBot() + "/Utils/BotsLogs/" + name + "_Network.txt"), Charset.defaultCharset());
//			if (lines.size() > 100000) {
//				clearTheFile();
//			}
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File(fileNetwork.getAbsolutePath()),
				true));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		writer.append(LocalDateTime.now() + " : " + str + "\n");
		writer.close();	
		}

	/**
	 * Append the text on the panel depending on the String
	 * 
	 * @param JtextPane tp
	 * @param String msg
	 * @param Color c
	 */
	private void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida	Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
	
	public static String[] split(String orig) {
	    List<String> splitted = new ArrayList<String>();
	    int nextingLevel = 0;
	    StringBuilder result = new StringBuilder();
	    for (char c : orig.toCharArray()) {
	        if (c == ',' && nextingLevel == 0) {
	            splitted.add(result.toString());
	            result.setLength(0);// clean buffer
	        } else {
	            if (c == '[')
	                nextingLevel++;
	            if (c == ']')
	                nextingLevel--;
	            result.append(c);
	        }
	    }
	    // Thanks PoeHah for pointing it out. This adds the last element to it.
	    splitted.add(result.toString());
	    String[] s1 = new String[splitted.size()];
	    for (int i = 0; i < splitted.size() ; i++) {
			s1[i] = splitted.get(i);
		}
	    return s1;
	}

	private void clearTheFile() {
		FileWriter fwOb;
		try {
			fwOb = new FileWriter("log_network" + botInstance + ".txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void append(Object str) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
//		LocalTime time = LocalTime.now();
//		String timing = formatter.format(time);
//		String newSt = "[" + timing + "] [BOT " + botInstance + "] " + str;
//		debug.println(newSt);
	}

	public static String cleanString(String s) {
		s = s.toLowerCase();
		s = s.replaceAll("\"", "");
		return s == null ? null : Normalizer.normalize(s, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	public int getBotInstance() {
		return botInstance;
	}

	public void setBotInstance(int botInstance) {
		this.botInstance = botInstance;
	}

	public JFrame getF() {
		return f;
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public Log getLog() {
		return logIa;
	}
}
