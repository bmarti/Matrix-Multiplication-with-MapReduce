import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.System.*;

public class Cal4{
	public static void main(String[] str) {
		long S = 0;
		Paillier paillier = new Paillier();
		for(int i =0 ; i<10000000;i++){
			Random r1 = new Random();
			Random r2 = new Random();
			int n1  = r1.nextInt(10);
			BigInteger m1 =  BigInteger.valueOf(n1);
			long startTime = System.nanoTime();
			BigInteger em1 = paillier.Encryption(m1);
			long endTime =System.nanoTime();
			S = S + (endTime - startTime) ;
	}
		S=S/10000000;
		System.out.println(" Censcryp computed in : "+ S +"ns");
	}


}
