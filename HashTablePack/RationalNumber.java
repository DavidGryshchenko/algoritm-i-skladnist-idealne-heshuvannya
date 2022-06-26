package HashTablePack;

public class RationalNumber implements Comparable<RationalNumber>{
	
	 int numerator;
	 int denominator;

	public RationalNumber() {
		numerator=(int)(Math.random()*25);
		denominator=(int)(Math.random()*25)+1;
	}
	
	public RationalNumber(int numerator, int denominator) {
		this.numerator=numerator;
		this.denominator=denominator;
		isCorrect();
	}
	public RationalNumber(String strRationalNumber) {
		String[] numAndDen=strRationalNumber.trim().split("/");
		if (numAndDen.length==0 || numAndDen.length>2 ) {
			new Exception("incorrect string input");
		}
		
		this.numerator=Integer.parseInt(numAndDen[0].trim());
		if (numAndDen.length==2) {
			this.denominator=Integer.parseInt(numAndDen[1].trim());
		}else {
			this.denominator=1;
		}
		isCorrect();
	}
	private void isCorrect() throws ArithmeticException{
		if (denominator==0) {
			new ArithmeticException();
		}
		if (numerator==0) {
			return;
		}
		for (int i = 2; i*i < Math.min(this.denominator, this.numerator); i++) {
			while (this.numerator%i==0 && this.denominator%i==0) {
				this.numerator/=i;
				this.denominator/=i;
			}
		}
		
		
		if (numerator%denominator==0) {
			numerator/=denominator;
			denominator=1;
		}else if(denominator%numerator==0){
			denominator/=numerator;
			numerator=1;
		}
		if (denominator<0) {
			numerator*=-1;
			denominator*=-1;
		}
	}
	 
	@Override
	public String toString() {
		if(numerator==0) {
			return "0";
		}
		if (denominator==1) {
			return numerator+"";
		}
		return numerator+"/"+denominator;
	}


	@Override
	public int compareTo(RationalNumber other) {
		return this.numerator*(other).denominator-this.denominator*(other).numerator;

	}
	@Override
	public boolean equals(Object obj) {
		
		return ((RationalNumber)obj).numerator==this.numerator && ((RationalNumber)obj).denominator==this.denominator;
	}
}
