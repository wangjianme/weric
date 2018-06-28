package com.topsen.financial.util.support.format;
import  java.text.DecimalFormat;

/**Сдת��д
 * @author lily200825 2011-5-16
 * <li>���֧��<font color='red'>Ǫ����</font>(16λ����,2λС��)</li>
 */
public class RMB {
	//�����־
	public static volatile boolean isDebug = false;
	
	//��ֵ {Ǫ���� ������ Ǫ���ۣ�ʰ��Ԫ}
	public static final String[] unit = { "Ǫ","��","ʰ","��"
											,"Ǫ","��","ʰ","��"   
											,"Ǫ","��","ʰ","��" 
											,"Ǫ" ,"��","ʰ","Ԫ" };
	// ���ֱ�ʾ
	public static final char[] digit = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��','��' };   
	
	/**
	 * Сд��� ת ��д���
 * <li>���֧��<font color='red'>������</font>(15λ������2λС��) ����<font color='red'>Ǫ����</font>(16λ����)</li>
	 * <li>�������֧��<font color='red'>Ǫ����</font>(16λ����)��ֻ�����д����</li>
	 * <li>��������Խ����С���������Խ����ȷ</li>
	 * <li>����������Ը���</li>
	 * @param amount
	 * @return ��д���
	 */
	public static String number2UpperCase(double  _amount){
		double  amount = Math.abs(_amount);
		StringBuffer result = new StringBuffer();
		/*
		 * -------- �������� --------
		 */
		StringBuffer result_intStr = new StringBuffer();
		long num = (long)amount;
		// ת�ɴ�д
		String intStr = getUpperCase(num);
		int len = intStr.length();
		
		String regex_01 = "(��.)+";
		String regex_00 = "(��)+";
		String rep_str = "��";
		char zero = '��';
		
		if(len<=16){
			// ====== Ǫ���� ======
			int index = 0;
			for(int i=16-len ; i<16; i++){
				result_intStr.append(intStr.charAt(index++));
				//��Ȩֵ
				result_intStr.append(unit[i]);
			}
			//��Ȩ��δ�ϲ�������
			String temp_intStr = result_intStr.toString();
			int temp_len = temp_intStr.length();
			if(isDebug){
				System.out.println("��Ȩ��δ���������㣬["+temp_intStr+"]��");
			}
			
			result_intStr = new StringBuffer();
			if(len>=13){
				//���� ,�ϲ������� ��X -> 
				String str = temp_intStr.substring(0, temp_len-12*2).replaceAll(regex_01, rep_str);
				result_intStr.append( str.charAt(str.length()-1) == zero ? str.substring(0, str.length()-1)+'��' : str);
			}
			if(len>=9){
				//�� ,�ϲ������� ��X
				int start = temp_len-12*2 < 0 ? 0 : temp_len-12*2 ;
				String str = temp_intStr.substring(start , temp_len-8*2).replaceAll(regex_01, rep_str);
				result_intStr.append( str.charAt(str.length()-1) == zero ? str.substring(0, str.length()-1)+'��' : str);
			}
			if(len>=5){
				//�� ,�ϲ������� ��X
				int start = temp_len-8*2 < 0 ? 0 : temp_len-8*2 ;
				String str = temp_intStr.substring(start , temp_len-4*2).replaceAll(regex_01, rep_str);
				result_intStr.append( str.charAt(str.length()-1) == zero ? str.substring(0, str.length()-1)+'��' : str);
			}
			if(len>=1){
				//Ԫ ,�ϲ������� ��X
				int start = temp_len-4*2 < 0 ? 0 : temp_len-4*2 ;
				String str = temp_intStr.substring(start , temp_len).replaceAll(regex_01, rep_str);
				result_intStr.append( str.charAt(str.length()-1) == zero ? str.substring(0, str.length()-1)+'Ԫ' : str);
			}
			//�����
			String last_intStr = result_intStr.toString().replaceAll(regex_00, rep_str);
			if(last_intStr.length()==1){
				//Ԫ
				last_intStr = zero+last_intStr;
				result_intStr = new StringBuffer(last_intStr);
			}else{
				//XX�����ǪԪ -> XX�����ǪԪ
				int w_index = last_intStr.lastIndexOf('��');
				
				if(len>=9 && w_index>0 && last_intStr.charAt(w_index-1)=='��'){
					last_intStr = last_intStr.substring(0, w_index) + zero + last_intStr.substring(w_index+1);
					result_intStr = new StringBuffer(last_intStr);
				}
			}
		}else{
			//======= ���� Ǫ���� ======= 
			if(isDebug){
				System.out.println("���� Ǫ���ڡ�����ֻ�����д����");
			}
			result_intStr.append(intStr);
			result_intStr.append("Ԫ");
		}
		//������
		result.append(result_intStr.toString().replaceAll(regex_00, String.valueOf(zero) ));
		result.append(" ");
		if(isDebug){
			System.out.println("���������㣬["+result.toString()+"]��");
		}
		
		/*
		 * -------- С������(������λС��) --------
		 */
		//С������(������λС��) 0.01
		double decimal = Double.parseDouble( new DecimalFormat("0.00").format(amount - (double)num) ) ;
		if(decimal>0d){
			StringBuffer result_floatStr = new StringBuffer();
			String decimalStr = new DecimalFormat("0.00").format(amount - num);
			len = decimalStr.length();
			decimalStr = decimalStr.substring(2, len>5? 4 : len);
			result_floatStr.append(getUpperCase(Long.parseLong(decimalStr.substring(0, 1))));
			result_floatStr.append("��");
			result_floatStr.append(getUpperCase(Long.parseLong(decimalStr.substring(1, 2))));
			result_floatStr.append("��");
			//������
			result.append(result_floatStr);
			if(isDebug){
				System.out.println("����С����["+result_floatStr+"]��");
			}
		}
		return result.toString();
	}
	
	/**
	 * ���ִ�д
	 * @param num 
	 * <li>����ԭ���������</li>
	 * @return
	 */
	public static String getUpperCase(long num){
		return getUpperCase(num,true);
	}
	
	/**
	 * ���ִ�д
	 * @param num 
	 * @param is_original �Ƿ�ԭ��
	 * <li>is_original = true : ����ԭ���������</li>
	 * <li>is_original = false : �������</li>
	 * @return
	 */
	public static String getUpperCase(long num , boolean is_original){
		StringBuffer result = new StringBuffer();
		String temp = null;
		if(is_original){
			temp = String.valueOf(num);
		}else{
			temp = new StringBuffer(String.valueOf(num)).reverse().toString();
		}
		for(int i=0; i<temp.length(); i++){
			char c = temp.charAt(i);
			int index = Integer.parseInt(String.valueOf(c));
			result.append(digit[index]);
		}
		return result.toString();
	}
}
