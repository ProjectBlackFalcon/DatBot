package Main.Frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import Game.Info;
import Game.Plugin.specBot;
import Game.map.Map;
import Game.map.MapMovement;
import Game.movement.CellMovement;
import Game.movement.Movement;
import Main.MainPlugin;
import protocol.network.Network;

/**
 * A frame with sample text components.
 */
@SuppressWarnings("serial")
public class TextComponentFrame extends JFrame {
	private static String timing;
	private final JTextArea textArea;

	public TextComponentFrame() {
		setTitle("ProtoBot");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		final JTextField textField = new JTextField();

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1, 1));
		northPanel.add(textField);

		add(northPanel, BorderLayout.NORTH);

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		// add button to append text into the text area

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (textField.getText().contains("moveToCell")) {
					int length = textField.getText().length();
					int cellId = 0;
					try {
						if (length == 14) {
							if (Integer.parseInt(textField.getText(11, 3)) < 560
									&& Integer.parseInt(textField.getText(11, 3)) >= 0) {
								cellId = Integer.parseInt(textField.getText(11, 3));
							} else
								cellId = -1;
						} else if (length == 13) {
							if (Integer.parseInt(textField.getText(11, 2)) < 560
									&& Integer.parseInt(textField.getText(11, 2)) >= 0) {
								cellId = Integer.parseInt(textField.getText(11, 2));
							} else
								cellId = -1;
						} else if (length == 12) {
							if (Integer.parseInt(textField.getText(11, 1)) < 560
									&& Integer.parseInt(textField.getText(11, 1)) >= 0) {
								cellId = Integer.parseInt(textField.getText(11, 1));
							} else
								cellId = -1;
						} else
							cellId = -1;

						if (cellId != -1) {
							if (Map.Cells.get(cellId).Mov == false || Info.cellId == cellId) {
								MainPlugin.frame.append("Cette case est inaccessible");
								return;
							} else {
								CellMovement mov = Movement.MoveToCell(cellId);
								mov.performMovement();
							}
						} else
							MainPlugin.frame.append("CellId out of bound");
					} catch (NumberFormatException | BadLocationException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				else if (textField.getText().contains("changeMap")) {
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
							int length = textField.getText().length();
							String direction = "";
							if (length == 14) {
								try {
									if(textField.getText(10, 4).equals("West"))
										direction = "West";
									if(textField.getText(10, 4).equals("East"))
										direction = "East";
								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (length == 15) {
								try {
									if(textField.getText(10, 5).equals("North"))
										direction = "North";
									if(textField.getText(10, 5).equals("South"))
										direction = "South";
								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							try {
								MapMovement mov = Movement.ChangeMap(direction);
								if (mov == null) {
									MainPlugin.frame.append("Déplacement impossible ! Un obstacle bloque le chemin !");
								}
								else {
									mov.PerformChangement();
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }
					});
				}
				
				else if (textField.getText().contains("goToMap")){
					try {
						Movement.goToMap(Info.coords[0],Info.coords[1],-13, -28,null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else if (textField.getText().contains("SpecBot")){
					try {
						specBot bot = new specBot();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else if (textField.getText().contains("stop")){
					try {
						Network.socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// Timing
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		timing = formatter.format(time);

	}

	public void append(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
		LocalTime time = LocalTime.now();
		timing = formatter.format(time);
		String newSt = "[" + timing + "] " + str;
		System.out.println(newSt);
//		textArea.append(newSt);
//		textArea.setCaretPosition(textArea.getText().length());
	}
	
	public void appendDebug(String str) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.FRANCE);
//		LocalTime time = LocalTime.now();
//		timing = formatter.format(time);
//		String newSt = "[" + timing + "] " + str + "\n";
		textArea.append(str +"\n");
//		textArea.setCaretPosition(textArea.getText().length());
	}

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 900;
}
