import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

import java.math.BigInteger;

public class MapTwoSteps extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
	// Variables de configuration
	Configuration conf = context.getConfiguration();
	int m = Integer.parseInt(conf.get("m"));
	int p = Integer.parseInt(conf.get("p"));
	// Variables gerant les vecteurs contenues dans le fichier en entree
	String line = value.toString(); 
	String[] indicesAndValue = line.split(","); // (M, i, j, Mij) par exemple
	Text outputKey = new Text();
	Text outputValue = new Text();
	BigInteger bm1 = new BigInteger(indicesAndValue[3]);
	if (indicesAndValue[0].equals("M")) {
	    outputKey.set(indicesAndValue[2]); // la cle en sortie est le j
	    outputValue.set(indicesAndValue[0] + "," + indicesAndValue[1] + "," + bm1.toString());
	    context.write(outputKey, outputValue);
	} else {
	    outputKey.set(indicesAndValue[1]); // la cle en sortie est le j
	    outputValue.set(indicesAndValue[0] + "," + indicesAndValue[2] + "," + bm1.toString());
	    context.write(outputKey, outputValue);
	}
    }
}   
