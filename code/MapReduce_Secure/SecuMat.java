import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.System.*;


public class SecuMat{
	public static void main(String[] str) {
		try{
			BufferedReader br  = new BufferedReader(new FileReader(str[0]));
			String line;
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
			
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	
		
	}


}
