package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class csvRead {
	public static void main(String[] args) {

		final int COL_PRICE = 9;
		final int COL_PRICE_TAX = 10;
		BufferedReader br = null;			
		BufferedWriter bw = null;

		try {

			FileInputStream in = new FileInputStream("in/test.csv");
			InputStreamReader sr = new InputStreamReader(in, "SJIS"); // yayaoi format

			br = new BufferedReader(sr);			
			bw = new BufferedWriter(new FileWriter("out/result.csv"));

			String line = "";
			double result = 0;
			while ((line = br.readLine()) != null) {
				int idx = 0; 
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					idx++;		
					if (idx > 1) {
						bw.write(",");
					}
					if (idx == COL_PRICE) {
						// calc tax-inclusive
						String token = st.nextToken();
						int price = Integer.parseInt(token);
						result = calcTaxInclusive(price, 1.08);
						bw.write(token);						
					} else if (idx == COL_PRICE_TAX) {
						// set tax-inclusive
						st.nextToken();
						bw.write(String.valueOf(result));
						result = 0; // initialize result
					} else {
						bw.write(st.nextToken());						
					}
				}
				// new line
				bw.newLine();
				idx = 0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close file
			try {
				br.close();
				bw.close();			
			} catch (Exception e) {}
		}
	}
	
	// calc tax-inclusize
	private static double calcTaxInclusive(int price, double rate) {
		double result;
		
		double ex = price/rate;
		result = price - ex;
		result = Math.floor(result);
		
		return result;
		
	}

}
