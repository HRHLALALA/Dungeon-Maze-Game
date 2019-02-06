package Map;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class RandomMapGenerator {

	private int bound;
	private char[][] map;
	private Random random;
	private int mapQuantities;
	private ArrayList<MyPoint> coords;
	
	public RandomMapGenerator(int mapSize, int mapQuantities) {
		this.bound = mapSize;
		this.setRandom(new Random());
		this.setCoords(new ArrayList<>());
		this.mapQuantities = mapQuantities;
		this.setmap(new char[mapSize][mapSize]);
	}

	public ArrayList<MyPoint> getCoords() {
		return coords;
	}

	public void setCoords(ArrayList<MyPoint> coords) {
		this.coords = coords;
	}
	
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public char[][] getmap() {
		return map;
	}

	public void setmap(char[][] map) {
		this.map = map;
		for(int i = 0; i < this.bound; i++) {
			for(int j = 0; j < this.bound; j++) {
				this.map[i][j] = '#';
			}
		}
	}
	
	// minimum spanning tree algorithm
	public void prim() {
		int x = random.nextInt(bound);
		int y = random.nextInt(bound);
		this.coords.add(new MyPoint(x, y));
		this.map[x][y] = ' ';

		while(true) {
			ArrayList<MyPoint> candidateNodes = new ArrayList<>();
			HashMap<MyPoint, ArrayList<MyPoint>> matchedNeighbours = new HashMap<>();
			for(int i = 0; i < this.coords.size(); i++) {
				MyPoint node = this.coords.get(i);
				ArrayList<MyPoint> neighbours = this.getNeighbours(node);
				ArrayList<MyPoint> candidateNeighbours = new ArrayList<>();
				for(MyPoint adjNode: neighbours) {
					if(isClose(adjNode, node)) {
						continue;
					}
					candidateNeighbours.add(adjNode);
				}
				if(candidateNeighbours.size() == 0)
					continue;
				candidateNodes.add(node);
				matchedNeighbours.put(node, candidateNeighbours);
			}
			if(candidateNodes.size() == 0)
				break;
			
			int m = random.nextInt(candidateNodes.size());
			ArrayList<MyPoint> neighbours = matchedNeighbours.get(candidateNodes.get(m));
			int n = random.nextInt(neighbours.size());
			MyPoint p = neighbours.get(n);
			if(this.coords.contains(p)) continue;
			this.map[p.x][p.y] = ' ';		
			this.coords.add(p);
			this.random.setSeed(System.currentTimeMillis());
		}		
	}
	
	public boolean isClose(Point node, Point prev) {
		for(Point p: this.coords) {
			if(p.equals(prev)) continue;
			if((p.x == node.x && Math.abs(p.y - node.y) < 2) ||
				(p.y == node.y && Math.abs(p.x - node.x) < 2))
				return true;
		}
		return false;
	}
	
	public ArrayList<MyPoint> getNeighbours(MyPoint p) {
		ArrayList<MyPoint> neighbours = new ArrayList<MyPoint>();
		if(p.x > 0) neighbours.add(new MyPoint(p.x-1, p.y));
		if(p.x < this.bound-1) neighbours.add(new MyPoint(p.x+1, p.y));
		if(p.y > 0) neighbours.add(new MyPoint(p.x, p.y-1));
		if(p.y < this.bound-1) neighbours.add(new MyPoint(p.x, p.y+1));
		return neighbours;
	}
	
	public void resetMap() {
		for(int i = 0; i < this.bound; i++) {
			for(int j = 0; j < this.bound; j++) {
				this.map[i][j] = '#';
			}
		}
		this.coords.clear();
	}
	
	public void GenerateMap() {
		for(int i = 0; i < this.mapQuantities; i++) {
			this.random.setSeed(System.currentTimeMillis());
			this.prim();
			File file = new File("src/Map/"+String.valueOf(i)+".txt");
			System.out.println(file);
			this.coords.sort(new Comparator<MyPoint>() {
				@Override
				public int compare(MyPoint arg0, MyPoint arg1) {
					if(arg0.x + arg0.y > arg1.x + arg1.y)
						return 1;
					else if(arg0.x + arg0.y < arg1.x + arg1.y)
						return -1;
					return 0;
				}
				
			});
			this.map[this.coords.get(0).x][this.coords.get(0).y] = 'r';
			this.map[this.coords.get(this.coords.size()-1).x][this.coords.get(this.coords.size()-1).y] = 'e';
			try {
				FileWriter fw = new FileWriter(file);
				System.out.println("<map>");
				fw.write("1"+"\r\n"+"20"+"\r\n");
				for(int a = 0; a < this.bound; a++) {
					for(int b = 0; b < this.bound; b++) {
						System.out.print(this.map[a][b]);
						fw.write(this.map[a][b]);
					}
					System.out.println();
					fw.write("\r\n");
				}
				System.out.println("</map>");
				fw.close();
				this.resetMap();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}
	
	private class MyPoint extends Point {
		
		public MyPoint(int x, int y) {
			super(x, y);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o == null) return false;
			if(this == o) return true;
			if(!(o instanceof MyPoint))
				return false;
			MyPoint p = (MyPoint) o;
			if(p.x != this.x || p.y != this.y)
				return false;
			return true;
		}
		
	}
	
	public static void main(String[] args) {
		RandomMapGenerator g = new RandomMapGenerator(20, 10);
		g.GenerateMap();
	}

}
