import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Object;

public class SecuMatCluster {
	public static void main (String[] args) throws Exception {
		// nom complet du fichier
		Path nomcomplet = new Path("/home/ubuntu/secure-mapreduce-f4/code/MapReduce_Secure/M");
		
		// ouvrir le fichier sur HDFS
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		FSDataInputStream inStream = fs.open(nomcomplet);
		try {
			// Preparer un lecteur
			InputStreamReader isr = new InputStreamReader(inStream);
			BufferedReder br = new BuffererdReader(isr);
			
			String line = br.readLine();
			Paillier paillier = new Paillier();
			Vector<Integer> vec = new Vector<Integer>();
			while ((line = br.readLine()) != null)
			{
				String[] indicesAndValue = line.toString().split(",");
				int val =  Integer.parseInt(indicesAndValue[3]);
				vec.add(val);
			}
			
			long startTime = System.nanoTime();
			for (int i =0; i < vec.size(); i++)
			{
				BigInteger m1 =  BigInteger.valueOf(vec.get(i));
				BigInteger em1 = paillier.Encryption(m1);
				System.out.println(" :" + em1.toString() );
			}
			long endTime =System.nanoTime();
			System.out.println(" computed in : "+ (endTime - startTime) +"ns");
			
			
		} finally{
			inStream.close();
			fs.close();
		}
	}
}
