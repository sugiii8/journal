package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class csvRead {
	public static void main(String[] args) {

		final int COL_TITLE = 8;
		final int COL_PRICE = 9;
		final int COL_PRICE_TAX = 10;
		BufferedReader br = null;			
//		BufferedWriter bw = null;
		PrintWriter pw = null;
		
		try {

			FileInputStream in = new FileInputStream("in/test.csv");
			InputStreamReader sr = new InputStreamReader(in, "Shift_JIS"); // yayaoi format

			br = new BufferedReader(sr);			
//			bw = new BufferedWriter(new FileWriter("out/result.csv"));
			pw = new PrintWriter(new BufferedWriter(new FileWriter("out/result.csv")));
//			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out/result.csv"),"Shift_JIS")));

			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
				int idx = 0; 
				System.out.println(line);
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					idx++;		
					if (idx > 1) {
						pw.write(",");
					}
					if (idx == COL_TITLE) {
						st.nextToken();						
						pw.write("\"課対仕入内8%\"");	
					} else if (idx == COL_PRICE) {
						// calc tax-inclusive
						String token = st.nextToken();
						int price = Integer.parseInt(token);
						result = calcTaxInclusive(price, 1.08);
						pw.write(token);						
					} else if (idx == COL_PRICE_TAX) {
						// set tax-inclusive
						st.nextToken();
						pw.write(String.valueOf(result));
						result = ""; // initialize result
					} else {
						pw.write(st.nextToken());						
					}
				}
				// new line
				pw.println();
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
				pw.close();			
			} catch (Exception e) {}
		}
	}
	
	// calc tax-inclusize
	private static String calcTaxInclusive(int price, double rate) {
		double result;
		
		double ex = price/rate;
		result = price - ex;
		result = Math.floor(result);
		
		String resultStr = String.valueOf(result);
		resultStr = resultStr.substring(0, resultStr.length() - 2);
		System.out.println(resultStr);
		return resultStr;
		
	}

}
