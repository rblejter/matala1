

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class Graph_Algo {
	DijkstraSP dsp;

	//Constractor - Create the graph by reciving a text data file.
	public Graph_Algo(File f) {
		Graph g=new Graph();
		g.createGraph(f);
		dsp =g.getDsp();

	}
	//getShortestPath Function - Return the shortest path between two vertices.
	public String getShortestPath(int a, int b){
		dsp.computePaths(dsp.getVertex(a));
		String ans= dsp.getShortestPathTo(b);
		dsp.unComputePaths();
		return ans;
	}

	//getShortestDistance Function - Return the shortest distance between two vertices.
	public double getShortestDistance(int a, int b){
		dsp.computePaths(dsp.getVertex(a));
		double ans=dsp.getShortestDistanceTo(b);
		dsp.unComputePaths();
		return ans;
	}
	//BLL Function - Return the shortest distance between two vertices without passing forbidden vertices.
	public double BLL(String line){

		//Taking from the incoming string the origin and destination vertices, And the forbidden vertices.
		StringTokenizer st= new StringTokenizer(line, " ");
		int from=Integer.parseInt(st.nextToken());
		int to=Integer.parseInt(st.nextToken());
		int numOfBLNodes=Integer.parseInt(st.nextToken());
		Vector <Integer>B=new Vector<>();
		//Check if the nodes number we read are fine
		if(to<0||from<0){
			System.out.println("Node can't be bellow 0");
		}
		else if(to>=dsp.vertices.length||from>=dsp.vertices.length){
			System.out.println("Too high node number");
		}
		else if(numOfBLNodes<0){
			System.out.println("The number of blacklisted nodes can't be bellow 0");
		}
		else{
			//Making the incoming black vertices to be forbidden to pass.
			for (int i = 0; i < numOfBLNodes; i++) {
				int BL=Integer.parseInt(st.nextToken());
				if(BL<0||BL>dsp.vertices.length){
					System.out.println("Blacklist number is wrong");
				}
				else{
					dsp.vertices[BL].BlackList=true;
					B.add(BL);
				}
			}
			//Getting the shortest path between the origin and destination vertices.
			getShortestPath(from,to);

			//Getting the shortest Distance between the origin and destination vertices.
			double ans=getShortestDistance(from,to);

			//Return back the forbidden vertices to the previous state.
			for (int i = 0; i < numOfBLNodes; i++) {
				int BL=B.get(i);
				dsp.vertices[BL].BlackList=false;
			}
			//Return the shortest distance between the origin and destination vertices.
			return ans;
		}
		return Double.POSITIVE_INFINITY;
		
	}

	//blackList Function - Reading from a data file and calculate the distances accordance the lines.
	public void blackList(File f){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));

			//Taking the Several ways of calculating.
			int numOfBL=Integer.parseInt(br.readLine());

			String line=br.readLine();

			//Passing all the left lines of the file and calculate the distance between the two vertices.
			while(line!=null){
				BLL(line);
				line=br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
