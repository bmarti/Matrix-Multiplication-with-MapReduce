import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import java.io.IOException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;
//import java.util.HashMap;

public class ReduceTwoSteps2 extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context)
	throws IOException, InterruptedException {
	BigInteger result = BigInteger.ONE;
	//key=(i,k),
	//Values = [(M,j,V, W)]
	Paillier paillier = new Paillier();
	for (Text val : values) {
		
	    String[] value =  val.toString().split(",");
	    BigInteger c_ij = new BigInteger(value[0]); // emij^(njk+tjk)
	    BigInteger em_ij = new BigInteger(value[1]);
	    BigInteger et_jk = new BigInteger(value[2]);
	    BigInteger t_jk = paillier.Decryption(et_jk);
	    int expo = t_jk.intValue();
	    BigInteger expo_emijtjk = em_ij.pow(expo);
	    BigInteger d_ij = c_ij.divide( expo_emijtjk).mod(paillier.nsquare); // emij^(njk+tjk)/emij^tjk 
	    result=result.multiply(c_ij).divide( expo_emijtjk ).mod(paillier.nsquare) ;
	}
	if (result != BigInteger.ZERO) {
	    context.write(key, new Text(result.toString() + "," + paillier.Decryption(result).toString() ) );
	} 
    }
}
