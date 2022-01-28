/* 2076298 �̹ΰ� */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CykMain {
	private static int ruleCnt = 0; //ruleCnt�� production rule�� ���� / 0���� �ʱ�ȭ
	private static HashMap<String, ArrayList<String>> ruleMap = new HashMap<String, ArrayList<String>>(); //ruleMap�� production rule�� �����ϱ� ���� hashmap
	private static String w; //w�� production rule�� ���� accept�Ǵ� ��, reject�Ǵ� �� Ȯ���ϰ��� �ϴ� ���ڿ�

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("��Ģ�� ������ �Է��ϼ���>> "); //chomsky ���������� ��ȯ�� production rule�� ���� �Է�(ȭ��ǥ ��� > ����ؾ� �ϰ�, | ������� �ʱ�)
		ruleCnt = sc.nextInt(); //sc�� ���� �Է¹��� ������ ruleCnt�� ����
		sc.nextLine(); //�Է��� enter�� ó�����ֱ� ���� �ڵ�
		System.out.println(ruleCnt + "���� ��Ģ�� �Է��ϼ���"); //ruleCnt��ŭ�� ��Ģ�� �Է��϶�� ����ڿ��� �˷��ֱ� ���� �ڵ�
		
		for(int i=0; i<ruleCnt;i++) { //ruleCnt��ŭ for�� �ݺ�
			String lnstr = sc.nextLine(); //sc�� ���� �Է¹��� production rule�� lnstr�� ����
			String[] str = lnstr.split(">"); //�Է¹��� lnstr�� > ��ȣ�� �������� �и��� str �迭�� ����
			if(str.length == 2) { //>�� �������� �� �и����� ��� ����Ǵ� �ڵ� / >�� �������� �հ� �ڷ� �и��߱� ������ str�� length�� 2
				String key = str[1]; //>�� �������� �ڿ� ��ġ�� ���ڿ��� key�� ����. ��) S>AB�� lnstr�� �Է����� ���, AB�� key�� ���Ե� 
				ArrayList<String> lst = ruleMap.get(key.trim()); //key�� trim�� ���� ruleMap���� ������ lst�� ����
				if(lst == null) { //lst�� null�� ��� ����Ǵ� �ڵ�
					lst = new ArrayList<String>(); //���ο� arraylist lst ����
					lst.add(str[0].trim()); //lnstr�� >�� �������� �и����� ��, �տ� ��ġ�� ���ڿ��� trim�Ͽ� lst�� �߰�
					ruleMap.put(key, lst); //ruleMap�� (key, lst)�� put
				}
				else 
					lst.add(str[0].trim()); //lst�� null�� �ƴ� ��� ����Ǵ� �ڵ� / lnstr�� >�� �������� �и����� ��, �տ� ��ġ�� ���ڿ��� trim�Ͽ� lst�� �߰�
			}
			else {	//>�� �������� �и����� ��, str�� length�� 2�� �ƴ� ��쿡�� ������ �߻��� �� ����. �� ������ ó���ϱ� ���� �ڵ尡 �ʿ���.
				System.out.println("�ùٸ� ������ ��Ģ�� �Է��ϼ���"); //�ٽ� �ùٸ� ��Ģ�� �Է��� �� �ֵ��� �ȳ��ϴ� �ڵ�
				i--; //�߸��� ��Ģ�� �Է����� ��, i�� ���ҽ�Ŵ���ν� �ùٸ� ��Ģ�� ruleCnt�� �Է¹��� �� �ֵ��� �ϴ� �ڵ�
			}
		}
		HashMap<Integer, ArrayList<String>> parseVarMap = new HashMap<Integer, ArrayList<String>>(); //parseVarMap�� w�� parsing�ϴ� �� ����ϴ� hashmap
		System.out.println("\n��� Ȯ���� �ʿ��� ���ڿ��� �Է��ϼ���"); //production rule�� ���� accept�Ǵ� ��, reject�Ǵ� ���� �Ǵ��ؾ� �ϴ� ���ڿ��� ����ڷκ��� �Է� �ޱ����� �ڵ�
		w = sc.nextLine().trim(); //sc�� ���� �Է¹��� ���ڿ��� ���� ���ڸ� ������ w�� ����
		int wlen = w.length(); //w�� ���̸� wlen�� ����
			
		for(int len=1; len<=wlen; len++) { //wlen��ŭ for�� �ݺ�
			for(int i=1, j=len; j<=wlen; i++, j++) { //i�� ���ڿ��� ���� �ε���, j�� ���ڿ��� �� �ε���
				Integer newkey = makeKey(i, j, wlen); //i, j, wlen�� �̿��� ������ ���� ���� newkey�� ����
				ArrayList<String> newVars = new ArrayList<String>(); //arraylist newVars ����
	            //V(ij)�� �̿��� key�� ã�Ƴ��� �ڵ�
				if(len == 1) { //�� V(ij)�� ij�� 11 22 33 44 ...�� ���
					String findkey = w.substring(i-1, i); //w�� �Ϻθ� �ɺ��� ������ findkey�� ����
					ArrayList<String> ruleVar = ruleMap.get(findkey); //findkey�� �ɺ��� ���� �ִ� ������ ã�� ruleVar�� ����
					if(ruleVar != null && !ruleVar.isEmpty()) { //ruleVar�� null�� �ƴϰ� ruleVar�� ������� ���� ��� ����Ǵ� �ڵ�
						for(String var : ruleVar) {
							newVars.add(var); //ruleVar�� �����ϴ� �������� ��� newVars�� ����
						}
						parseVarMap.put(newkey, newVars); //parseVarMap�� (newkey, newVars) ����
					}
					continue;
				}
				else { //len�� 1�� �ƴ� ��� ����Ǵ� �ڵ�
					for(int k=i; k<j; k++) {
						Integer key1 = makeKey(i, k, wlen); //i, k, wlen�� �̿��� ������ ���� ���� ������ �� key1�� ����
						Integer key2 = makeKey(k+1, j, wlen); //(k+1), j, wlen�� �̿��� ������ ���� ���� ������ �� key2�� ���� / V(ij)�� �پ��ϰ� ������� �� �ֱ� ������ key�� 2�� ���. ��) V(13)�� ���, V(11)V(23) ���յ� �����ϰ� V(12)V(23) ���յ� ������
						//V(i, k)V(k+1, j)�� �̿��� key�� ã�Ƴ��� �ڵ�
						for(String var1 : parseVarMap.get(key1)) { 
							for(String var2 : parseVarMap.get(key2)) {
								String findkey = var1+var2; //var1�� var2�� ���� ���� findkey�� ����
								ArrayList<String> ruleVar = ruleMap.get(findkey); //findkey�� �ɺ��� ���� �ִ� ������ ã�� ruleVar�� ����
								if(ruleVar != null && !ruleVar.isEmpty()) { //ruleVar�� null�� �ƴϰ� ruleVar�� ������� ���� ��� ����Ǵ� �ڵ�
									for(String var : ruleVar) {
										if(!newVars.contains(var)) newVars.add(var); //newVars�� var�� �����ϰ� ���� ���� ���, newVars�� var ����
									}
									parseVarMap.put(newkey, newVars); //parseVarMap�� (newkey, newVars) ����
								}
							}
						}
						parseVarMap.put(newkey, newVars); //parseVarMap�� (newkey, newVars) ����
					}
				}
			}
		}
		//���� key�� lastkey�� S�� �ִ����� Ȯ���ϱ� ���� �ڵ� (start variable�� S���� ������ w�� ������ �� �־�� accept�Ǳ� ����)
		Integer lastkey = makeKey(1, wlen, wlen); //1, wlen, wlen�� �̿��� ���� ���� ���� lastkey�� ����
		boolean bAccept = false; //bAccpet�� false�� �ʱ�ȭ
		for(String var : parseVarMap.get(lastkey)) {
			if(var.contains("S")) { //var�� start variable�� S�� ���� �ִ� ��� ����Ǵ� �ڵ�
				bAccept = true; //bAccept�� boolean ���� true�� ����
				break;
			}
		}
		if(bAccept) System.out.println("Accept"); //bAccept�� true�� ���(var�� S�� �����ϰ� �ִ� ���) ����Ǵ� �ڵ�
		else System.out.println("Reject"); //bAccept�� false�� ���(var�� S�� �����ϰ� ���� ���� ���) ����Ǵ� �ڵ�
	}
	//�ε��� i, j�� �̿��� key�� ������ �޼ҵ� makeKey
	private static int makeKey(int i, int j, int len) {
		int key = (i*len) + j; //key�� ���ϱ� ������ �迭���� ������ ���� ���� ������ key�� ���� 
		return key;
	}
}

/* �׽�Ʈ ������ ���� ���
�׽�Ʈ ������ 1
aabbb: accept
bbbb: reject
aaaaa: reject
�׽�Ʈ ������ 2
baaba: accept
ababaabaaa: accept
ababaabaab: reject
*/