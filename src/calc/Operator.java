package calc;

public enum Operator {
		
	//演算子ごとの処理を列挙型にして切り出す事で
	//呼び出し元の文を軽くする
	
	PLUS("+"){
		@Override
		public double calculate(double x,double y) {
			return x + y;
		}
	},
	MINUS("-"){
		@Override
		public double calculate(double x,double y) {
			return x - y;
		}
	},
	TIMES("*"){
		@Override
		public double calculate(double x,double y) {
			return x * y;
		}
	},
	DIVIDE("/"){
		@Override
		public double calculate(double x,double y) {
			if(y == 0) throw new IllegalArgumentException();
			return x / y;
		}
	},
	
	//以下は演算子と正負の符号が連続した場合の想定
	
	PLUS_MINUS("+-"){
		@Override
		public double calculate(double x, double y) {
			return MINUS.calculate(x, y);
		}
	},
	MINUS_MINUS("--"){
		@Override
		public double calculate(double x, double y) {
			return PLUS.calculate(x, y);
		}
	},
	PLUS_PLUS("++"){
		@Override
		public double calculate(double x, double y) {
			return PLUS.calculate(x, y);
		}
	},
	TIMES_MINUS("*-"){
		@Override
		public double calculate(double x, double y) {
			return -1 * TIMES.calculate(x, y);
		}
	},
	TIMES_PLUS("*+"){
		@Override
		public double calculate(double x, double y) {
			return TIMES.calculate(x, y);
		}
	},
	DIVIDE_MINUS("/-"){
		@Override
		public double calculate(double x, double y) {
			return -1 * DIVIDE.calculate(x, y);
		}
	},
	DIVIDE_PLUS("/+"){
		@Override
		public double calculate(double x, double y) {
			return DIVIDE.calculate(x, y);
		}
	};

	public static Operator getOperator(String opeStr){
		//与えられた演算子文字によってこの定数型を返す
		Operator[] operatorList = Operator.values();
		for(Operator operator : operatorList){
			if(operator.toString().equals(opeStr)){
				return operator;
			}
		}
		throw new IllegalArgumentException();
	}

	private final String symbol;
	
	private Operator(String symbol){
		this.symbol = symbol;
	}
	
	@Override
	public String toString(){
		return this.symbol;
	}
	
	public abstract double calculate(double x,double y);
		
}
