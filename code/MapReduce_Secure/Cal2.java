import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.System.*;

public class Cal2{
	public static void main(String[] str) {
		long S = 0;
		for(int i =0 ; i<10000000;i++){
			Random r1 = new Random();
			Random r2 = new Random();
			int n1  = r1.nextInt(10);
			int n2 =  r2.nextInt(10);
			BigInteger m1 =  BigInteger.valueOf(n1);
			BigInteger m2 =  BigInteger.valueOf(n2);
			long startTime = System.nanoTime();
			BigInteger add = m1.multiply(m2);
			long endTime =System.nanoTime();
			S = S + (endTime - startTime) ;
	}
		S=S/10000000;
		System.out.println(" Cx computed in : "+ S +"ns");
	}


}
