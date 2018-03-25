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
	BigInteger result = BigInteger.ZERO;
	//key=(i,k),
	//Values = [(M/N,j,V/W),..]
	
	for (Text val : values) {
	    String value = val.toString();
	    BigInteger e_val = new BigInteger(value);
	    result=result.add(e_val);
	}
	if (result != BigInteger.ZERO) {
	    context.write(key, new Text(result.toString()));
	} 
    }
}
