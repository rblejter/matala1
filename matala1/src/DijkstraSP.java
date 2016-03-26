
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;

class Vertex implements Comparable<Vertex>
{
	public final String name;
	public Vector<Edge>  adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public boolean BlackList=false;
	public Vertex(String argName) 
	{
		name = argName; 
	}
	public String toString() 
	{
		return name; 
	}
	public int compareTo(Vertex other)
	{
		return Double.compare(minDistance, other.minDistance);
	}
}

class Edge
{
	public final Vertex vert;
	public final float weight;
	public Edge(Vertex v, float w)
	{ 
		vert = v; 
		weight = w; 
	}
}

public class DijkstraSP
{
	static Vertex[] vertices;
    //DijkstraSP Copy constructor - Receiving arrays of vertices and copy them to the class array.
	public DijkstraSP(Vertex[] vs)
	{
		vertices = new Vertex[vs.length];
				for (int i=0; i<vs.length; i++)
		{
			vertices[i] = vs[i];
		}
	}
	//getVertex Getter Function - return the arrays of the vertices.
	public Vertex getVertex(int index){
		return vertices[index];
	}
		//computePaths Function - Calculate the roads of the incoming vertex. 
	public void computePaths(Vertex s)
	{
		s.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(s);
		while (!vertexQueue.isEmpty()) 
		{
			Vertex u = vertexQueue.poll();
			// Pass over all the edges of the vertex.
			for (Edge e : u.adjacencies)
			{
				Vertex v = e.vert;
				//Checking if the vertex is forbidden to pass.
				if(v.BlackList){
					continue;
				}
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU ;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}
	
	public void unComputePaths(){
		for(int i=0; i<vertices.length; i++){
			vertices[i].minDistance= Double.POSITIVE_INFINITY;
			vertices[i].previous=null;
		}
	}
	//getShortestDistanceTo Function - Getting the most shortest distance between two vertices
	public double getShortestDistanceTo(int target)
	{
		Vertex vertex;
		//Getting the name of the origin vertex.
		for (vertex = vertices[target]; vertex.previous != null; vertex = vertex.previous){}
			
	//	System.out.println("Distance from "+vertex.name+" to " + target + ": " + (float)(vertices[target].minDistance));
		
		// Return the distance.
		return vertices[target].minDistance;
	}
	
	//getShortestPathTo Function - Calculate the most shortest path between two vertices.
	public String getShortestPathTo(int target)
	{
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = vertices[target]; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
	//	System.out.println("Path: " + path);
		//Return the shortest path.
		return("Path: " + path);
	}

	
}
