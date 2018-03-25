import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import java.util.*;

public class MatrixMultiply {
    public static void main(String[] args) throws Exception {
	if (args.length != 3) {
	    System.err.println("Usage: MatrixMultiply <in_dir> <out_dir> <size_mat>");
	    System.exit(3);
	}
	long startTime = System.currentTimeMillis();
	Configuration conf = new Configuration();
	// M is an m-by-n matrix; N is an n-by-p matrix.
	conf.set("m", args[2]);
	conf.set("n", args[2]);
	conf.set("p", args[2]);
	@SuppressWarnings("deprecation")
	    Job job = new Job(conf, "MatrixMultiply");
	job.setJarByClass(MatrixMultiply.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);

	job.setMapperClass(Map.class);
	job.setReducerClass(Reduce.class);

	job.setInputFormatClass(TextInputFormat.class);
	job.setOutputFormatClass(TextOutputFormat.class);

	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));

	job.waitForCompletion(true);
	long endTime = System.currentTimeMillis();
	System.out.println((endTime - startTime)*0.001);
    }
}
