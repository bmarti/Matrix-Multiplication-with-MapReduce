import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.math.BigInteger;


public class Reduce
 extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       @Override
       
       public void reduce(Text key, Iterable<Text> values, Context context)
                       throws IOException, InterruptedException {
               String[] value;
               Paillier paillier = new Paillier();
               //key=(i,k),
               //Values = [(M/N,j,V/W),..]
               HashMap<Integer, BigInteger> hashA = new HashMap<Integer, BigInteger>();
               HashMap<Integer, BigInteger> hashB = new HashMap<Integer, BigInteger>();
                
               for (Text val : values) {
                       value = val.toString().split(",");
                       BigInteger e_val = new BigInteger(value[2]);                    
                       if (value[0].equals("M")) {
                               hashA.put(Integer.parseInt(value[1]),e_val);
                       }else {
								e_val = paillier.Decryption(e_val);
                               hashB.put(Integer.parseInt(value[1]), e_val);
                              
                       }
               }
               int n = Integer.parseInt(context.getConfiguration().get("n"));
               BigInteger result = BigInteger.ONE;
               BigInteger em_ij;
               BigInteger n_jk;
               BigInteger expo_em1m2;
               for (int j = 0; j < n; j++) {
                       em_ij = hashA.containsKey(j) ? hashA.get(j) : BigInteger.ZERO;
                       n_jk  = hashB.containsKey(j) ? hashB.get(j) : BigInteger.ZERO;
                       expo_em1m2 = em_ij.modPow(n_jk, paillier.nsquare);
                       result=result.multiply(expo_em1m2).mod(paillier.nsquare);
               }
               if (result != BigInteger.ZERO) {
                       context.write(null,
                                       new Text(key + /*"," + result.toString() + */"," + paillier.Decryption(result).toString()));                     
               }
       }
}
