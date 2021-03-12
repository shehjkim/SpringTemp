package com.company.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class StreamTest {
	public static void main(String[] args) throws Exception {

//하나씩? 출력
		/*
		 * FileInputStream fr = new FileInputStream("D:\\test\\sample.txt"); int c; while ( (c =
		 * fr.read()) != -1 ) { // System.out.println(c); //유니코드값
		 * System.out.println((char)c); } fr.close();
		 */

//buffer 이용
			//fileRead()
			//fileBufferRead()			
			//파일 읽기, 파일 복사
			/*
			 * BufferedInputStream br = new BufferedInputStream(new
			 * FileInputStream("D:\\test\\sample.txt")); BufferedOutputStream bw = new
			 * BufferedOutputStream(new FileOutputStream("D:\\test\\sample2.txt"));
			 * //writer-파일복사 String line; while (true) { line = br.readLine(); if (line ==
			 * null) break; // System.out.println(line); bw.write(line + "\n"); //파일복사 \n :
			 * enter
			 * 
			 * } br.close(); bw.close(); }
			 */
		
		//input, output으로 파일 복사
		BufferedInputStream br = new BufferedInputStream(new FileInputStream("D:\\test\\cat.jpg"));
		BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("D:\\test\\cat2.jpg")); //writer-파일복사
		int cnt;
		byte[] b = new byte[100];
			while (true) {
				cnt = br.read(b);
				if (cnt == -1 )
					break;
//				System.out.println(line);
				bw.write(b);			//파일복사 \n : enter
				
			}
			br.close();
			bw.close();
		}
}
