package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.City;
import core.City_Graph;
import core.Owner;
import core.Path;



public class Board extends JPanel{
	
	static final long serialVersionUID = 1L;
	
	private City_Graph city_graph;
	private JFrame jframe;
	private int boardOffset;
	private int citySquareSize;
	private Map<Integer, JButton> buttonMap;
	private boolean waitForTurn = false;
	private int clickedId = -1;
	private boolean doneTurn = false;
	
	
	private Color neutralColor;
	private Color romColor;
	private Color cathargoColor;
	
	private int buttonKlicked;

	
	
	public Board(City_Graph city_graph){
		
		this.city_graph = city_graph;
			
		
		// set Variables
		boardOffset = 200;
		citySquareSize = 40;
		neutralColor = new Color(190,190,190);
		romColor = new Color(210, 20, 20);
		cathargoColor = new Color(20, 20, 210);
		
		
		// init jframe
		jframe = new JFrame("test");
		jframe.setSize(city_graph.getBiggestX() + boardOffset,
				city_graph.getBiggestY() + boardOffset);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		buttonMap = new HashMap<Integer, JButton>();
		
		
		setLayout(null);
		
		
		setSize(jframe.getWidth(), jframe.getHeight());

		
		jframe.add(this);
		jframe.setVisible(false);
		repaint();
	
	}
	

	
	/**
	 * replaces the old cityGraph with the new one
	 * and redraws the scene
	 * @param new_city_graph the new city Graph 
	 */
	public void setNewGraph(City_Graph new_city_graph){
		this.city_graph = new_city_graph;
		
		//repaint();
	}
	
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		
		Iterator<Path> pathIterator = city_graph.getPathIterator();
	
		while(pathIterator.hasNext()){
			Path tmpPathRef = pathIterator.next();
			g.drawLine(tmpPathRef.getFirstCity().getXLocation(),
					   tmpPathRef.getFirstCity().getYLocation(), 
					   tmpPathRef.getSecondCity().getXLocation(),
					   tmpPathRef.getSecondCity().getYLocation());
		}
		
		
		/* JBUTTON */
		for(City c : city_graph){
			
			JButton jbutton;
			
			// math
			int cityMidX = c.getXLocation();
			int cityMidY = c.getYLocation();
			int squareHalf = (citySquareSize/2);
			int cityX = cityMidX - squareHalf;
			int cityY = cityMidY - squareHalf;
			Owner owner = c.getOwner();
					
			// draws the text and the rect
			String drawString = Integer.toString(c.getID());
			
			if(owner == Owner.Cathargo){
				drawString += " C";
			}else if(owner == Owner.Rom){
				drawString += " R";
			}
			
			g.setColor(getCityColor(owner));
			g.fillRect(cityX, cityY, citySquareSize, citySquareSize);
			
			
			g.setColor(new Color(0,0,0));
			g.drawString(drawString, cityMidX-(3*drawString.length()), cityMidY+5);
			
			
			// adds button
			jbutton = buttonFactory(cityX, cityY, c.getID());
			buttonMap.put(c.getID(), jbutton);
			
			add(jbutton);
			
		}
		
	}
	
	private JButton buttonFactory(int x, int y, final int id){
		
		JButton result = new JButton();
		result.setBounds(x, y, citySquareSize, citySquareSize);
		
		result.setContentAreaFilled(false);
		result.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(waitForTurn){
					waitForTurn = false;
					doneTurn = true;
					clickedId = id;
					System.out.println(id);
					//Board.this.notify();
				}
				
			}
		});
		
		return result;		
	}
	
	
	public int getID(){
		System.out.println("test");
		this.waitForTurn = true;
		while(!doneTurn){
			
			try {
				Thread.sleep(200);
				System.out.println("waiting");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		doneTurn = false;
		return this.clickedId;
	}
	
	
	
	public Color getCityColor(Owner owner){
		if(owner == Owner.Rom){
			return romColor;
		}else if(owner == Owner.Cathargo){
			return cathargoColor;
		}
		return neutralColor;
	}
	
	public void setGraph(City_Graph graph){
		city_graph = graph;
	}
	
	public City_Graph getGraph() {
		return city_graph;
	}
}
