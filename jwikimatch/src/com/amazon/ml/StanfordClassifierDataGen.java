package com.amazon.ml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class StanfordClassifierDataGen {


	public static String justSpace(String s) {
		return s.replaceAll("\\s+", " ");
	}
	public static String CaseToStanfordDataStr(Case c) {
		String sep = "\t";
		return c.classification + sep + justSpace(c.term) +
				sep + justSpace(c.bookContext) + sep + justSpace(c.wikiSummary);
	}
	
	public static void writeDataFile(int lineCnt, String fname, MongoCasesIt it) throws IOException {
  	File file = new File(fname);

		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < lineCnt; i++) {
			Case c = it.next();
			String line = CaseToStanfordDataStr(c);
//			System.out.println(line);
				bw.write(line + "\n");
		}	
	}
	
		public static void writeDataFileBalanced(int lineCnt, String fname, MongoCasesIt it) throws IOException {
  	File file = new File(fname);

  	int zerocases = lineCnt / 2;
  	int onecases = lineCnt - zerocases;
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		while (zerocases > 0 || onecases > 0) {
			Case c = it.next();
			if (c.classification == 1) {
				if (onecases == 0) continue;
				onecases--;
			}
			if (c.classification == 0) {
  			if (zerocases == 0) continue;
	  		zerocases--;
			}
			String line = CaseToStanfordDataStr(c);
  		bw.write(line + "\n");
		}	
	
		bw.close();
	}
	
	public static void main(String[] args) throws IOException {
	
//		System.out.println(justSpace("wwtaf   watawt\t\t awgeagw\tawawgawgaw  \tWTF"));
		
	//	if (true) return;
		int trainSize = 2000;
		int testSize = 2000;
		MongoCasesIt it = new MongoCasesIt("train");
		writeDataFileBalanced(2400, "junk", it);
		writeDataFileBalanced(trainSize, "stanfwikimatch-balanced-small.train", it);
//		writeDataFileBalanced(testSize, "stanfwikimatch-balanced-small.test", it);
	}
}
