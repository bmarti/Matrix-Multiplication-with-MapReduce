import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

import java.math.BigInteger;
import java.util.HashMap;

public class Reduce
 extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       @Override
       public void reduce(Text key, Iterable<Text> values, Context context)
                       throws IOException, InterruptedException {
               String[] value;
               //key=(i,k),
               //Values = [(M/N,j,V/W),..]
               HashMap<Integer, BigInteger> hashA = new HashMap<Integer, BigInteger>();
               HashMap<Integer, BigInteger> hashB = new HashMap<Integer, BigInteger>();
               for (Text val : values) {
                       value = val.toString().split(",");
		       BigInteger e_val = new BigInteger(value[2]);
                       if (value[0].equals("M")) {
			       hashA.put(Integer.parseInt(value[1]),e_val);
                       } else {
                              
			       hashB.put(Integer.parseInt(value[1]),e_val);
                       }
               }
               int n = Integer.parseInt(context.getConfiguration().get("n"));
               BigInteger result = BigInteger.ZERO;
               BigInteger m_ij;
               BigInteger n_jk;
               for (int j = 0; j < n; j++) {
                       m_ij = hashA.containsKey(j) ? hashA.get(j) : BigInteger.ZERO;
                       n_jk = hashB.containsKey(j) ? hashB.get(j) : BigInteger.ZERO;
                       result=result.add(m_ij.multiply(n_jk));
               }
               if (result != BigInteger.ZERO) {
                       context.write(null,
		       new Text(key.toString() + "," + result.toString()));
               }
       }
}
