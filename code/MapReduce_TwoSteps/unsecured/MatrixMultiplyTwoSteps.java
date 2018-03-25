import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat; // permet d'enchainer les jobs en reconnaissant les types tout de suite
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MatrixMultiplyTwoSteps {

    public static void main(String[] args) throws Exception {

	if (args.length != 3) {
	    System.err.println("Usage: MatrixMultiply <in_dir> <out_dir> <size_mat> ");
	    System.exit(1);
	}
	Configuration conf = new Configuration();
	// M is an m-by-n matrix; N is an n-by-p matrix.
	long startTime = System.currentTimeMillis();
	conf.set("m", args[2]);
	conf.set("n", args[2]);
	conf.set("p", args[2]);
	Path out = new Path(args[1]);
	@SuppressWarnings("deprecation")
		
	    // Configuration du premier round
	    Job job1 = new Job(conf, "matrix multiplication two steps first round");
	job1.setJarByClass(MatrixMultiplyTwoSteps.class);
	job1.setMapperClass(MapTwoSteps.class);
	job1.setNumReduceTasks(1); //uncomment for a map-only job 0: map-only, 1: map & reduce

	// Ajout pour empecher l'erreur ifile.readahead
	job1.getConfiguration().set("mapreduce.ifile.readahead", "false");

	// Combine les cles identiques avant d'etre traitees par le reduce
	//job1.setCombinerClass(ReduceTwoSteps12.class);
	job1.setReducerClass(ReduceTwoSteps.class);

	//job1.setMapOutputKeyClass(Text.class);
	//job1.setMapOutputValueClass(Text.class);
	
	job1.setOutputKeyClass(Text.class); // set the type expected to the output of Map AND Reduce
	job1.setOutputValueClass(Text.class);
	job1.setInputFormatClass(TextInputFormat.class);
	job1.setOutputFormatClass(TextOutputFormat.class);
	//job1.setOutputFormatClass(SequenceFileOutputFormat.class); erreur de lisibilité en sortie du map1 corrigé

	FileInputFormat.addInputPath(job1, new Path(args[0]));
	FileOutputFormat.setOutputPath(job1, new Path(out, "outJob1"));

	// fin du premier round
	if (!job1.waitForCompletion(true)) {
	    System.exit(1);
	}

	// // Configuration du deuxieme round
	Job job2 = Job.getInstance(conf, "matrix multiplication two steps second round");
	job2.setJarByClass(MatrixMultiplyTwoSteps.class);

	// Ajout pour empecher l'erreur ifile.readahead
	job2.getConfiguration().set("mapreduce.ifile.readahead", "false");

	job2.setNumReduceTasks(1); // fait un Map ET un Reduce --- à 0 = Map only !
	job2.setMapperClass(MapTwoSteps2.class);
	job2.setReducerClass(ReduceTwoSteps2.class);
	
	// job2.setSortComparatorClass(LongWritable.DecreasingComparator.class);
	job2.setMapOutputKeyClass(Text.class);
	job2.setMapOutputValueClass(Text.class);

	job2.setOutputKeyClass(Text.class);
	job2.setOutputValueClass(Text.class);
	job2.setInputFormatClass(KeyValueTextInputFormat.class);
	job2.setOutputFormatClass(TextOutputFormat.class);
	//job2.setInputFormatClass(SequenceFileInputFormat.class);

	FileInputFormat.addInputPath(job2, new Path(out, "outJob1"));
	FileOutputFormat.setOutputPath(job2, new Path(out, "out2"));
	// fin du second round
	if (!job2.waitForCompletion(true)) {
	    System.exit(1);
	}
	long endTime = System.currentTimeMillis();
	System.out.println((endTime - startTime)*0.001);
    }
}
