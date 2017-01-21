package datamining_a3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ReadFile {
	
	/*public static void main(String[] args) throws FileNotFoundException
	{
		file_output fo = read(3);
		
		int[][] data = fo.data;
		
		for(int i=0;i<data.length;i++)
		{
			for(int j=0;j<data[0].length;j++)
			{
				System.out.print(data[i][j]+" ");
			}
			System.out.println();
		}
		int row = fo.map_node_key.get("1194");
		int col = fo.map_node_key.get("124");
	//	System.out.println(row+" "+col);
		System.out.println(data[row][col]+" "+data[col][row]);
		
		
	}*/
	
	public static file_output read(int x) throws FileNotFoundException
	{
		
		int a =0,b=0;
		String path="";
		if(x==1)
		{
			path ="C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/attweb_net.txt";
			a = 180;b=180;
		}
		else if(x==2)
		{
			path = "C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/physics_collaboration_net.txt";
			a =142;b=142;
		}
		else if(x==3)
		{
			path = "C:/Users/Srikanth/Desktop/data mining/a3/Data_For_HW3/yeast_undirected_metabolic.txt";
			a = 359;b= 359;
		}
		else
		{
			path = "C:/Users/Srikanth/Desktop/data mining/project3/cho.txt";
			a = 386;b=18;
		}
		File file=new File(path);
		Scanner scan=new Scanner(file);
		int[][] data = new int[a][b];
        
		HashMap<Integer,String> map_index_key = new HashMap<Integer,String>();
		HashMap<String,Integer> map_node_key = new LinkedHashMap<String,Integer>();
		HashSet<String> set_nodes = new HashSet<String>();
       
        
        int index=0;
        
        String token="", token1="",token2="";
        int count=0;
        while(scan.hasNextLine())
        {
        	StringTokenizer st = new StringTokenizer(scan.nextLine());
        	
        	while(st.hasMoreTokens()) 
        	{
        		token = "";
        		if(count==0)
        		{
        			token1 = st.nextToken();
        			
        			token = token1;
        		}
        		else if(count==1)
        		{
        			token2 = st.nextToken();
        			
        			token =token2;
        		}
        		
        		if(count==0)
        			count=1;
        		else
        			count=0;
        		
        		if(!map_node_key.containsKey(token))
        		{
        			map_node_key.put(token, index);
        			map_index_key.put(index, token);
        			set_nodes.add(token);
        			index++;
        			
        		}
        	        	
        		
        	}
        	
        	int row = map_node_key.get(token1);
        	int col = map_node_key.get(token2);
        //	System.out.println(token1+"..."+row+" "+token2+"..."+col );
        	data[row][col] = data[col][row] = 1;  

        	
        	
        }
        
        
     //   System.out.println(map_index_key);
     //   System.out.println(map_node_key.size());
        
        file_output fo = new file_output();
        fo.data = data;
        fo.map_index_key.putAll(map_index_key);
        fo.map_node_key.putAll(map_node_key);
    //    System.out.println(fo.map_node_key);
    //    System.out.println(fo.map_index_key);
        
        return fo;
	}


}
