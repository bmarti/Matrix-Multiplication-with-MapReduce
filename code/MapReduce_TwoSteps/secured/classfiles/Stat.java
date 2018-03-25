
import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.System.*;


public class Stat{
	public static double Moyenne (double[] ech){
		double Moy = 0.0;
		for (int i = 0; i<ech.length;i++){
			Moy = Moy +  ech[i];
		}
		double n= ech.length;
		Moy = Moy / n;
		return Moy;
		
	}
	
	public static double Variance (double[] ech, double moyenne)
	{
		double Var = 0.0;
		for (int i = 0; i < ech.length; i++)
		{
			Var = Var + Math.pow((ech[i] - moyenne),2.0);
		}
		Var = Var / ech.length;
		return Var;
	}
	
	public static void main (String[] str) {
		try{
			BufferedReader br  = new BufferedReader(new FileReader(str[0]));
			BufferedWriter writer = new BufferedWriter(new FileWriter("Moyenne.txt", true));
			writer.flush();
			BufferedWriter writerV = new BufferedWriter(new FileWriter("Variance.txt", true));
			writerV.flush();
			BufferedWriter writerE = new BufferedWriter(new FileWriter("Ecart_type.txt", true));
			writerE.flush();
			String line;
			int n = Integer.parseInt(str[1]);
			double[] TabEch = new double[n];
			while ((line = br.readLine()) != null)
			{
				String[] Ech_str = line.toString().split(" ");
				for (int i =0; i < Ech_str.length;i++){   // commence à 1 car 0 contient taille de mat
					Double val =  Double.parseDouble(Ech_str[i]);
					val = val.doubleValue();
					TabEch[i] = val;
				}
				//int taille_mat = Integer.parseInt(Ech_str[0]);
				
				String[] taille = str[0].toString().split(".txt");
				Integer taille_mat = Integer.parseInt(taille[0]);
				double moy = Moyenne(TabEch);
				double variance = Variance(TabEch,moy);
				double ec = Math.sqrt(variance);
				
				writer.append(taille_mat + " " + moy  +"\n");
				writer.flush();
				writerV.append(taille_mat + " " + variance  +"\n");
				writerV.flush();
				writerE.append(taille_mat + " " + ec  +"\n");
				writerE.flush();
			
			
			}
			
			
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	
	}
}
