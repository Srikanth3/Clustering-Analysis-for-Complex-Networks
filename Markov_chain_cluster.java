package datamining_a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Markov_chain_cluster {
	
	public static void main(String[] args) throws IOException
	{
		
		ReadFile rf = new ReadFile();
		file_output fo = new file_output();
		
		int file_no = 1;
		fo = rf.read(file_no);
		int[][] data = fo.data;
		
		data = self_loops(data);
		int e=0,p=0;
		if(file_no==1)
		{
			e=30;
			p=30;
		}
		else if(file_no==2)
		{
			e=100;
			p=95;
		}
		else 
		{
			e = 140;
			p = 140;
		}
		
		double[][] n_data = normalize(data);
		int count=0;
		while(true)
		{
			double[][] prev_data = n_data;
			n_data = e_power(n_data,e);
			n_data = inflate(n_data,p);
			n_data = normalize(n_data);
			count++;
			
			int check_count=0;
			for(int i=0;i<n_data.length;i++)
			{
				if(Arrays.equals(prev_data[i], n_data[i]))
					check_count++;
				
			}
					
			if(check_count==n_data.length)
				break;
		}
		System.out.println("Matrix converged after "+count+" iterations"); 
		
		String cluster="";
		ArrayList<String> al = new ArrayList<String>();
		int c_count=0;
		
		ArrayList<ArrayList<String>> al2 = new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<n_data.length;i++)
		{
			ArrayList<String> al1 = new ArrayList<String>();
			
			for(int j=0;j<n_data[0].length;j++)
			{
				if(n_data[i][j]!=0)
				{
					cluster+= fo.map_index_key.get(j)+" ";
					al1.add(fo.map_index_key.get(j));
					c_count++;
				}
			}
			if(cluster.length()!=0)
			{
				al.add(cluster);
				al2.add(al1);
			}
			cluster="";
		}
	//	System.out.println("c count:"+c_count);
	
		write_to_file2(al2,file_no,fo.map_node_key);
	
	}
	
	public static void write_to_file2(ArrayList<ArrayList<String>> al2,int x,HashMap<String,Integer> node_map) throws IOException
	{
	
		try
		{
			String path1 ="C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/attweb_net_output.clu";
		
			String path2 ="C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/physics_collaboration_net_output.clu";
			
			String path3 ="C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/yeast_undirected_metaboli_outputc.clu";

			
			String path="";
			if(x==1)
				path = path1;
			else if(x==2)
				path = path2;
			else
				path = path3;
			
			File file = new File(path);
			 
			if(file.exists())
			  	  file.delete();
			  
			if (!file.exists()) 
				  file.createNewFile();
              
			 
			  
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
             
                    
            HashMap<String,Integer> h = new HashMap<String,Integer>();
            int al_count=1;
            
            for(ArrayList<String> al : al2)
            {
            	for(String s : al)
            		if(!h.containsKey(s))		 
        			 	 h.put(s,al_count);
        			 
            	if(!al.isEmpty())
            		al_count=al_count+1;;
          }
        
        file_output fo = new file_output();
      
            if(x==1)
               	bw.write("*vertices 180");
            
            else if(x==2)
            	bw.write("*vertices 142");
            
            else 
            	bw.write("*vertices 359");
            
            bw.newLine();
            
            for(Entry<String,Integer> entry : node_map.entrySet() )
            {
            	String key = entry.getKey();
            	Integer value = h.get(key); 
            	bw.write(""+value);
            	bw.newLine();
            }
    	
			  bw.close();
		}
		catch(Exception e)
		{
              System.out.println(e);
		}
	}
	

	
	static double[][] inflate(double[][] n_data,int x)
	{
		for(int i=0;i<n_data.length;i++)
		{
			for(int j=0;j<n_data[0].length;j++)
			{
				n_data[i][j] = Math.pow(n_data[i][j], x);
			}
		}
		
		return n_data;
	}
	
	static double[][] e_power(double[][] data,int x)
	{
		double[][] prod = new double[data.length][data.length];
		if(x<2)
			return data;
		else if(x==2)
			prod =	multiply(data,data);
		else if(x>2)
		{
			prod =	multiply(data,data);
			int count =2;
			while(count<x)
			{
				prod = multiply(data,prod);
				count++;
			}
		}
		
		return prod;
	}
	
	static double[][] multiply(double[][] data, double[][] prod)
	{
		int n = data.length;
		double[][] prod2 = new double[data.length][data[0].length];
		
		
		  for (int i = 0; i < n; i++)
	        {
	            for (int j = 0; j < n; j++)
	            {
	                for (int k = 0; k < n; k++)
	                {
	                    prod2[i][j] = prod2[i][j] + data[i][k] * prod[k][j];
	                }
	            }
	        }
		  
		  return prod2;
	}
	
	static void print(double[][] data)
	{
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[0].length;j++)
			{
				System.out.print(data[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	static void print(int[][] data)
	{
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[0].length;j++)
			{
				System.out.print(data[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	static int[][] self_loops(int[][] data)
	{
		int i=0;
		while(i<data.length)
			data[i][i++]=1;
		
		return data;
	}
	
	static double[][] self_loops(double[][] data)
	{
		int i=0;
		while(i<data.length)
			data[i][i++]=1;
		
		return data;
	}
	
	static double[][] normalize_by_row(double[][] data)
	{
		double[][] n_data = new double[data.length][data[0].length];
		
		for(int i=0;i<data.length;i++)
		{
			int sum=0;
			for(int j=0;j<data.length;j++)
				sum+=data[i][j];
			
			for(int j=0;j<data.length;j++)
				n_data[i][j] = data[i][j]/sum;
		}
		return n_data;
	}
	
	static double[][] normalize(double[][] data)
	{
		double[][] n_data = new double[data.length][data[0].length];
		for(int j=0;j<data[0].length;j++)
		{ 
			double sum=0;
			
			for(int i=0;i<data.length;i++)
				sum+=data[i][j];
						
			for(int i=0;i<data.length;i++)
				n_data[i][j]= (double) data[i][j]/sum;
		}
		return n_data;
	}
	
	static double[][] normalize(int[][] data)
	{
		double[][] n_data = new double[data.length][data[0].length];
		for(int j=0;j<data[0].length;j++)
		{ 
			int sum=0;
						
			for(int i=0;i<data.length;i++)
				sum+=data[i][j];
				
			for(int i=0;i<data.length;i++)
				n_data[i][j]= (double) data[i][j]/sum;
		}
		return n_data;
	}

}

