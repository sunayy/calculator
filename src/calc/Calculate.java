package calc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate {

	public static final int MODE_PM = 0; //加減を表す定数
	public static final int MODE_TD = 1; //乗除を表す定数
	
	private String expression;
	
	public Calculate(String expression){
		this.expression = expression;
	}
	
	public double evaluateExpression(){
		//セットされた式より解を求める
		String expression = this.expression;
		expression = replaceInnerBrackets(expression);
		return calculate(expression);
	}
	
	public String getExpression(){
		return this.expression;
	}
	
	public static String replaceInnerBrackets(String expression){
		
		//含まれる括弧がネストしている場合、再帰的に外側の括弧から外していく
		//そうして入れ子の一番深い括弧内の式から解き、結果を元のexpressionに当てはめていく
		if(expression.matches(".*\\([^\\)]*\\(.*")){
			String deletedBrackets = deleteOuterBrackets(expression);
			String innerBrackets = replaceInnerBrackets(deletedBrackets);
			expression = expression.replace(deletedBrackets,innerBrackets);
		}

		//以下、括弧内の式を置換していく処理
		String regex = "(\\(.+?\\))";		
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(expression);
		
		//同じ階層の括弧内の式を順次計算していく
		while(matcher.find()){
			String matchString = matcher.group();
			expression = expression.replace(matchString, "" + calculate(matchString.replaceAll("[\\(\\)]", "")));
		}

		//括弧を計算結果の数値に置き換えた文字列を返す
		//最終的には括弧のない計算式が返される
		return expression;
	}

	public static String deleteOuterBrackets(String expression){
		//式に含まれる一番外の括弧をとその外側をブランクに置換する
		return expression.replaceAll("(^[^\\(]*\\()|(\\)[^\\)]*$)", "");
	}
	
	public static double calculate(String expression){
		//乗除 -> 加減の順番で計算し、結果をexpressionに更新していく
		expression = getCalculatedString(expression, MODE_TD);
		expression = getCalculatedString(expression, MODE_PM);
		//最終的な解答
		return Double.parseDouble(expression);
	}
	
	private static String getCalculatedString(String expression,int mode){
		
		//加減モード・乗除モードに当てはまらない場合は例外を投げる
		if(mode != MODE_PM && mode != MODE_TD){
			throw new IllegalArgumentException();
		}
		
		//ここで演算子の場合分けを行う
		// ++ +- -+ -- *+ *- /+ /- + - を取り得るものと想定する（正負の符号が演算子としての記号に連続する場合を考えて）
		String regex = (mode == MODE_PM) ? "(\\+\\+|\\+-|-\\+|--|\\+|-)" : "(\\*\\+|\\*-|/\\+|/-|\\*|/)";
		Pattern pattern = Pattern.compile("(\\+|-|)[0-9]+\\.*[0-9]*" + regex + "[0-9]+\\.*[0-9]*");
		Matcher matcher = pattern.matcher(expression);

		//計算の余地がある限り処理を行う
		//実行イメージ
		//  3 + 6 + 8 + 5
		//= 9 + 8 + 5
		//= 17 + 5
		//= 22
		while(matcher.find()){
			//先頭に存在する符号があれば格納しておく
			//なければ空文字を格納し、後続の処理結果への影響を無くす
			//式を一組検出する
			String matchString = matcher.group();
			String splitRegex = "(?<!^)" + regex;
			//式を演算子で分割して値を取り出し、それぞれを配列に
			String[] strValues = matchString.split(splitRegex);
			
			// strValues[X] `operator` strValues[Y]で計算を行う
			double[] values = {
					Double.parseDouble(strValues[0]) 
				   ,Double.parseDouble(strValues[1])
			};
			final double X = values[0];
			final double Y = values[1];
			//次のreplaceAllに備えて、+の符号が入ってた場合のシーケンスを考慮する
			strValues[0] = strValues[0].replaceAll("\\+", "\\\\+");
			//数字と小数点をすべて取り除き演算子を抽出
			String operator = matchString.replaceAll("^" + strValues[0],"")
					 					 .replaceAll(strValues[1], "");
			//演算
			double ans = OperatorType.init(operator).calc(X, Y);
			//得た値を元の式に置き換えて次回へ
			expression = expression.replace(matchString, "" + ans);
			//新しい式に正規表現を適用する
			matcher = pattern.matcher(expression);
		}
		//それぞれのモードで計算し尽くした結果を返す
		return expression;
	}
}
