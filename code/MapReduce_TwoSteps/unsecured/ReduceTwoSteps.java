import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import java.io.IOException;

import java.math.BigInteger;
import java.util.*;

public class ReduceTwoSteps extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context)
	throws IOException, InterruptedException {
	ArrayList<String> valuesGroupedByM = new ArrayList<String>();
	ArrayList<String> valuesGroupedByN = new ArrayList<String>();
	for (Text val:values)
	    {
		String value = val.toString();
		(value.startsWith("M") ? valuesGroupedByM : valuesGroupedByN).add(value);
	    }

	Iterator<String> iteratorM = valuesGroupedByM.iterator();
	while (iteratorM.hasNext())
	    {
		String[] tokensM = iteratorM.next().split(",");
		BigInteger m_ij = new BigInteger(tokensM[2]);
		Iterator<String> iteratorN = valuesGroupedByN.iterator();
		while (iteratorN.hasNext())
		    {
			String[] tokensN = iteratorN.next().split(",");
			key = new Text(tokensM[1]+","+tokensN[1]);
			BigInteger n_jk = new BigInteger(tokensN[2]);
			context.write(key, new Text(m_ij.multiply(n_jk).toString()));
		    }
	    }
    }
}
