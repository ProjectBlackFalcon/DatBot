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

import utils.GameData;

public class DisplayInfo {

	private int botInstance;
	protected boolean displayPacket;

	protected JFrame f;
	private FileOutputStream fileOutputStream;
	private JTextPane text;

	private PrintStream log;
	public PrintStream debug;

	public DisplayInfo(int botInstance, boolean displayPacket) {
		this.botInstance = botInstance;
		this.displayPacket = displayPacket;
		if (displayPacket) {
			initComponent();
		}

		initLogs();
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
		try {
			// log = System.out;
			fileOutputStream = new FileOutputStream(GameData.getPathDatBot() + "//log_network" + botInstance + ".txt");
			log = new PrintStream(fileOutputStream);
			//			debug = new PrintStream(new FileOutputStream("debug.txt"));
			debug = System.out;
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
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
	    return s;
	}

	public static void appendDebugLog(String errorType, String s) {
		File file = new File(GameData.getPathDatBot() + "//packetErrors.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();

			}
			catch (IOException e) {
				System.out.println("File not created");
				e.printStackTrace();
			}
		}
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
		else {
			List<String> lines;
			try {
				lines = Files.readAllLines(Paths.get(GameData.getPathDatBot() + "//log_network" + botInstance + ".txt"), Charset.defaultCharset());
				if (lines.size() > 1000) {
					clearTheFile();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			String newSt = "[" + timing + "] [BOT " + this.botInstance + "] " + str;
			log.println(newSt);
		}
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		String timing = formatter.format(time);
		String newSt = "[" + timing + "] [BOT " + botInstance + "] " + str;
		debug.println(newSt);
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
}
