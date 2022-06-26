package HashTablePack;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class HashTable {
	private ArrayList<HashTableCell> mainTable;
	private int a;
	private int b;
	private int c;
	final private int p=999_983;
	private int size;
	private boolean canChange;
	private int minTableSize;
	
	private int numberOfkeys;
	
	public HashTable(int size) {
		this.mainTable = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			this.mainTable.add(null);
		}
		this.canChange = true;
	//	this.p = firstNextPrimeNumber(size);
		this.size = size;
		this.numberOfkeys=this.minTableSize = 0;
		setParametersForHashFunction(size);
		//System.out.println("AB = "+this.a+" "+this.b);
	}
	
	public void insert(RationalNumber key) {
		if (this.canChange) {
			int index =hashCode(key, this.a, this.b,this.c,  this.size);
			if (mainTable.get(index) == null) {
				mainTable.set(index, new DynamiñTable());
			}
			if(((DynamiñTable) mainTable.get(index)).insert(key)) {
				this.numberOfkeys++;
			}
			if (((DynamiñTable) mainTable.get(index)).getSize() > this.minTableSize) {
				this.minTableSize++;
			}
		}
	}
	
	public boolean find(RationalNumber key) {
		int index = hashCode(key, this.a, this.b,this.c,  this.size);
		if (this.canChange) {
			if (((DynamiñTable) mainTable.get(index)).secondTable.indexOf(key)!=-1) {
				return true;
			}
			return false;
		}else {
			
			StaticTable current=((StaticTable) mainTable.get(index));
			if (current!=null  ) {
				return current.find(key);
			}
			return false;
		}
	}
	
	public void print() {
		for (int i = 0; i < mainTable.size(); i++) {
			if (mainTable.get(i) == null) {
				System.out.println("_");
			} else {
				System.out.println(mainTable.get(i).toString());
			}
		}
	}

	public void makeStatic() {
		if (!canChange) {
			System.out.println("It is already static");
			return;
		}
//		while (this.minTableSize>((this.numberOfkeys/this.size)+1)) {
//			this.changeTableByNewAB();
//		}
		//this.print();
		this.canChange = false;
		for (int i = 0; i < mainTable.size(); i++) {
			if (mainTable.get(i) != null ){//&& ((DynamiñTable)mainTable.get(i)).getSize()>1) {
				mainTable.set(i, new StaticTable(((DynamiñTable) mainTable.get(i)).secondTable));
			}
		}
	}

	private void changeTableByNewAB() {// return max
		setParametersForHashFunction(this.p);
		ArrayList<HashTableCell> newMainTable = new ArrayList<>(this.size);
		for (int i = 0; i < this.size; i++) {
			newMainTable.add(null);
		}
		int newMinTableSize = 0;
		for (int i = 0; i < this.mainTable.size(); i++) {
			if (((DynamiñTable) this.mainTable.get(i)) != null) {
				for (RationalNumber copyKey : ((DynamiñTable) this.mainTable.get(i)).secondTable) {
					int index = hashCode(copyKey, this.a, this.b,this.c,  this.size);
					if (newMainTable.get(index) == null) {
						newMainTable.set(index, new DynamiñTable());
					}
					((DynamiñTable) newMainTable.get(index)).insert(copyKey);
					if (((DynamiñTable) newMainTable.get(index)).getSize() > newMinTableSize) {
						newMinTableSize++;
					}
				}
			}

		}
		this.mainTable = newMainTable;
		this.minTableSize = newMinTableSize;
	}

	private void setParametersForHashFunction(int p) {
		this.a = ((int) (Math.random() * (this.p - 1))) + 1;
		this.c = ((int) (Math.random() * (this.p - 1))) + 1;
		this.b = (int) (Math.random() * this.p);
	}

	public int hashCode(RationalNumber key, int a, int b, int c, int size) {
		
		if (key instanceof RationalNumber) {
			if (size==0 || size==0) {
				return 0;
			}
			//System.out.println(size);
		
			return (Math.abs(((a * (key.numerator+ (c* key.denominator))) + b  )% p) % size);
			
		} else {
			System.out.println("incorrect key");
			return -1;
		}
	}
	
	private abstract class HashTableCell {

	}

	private class DynamiñTable extends HashTableCell {
		LinkedList<RationalNumber> secondTable;

		public DynamiñTable() {
			// System.out.println("DynamicTable");
			this.secondTable = new LinkedList<>();

		}

		boolean insert(RationalNumber key) {
			if (secondTable.indexOf(key) == -1) {
				secondTable.add(key);
				return true;
			} else {
				System.out.println("HashTable contains this key");
				return false;
			}
		}
		
		public int getSize() {
			return this.secondTable.size();
		}
		
		@Override
		public String toString() {
			return secondTable.toString();
		}

	}

	private class StaticTable extends HashTableCell {
		ArrayList<RationalNumber> secondTable;
		private int a;
		private int b;
		private int c;
		private int size;

		public StaticTable() {
			a = b = size = 0;
		}
		
		public StaticTable(LinkedList<RationalNumber> oldTable) {
			this.a=1;this.b=0;
			StaticTable.this.size = (oldTable.size() * oldTable.size());
			this.secondTable = new ArrayList<>(StaticTable.this.size);

			for (int i = 0; i < StaticTable.this.size; i++) {
				StaticTable.this.secondTable.add(null);
			}
			// System.out.println("MMM"+oldTable.toString()+" "+oldTable.size()+"
			// "+StaticTable.this.size);

			if (this.size <= 1) {
				this.a = this.b = 0;
			} else {
				boolean isNotCorrect=true;
				while (isNotCorrect) {
					
					StaticTable.this.changeHashFunctionInner();
					boolean[] array = new boolean[StaticTable.this.size];
					int i = 0;
					isNotCorrect=false;
					for (RationalNumber copyKey : oldTable) {
						int index = HashTable.this.hashCode(copyKey,StaticTable.this.a,StaticTable.this.b,StaticTable.this.c,  StaticTable.this.size);
						
						if (array[index]) {
							isNotCorrect=true;
							break;
						}
						array[index] = true;
						
					}
				}
				 
			}
			for (RationalNumber copyKey : oldTable) {
				int index = HashTable.this.hashCode(copyKey,StaticTable.this.a,StaticTable.this.b, StaticTable.this.c, StaticTable.this.size);
				// System.out.print(index+" ");
				StaticTable.this.secondTable.set(index, copyKey);
			}

		}
		
		private void changeHashFunctionInner() {
			

			StaticTable.this.a = ((int) (Math.random() * (p - 1))) + 1;
			StaticTable.this.c = ((int) (Math.random() * (p - 1))) + 1;
			StaticTable.this.b = (int) (Math.random() * p);
		}
		
		public boolean find(RationalNumber key) {//HashTable.this.hashCode(secondTable, a, b, size);
			if (this.secondTable.get(HashTable.this.hashCode(key,StaticTable.this.a,StaticTable.this.b, StaticTable.this.c, StaticTable.this.size))!=null && this.secondTable.get(HashTable.this.hashCode(key,StaticTable.this.a,StaticTable.this.b, StaticTable.this.c,StaticTable.this.size )).equals(key) ) {
				return true;
			}
			return false;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			if (this.secondTable == null) {
				return "_";
			}
			StringBuilder result = new StringBuilder("");
			int i=0;
			for (RationalNumber key : this.secondTable) {
				//if (key != null) {
					result.append(i++ +" = "+key + ", ");
				//}
			}
			return result.toString();
		}
	}

	
	
	//
	private int firstNextPrimeNumber(int number) {
		boolean isNotPrime = true;
		while (isNotPrime) {
			isNotPrime = false;
			for (int i = 2; i * i <= number; i++) {
				if (number % i == 0) {
					isNotPrime = true;
					number++;
					break;
				}
			}

		}
		return number;
	}
}
