package spider.util.collaborative;

public class StdoutObject {
	
	public static String matrixToString(int[][] matrixValues){
		StringBuffer sb = new StringBuffer("\n");
		for (int i = 0;i < matrixValues.length;i++){
			sb.append("[");
			for (int j = 0;j < matrixValues[i].length;j++ ){
				if (j == matrixValues[i].length - 1){
					sb.append(matrixValues[i][j]+"]");
					continue;
				}
				sb.append(matrixValues[i][j]+",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public static String matrixToString(float[][] matrixValues){
		StringBuffer sb = new StringBuffer("\n");
		for (int i = 0;i < matrixValues.length;i++){
			sb.append("[");
			for (int j = 0;j < matrixValues[i].length;j++ ){
				if (j == matrixValues[i].length - 1){
					sb.append(matrixValues[i][j]+"]");
					continue;
				}
				sb.append(matrixValues[i][j]+",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
