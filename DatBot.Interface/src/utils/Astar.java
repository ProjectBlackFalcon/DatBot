package utils;

import java.util.*;

import game.Info;
import protocol.network.Network;
import utils.d2p.MapManager;

/*
 * WITHOUT DIAGONAL FOR MAP CHANGE
 */
public class Astar {
	public final int DIAGONAL_COST = 14;
	public final int V_H_COST = 10;

	class Cell {
		int heuristicCost = 0; //Heuristic cost
		int finalCost = 0; //G+H
		int i, j;
		Cell parent;

		Cell(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public String toString() {
			return "[" + this.i + ", " + this.j + "]";
		}
	}

	//Blocked cells are just null Cell values in grid
	Cell[][] grid = new Cell[5][5];

	PriorityQueue<Cell> open;

	boolean closed[][];
	int startI, startJ;
	int endI, endJ;

	public void setBlocked(int i, int j) {
		grid[i][j] = null;
	}

	public void setStartCell(int i, int j) {
		startI = i;
		startJ = j;
	}

	public void setEndCell(int i, int j) {
		endI = i;
		endJ = j;
	}

	void checkAndUpdateCost(Cell current, Cell t, int cost) {
		if (t == null || closed[t.i][t.j]) return;
		int t_final_cost = t.heuristicCost + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.finalCost) {
			t.finalCost = t_final_cost;
			t.parent = current;
			if (!inOpen) open.add(t);
		}
	}

	public void AStarNoDiag() {

		//add the start location to open list.
		open.add(grid[startI][startJ]);

		Cell current;

		while (true) {
			current = open.poll();
			if (current == null) break;
			closed[current.i][current.j] = true;

			if (current.equals(grid[endI][endJ])) { return; }

			Cell t;
			if (current.j - 1 >= 0) {
				t = grid[current.i][current.j - 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.j + 1 < grid[0].length) {
				t = grid[current.i][current.j + 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.i + 1 < grid.length) {
				t = grid[current.i + 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}
		}
	}

	public void AStarDiag() {

		//add the start location to open list.
		open.add(grid[startI][startJ]);

		Cell current;

		while (true) {
			current = open.poll();
			if (current == null) break;
			closed[current.i][current.j] = true;

			if (current.equals(grid[endI][endJ])) { return; }

			Cell t;
			if (current.i - 1 >= 0) {
				t = grid[current.i - 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i - 1][current.j - 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i - 1][current.j + 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}
			}

			if (current.j - 1 >= 0) {
				t = grid[current.i][current.j - 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.j + 1 < grid[0].length) {
				t = grid[current.i][current.j + 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.i + 1 < grid.length) {
				t = grid[current.i + 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i + 1][current.j - 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i + 1][current.j + 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}
			}
		}
	}

	public List<int[]> path;
	public List<String> pathString;

	/*
	 * Params : tCase = test case No. x, y = Board's dimensions si, sj = start
	 * location's x and y coordinates ei, ej = end location's x and y
	 * coordinates int[][] blocked = array containing inaccessible cell
	 * coordinates
	 */
	public void test(int si, int sj, int ei, int ej, int[][] blocked, boolean diag) {
		int x = 40;
		int y = 14;
		//Reset
		grid = new Cell[x][y];
		closed = new boolean[x][y];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Cell c1 = (Cell) o1;
			Cell c2 = (Cell) o2;

			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		});
		//Set start position
		setStartCell(si, sj); //Setting to 0,0 by default. Will be useful for the UI part

		//Set End Location
		setEndCell(ei, ej);

		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				grid[i][j] = new Cell(i, j);
				grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
			}
		}
		grid[si][sj].finalCost = 0;

		/*
		 * Set blocked cells. Simply set the cell values to null for blocked
		 * cells.
		 */
		for (int i = 0; i < blocked.length; ++i) {
			setBlocked(blocked[i][0], blocked[i][1]);
		}

		//Display initial map
		System.out.println("Grid: ");
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				if (i == si && j == sj)
					System.out.print("SO  "); //Source
				else if (i == ei && j == ej)
					System.out.print("DE  "); //Destination
				else if (grid[i][j] != null)
					System.out.printf("%-3d ", 0);
				else
					System.out.print("BL  ");
			}
			System.out.println("");
		}
		System.out.println("");

		if (diag) {
			AStarDiag();
		}
		else {
			AStarNoDiag();
		}
		System.out.println("\nScores for cells: ");
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				if (grid[i][j] != null)
					System.out.printf("%-3d ", grid[i][j].finalCost);
				else
					System.out.print("BL  ");
			}
			System.out.println("");
		}
		System.out.println("COST : " + grid[ei][ej].finalCost);
		System.out.println("");
		ArrayList<int[]> path = null;
		if (closed[endI][endJ]) {
			//Trace back the path 
			System.out.println("Path: ");
			Cell current = grid[endI][endJ];
			System.out.print(current);
			path = new ArrayList<int[]>();
			path.add(new int[] { current.i, current.j });
			while (current.parent != null) {
				System.out.print(" -> " + current.parent + "(" + (current.parent.i * 14 + current.parent.j) + ")");
				current = current.parent;
				path.add(new int[] { current.i, current.j });
			}
			System.out.println("");
			//Display final map
			System.out.println("Final: ");
			for (int i = 0; i < x; ++i) {
				for (int j = 0; j < y; ++j) {
					boolean pathTrue = false;
					for (int[] is : path) {
						if (is[0] == i && is[1] == j) pathTrue = true;
					}
					if (i == si && j == sj)
						System.out.print("SO  "); //Source
					else if (i == ei && j == ej)
						System.out.print("DE  "); //Destination
					else if (pathTrue)
						System.out.printf("%-3d ", 1);
					else if (grid[i][j] != null)
						System.out.printf("%-3d ", 0);
					else
						System.out.print("BL  ");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		else {
			System.out.println("No possible path");
		}
	}

	public void initMap(int si, int sj, int ei, int ej, int[][] blocked, boolean diag) {
		int x = 40;
		int y = 14;
		//Reset
		grid = new Cell[x][y];
		closed = new boolean[x][y];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Cell c1 = (Cell) o1;
			Cell c2 = (Cell) o2;

			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		});
		//Set start position
		setStartCell(si, sj); //Setting to 0,0 by default. Will be useful for the UI part

		//Set End Location
		setEndCell(ei, ej);

		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				grid[i][j] = new Cell(i, j);
				grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
			}
		}

		grid[si][sj].finalCost = 0;

		/*
		 * Set blocked cells. Simply set the cell values to null for blocked
		 * cells.
		 */
		for (int i = 0; i < blocked.length; ++i) {
			setBlocked(blocked[i][0], blocked[i][1]);
		}

		if (diag) {
			AStarDiag();
		}
		else {
			AStarNoDiag();
		}

		if (grid[ei][ej].finalCost == 0) {
			path = null;
			return;
		}

		ArrayList<int[]> pathTemp = new ArrayList<>();
		path = new ArrayList<>();
		if (closed[endI][endJ]) {
			//Trace back the path 
			Cell current = grid[endI][endJ];
			pathTemp.add(new int[] { current.i, current.j });
			while (current.parent != null) {
				current = current.parent;
				pathTemp.add(new int[] { current.i, current.j });
			}
			for (int i = pathTemp.size() - 2; i != 0; i--) {
				path.add(pathTemp.get(i));
			}
			path.add(new int[] { ei, ej });
		}
	}

	public static void main(String[] args) throws Exception {
		new MapManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\maps");
		utils.d2p.map.Map map = MapManager.FromId(154010369);

		List<int[]> blocked = new ArrayList<>();
		for (int i = 0; i < map.getCells().size(); i++) {
			if (!map.getCells().get(i).isMov()) {
				blocked.add(new int[] { i % 14, i / 14 });
			}
		}

		int s = 520;
		int e = 212;

		Astar a = new Astar(s % 14, s / 14, e % 14, e / 14, blocked, true);
		System.out.println("Path : " + a.getPath());
	}

	public Astar(int si, int sj, int ei, int ej, List<int[]> blocked, boolean diag) {
		//Inverse x et y

		int temp1 = si;
		si = sj;
		sj = temp1;
		int temp2 = ei;
		ei = ej;
		ej = temp2;

		for (int k = 0; k < blocked.size(); k++) {
			int tempx = blocked.get(k)[0];
			int tempy = blocked.get(k)[1];
			blocked.set(k, new int[] { tempy, tempx });
		}

		int[][] blockedArray = new int[blocked.size()][2];
		for (int i = 0; i < blockedArray.length; i++) {
			blockedArray[i][0] = blocked.get(i)[0];
			blockedArray[i][1] = blocked.get(i)[1];
		}
		initMap(si, sj, ei, ej, blockedArray, diag);
	}

	public List<int[]> getPath() {
		return path;
	}

	public void setPath(List<int[]> path) {
		this.path = path;
	}
}
