package HashTablePack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		
		HashTable table = new HashTable(20);

		for (int i = 0; i < 20; i++) {
			table.insert(new RationalNumber());
		}
	
		table.makeStatic();
		System.out.println();
		table.print();


	}

	

}
