

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class Graph {
	static Vertex[] vertices;
	DijkstraSP dsp;
	int numOfNodes;
	int numOfEdges;
	boolean isEWD=true;
	//createGraph Function - Reading the data in the text file, And creating a full graph  
	public  void createGraph(File f)
	{
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			// Taking the final number of the vertices.
			numOfNodes=Integer.parseInt(br.readLine());

			// Taking the final number of the edges.
			numOfEdges=Integer.parseInt(br.readLine());

			//Initial the size and the cells of the vertices array.
			vertices = new Vertex[numOfNodes];
			for (int i = 0; i < vertices.length; i++) {
				vertices[i] = new Vertex(""+i);
				vertices[i].adjacencies=new Vector<Edge>();
			}

			//Checking if the incoming data graph file is directed or undirected.
			if (f.getName().contains("EWG")){
				//Continue creating undirected graph.
				isEWD=false;

			}
			creator(br);
			br.close();
			dsp=new DijkstraSP(vertices);
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

	private void creator(BufferedReader br) {
		String line;
		try {
			line = br.readLine();
			while(line!=null){
				//Taking the origin and destination vertices, And the cost between them.
				StringTokenizer st= new StringTokenizer(line, " ");
				int from=Integer.parseInt(st.nextToken());
				int to=Integer.parseInt(st.nextToken());
				float cost=Float.parseFloat(st.nextToken());
				//Check if the nodes number we read are fine
				if(to<0||from<0){
					System.out.println("Node can't be bellow 0");
				}
				else if(to>=numOfNodes||from>=numOfNodes){
					System.out.println("Too high node number");
				}
				//Check if the cost is bellow 0
				else if(cost<0){
					System.out.println("Edge cost can't be bellow 0");
				}
				else{
					//Adding the edge between the origin vertex to the destination vertex.
					vertices[from].adjacencies.addElement(new Edge(vertices[to], cost));
					//Checking if the graph is undirected
					if(!isEWD){
						//Adding the edge between the destination vertex to the origin vertex (Undirected graph)
						vertices[to].adjacencies.addElement(new Edge(vertices[from], cost));
					}
				}
				line=br.readLine();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//return **********************************************
	public DijkstraSP getDsp() {
		return dsp;
	}


}
