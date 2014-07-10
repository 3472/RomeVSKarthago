package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;

import core.City_Graph;
import core.ConsolGame;
import core.Owner;
import core.Player;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	private JLabel rom;
	private JLabel karthago;
	private JLabel lblGameOver;
	private Board gameBoard;
	private ConsolGame game;
	/**
	 * Launch the application.
	 */
	
	@Override
	public void paint(Graphics g) {	

		rom.setText("Rom: Punkte: " + gameBoard.getGraph().getScore(Owner.Rom));
		karthago.setText("Karthago: Punkte: " + gameBoard.getGraph().getScore(Owner.Cathargo));
		gameBoard.repaint();
		super.paint(g);
		if(game.isGameOver()) {
			lblGameOver.setVisible(true);
		}
	}
	
	/**
	 * Create the frame.
	 */
	public GameFrame(Board board, ConsolGame g) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {2};
		gbl_contentPane.rowHeights = new int[] {1};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		gameBoard = board;
		game = g;
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 3.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		//contentPane.add(board, gbc_panel);
		board.setPreferredSize(board.getSize());
		final JScrollPane scrollPane = new JScrollPane(board);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(800, 800));
		contentPane.add(scrollPane, gbc_panel);
		
	
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.weightx = 1.0;
		gbc_buttonPanel.ipady = 1;
		gbc_buttonPanel.ipadx = 1;
		gbc_buttonPanel.gridx = 1;
		gbc_buttonPanel.gridy = 0;
		contentPane.add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new GridLayout(12, 0, 0, 0));
		
		JLabel spacer = new JLabel(" ");
		buttonPanel.add(spacer);
		
		rom = new JLabel("Rom: Punkte: " + board.getGraph().getScore(Owner.Rom));
		buttonPanel.add(rom);
		
		JLabel spacer2 = new JLabel(" ");
		buttonPanel.add(spacer2);
		
		karthago = new JLabel("Karthago: Punkte: " + board.getGraph().getScore(Owner.Cathargo));
		buttonPanel.add(karthago);
		
		JLabel spacer3 = new JLabel(" ");
		buttonPanel.add(spacer3);
		
		lblGameOver = new JLabel("Game Over");
		buttonPanel.add(lblGameOver);
		lblGameOver.setVisible(false);
		
		
		this.setSize(1000,600);
		this.setVisible(true);
		this.setAlwaysOnTop(true);

	}

}
