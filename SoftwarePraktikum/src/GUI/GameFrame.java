package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class GameFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public GameFrame(Board board) {
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
		
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 3.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(board, gbc_panel);
		
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
		
		JLabel rom = new JLabel("Rom: Punkte");
		buttonPanel.add(rom);
		
		JLabel spacer2 = new JLabel(" ");
		buttonPanel.add(spacer2);
		
		JLabel karthago = new JLabel("Karthago: Punkte");
		buttonPanel.add(karthago);
		
		JLabel spacer3 = new JLabel(" ");
		buttonPanel.add(spacer3);
		
		JLabel lblGameOver = new JLabel("Game Over");
		buttonPanel.add(lblGameOver);
	}

}
