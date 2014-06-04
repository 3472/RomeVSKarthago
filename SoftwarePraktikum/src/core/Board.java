package core;

import java.awt.Color;
import java.awt.Graphics;
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



public class Board extends JPanel{
	
	static final long serialVersionUID = 1L;
	
	private City_Graph city_graph;
	private JFrame jframe;
	private int boardOffset;
	private int citySquareSize;
	private Map<Integer, JButton> buttonMap;
	
	
	private Color neutralColor;
	private Color romColor;
	private Color cathargoColor;

	
	
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
		jframe.setVisible(true);
		repaint();
	
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
			
			
			// set Button
			jbutton = new JButton();
			jbutton.setBounds(cityX, cityY, citySquareSize, citySquareSize);
			jbutton.setName(c.getID() + "");
			jbutton.setBackground(getCityColor(owner));
			
			// buttons wont get painted
			jbutton.setContentAreaFilled(false);
			
		
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
			buttonMap.put(c.getID(), jbutton);
			
			add(buttonMap.get(c.getID()));
		}
		
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
}
