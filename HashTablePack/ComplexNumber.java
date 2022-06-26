package HashTablePack;

public class ComplexNumber {
	private int a;
	private int b;
	
	public int getA() {
		return this.a;
	}
	public int getB() {
		return this.b;
	}
	public ComplexNumber() {
		this.a=0;
		this.b=0;
	}
	public ComplexNumber(int a, int b) {
		this.a=a;
		this.b=b;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder result=new StringBuilder("");
		if (!(this.a==0 && this.b!=0)) {
			result.append(this.a);
		}
		
		if (b>0) {
			if (result.length()!=0) {
				result.append("+");
			}
			
		}else if(b==0) {
			return result.toString();
		}
		return result.append(this.b).append("i").toString();
	}
	@Override
	public boolean equals(Object obj) {
		
		return ((ComplexNumber)obj).a==this.a && ((ComplexNumber)obj).b==this.b;
	}
	public static ComplexNumber randomComplexNumber(int maxValue) {
		return new ComplexNumber(2*(int)(Math.random()*maxValue-(maxValue*0.5)), 2*(int)((Math.random()*maxValue-(maxValue*0.5))));
	}
}
