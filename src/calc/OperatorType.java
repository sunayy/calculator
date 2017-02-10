package calc;

public enum OperatorType implements Calclatable{
	
	PLUS("+"),
	MINUS("-"),
	TIMES("*"),
	DIVIDE("/"),
	PLUS_MINUS("+-"),
	MINUS_MINUS("--"),
	PLUS_PLUS("++"),
	TIMES_MINUS("*-"),
	TIMES_PLUS("*+"),
	DIVIDE_MINUS("/-"),
	DIVIDE_PLUS("/+");
	
	@Override
	public double calc(double x,double y){
		switch(this){
		//足し算
		case PLUS:
		case PLUS_PLUS:
		case MINUS_MINUS : 
			return x + y;
		//引き算
		case MINUS:
		case PLUS_MINUS:
			return x - y;
		//掛け算
		case TIMES:
		case TIMES_PLUS:
			return x * y;
		//割り算
		case DIVIDE:
		case DIVIDE_PLUS:
			if(y == 0) throw new IllegalArgumentException();
			return x / y;
		//演算子と負の符号が連続した場合の計算
		case TIMES_MINUS:
			return -1 * TIMES.calc(x, y);
		case DIVIDE_MINUS:
			return -1 * DIVIDE.calc(x, y);
		//こんなのねぇよ
		default :
			throw new IllegalArgumentException();
		}
	}

	private final String symbol;
	
	private OperatorType(String symbol){
		this.symbol = symbol;
	}
	public static OperatorType init(String opeStr){
		//与えられた演算子文字によってこの定数型を返す
		OperatorType[] operatorList = OperatorType.values();
		for(OperatorType operator : operatorList){
			if(operator.toString().equals(opeStr)){
				return operator;
			}
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public String toString(){
		return this.symbol;
	}
}
