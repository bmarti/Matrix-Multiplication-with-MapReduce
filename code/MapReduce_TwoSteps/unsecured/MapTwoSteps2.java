import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

import java.math.BigInteger;
import java.util.*;

public class MapTwoSteps2 extends org.apache.hadoop.mapreduce.Mapper<Text, Text, Text, Text> {
    @Override
    public void map(Text key, Text value, Context context)
	throws IOException, InterruptedException {
	String test = String.valueOf(key);
	context.write(new Text(test), value);
    }
}
