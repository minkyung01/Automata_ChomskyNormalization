/* 2076298 이민경 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CykMain {
	private static int ruleCnt = 0; //ruleCnt는 production rule의 개수 / 0으로 초기화
	private static HashMap<String, ArrayList<String>> ruleMap = new HashMap<String, ArrayList<String>>(); //ruleMap은 production rule을 저장하기 위한 hashmap
	private static String w; //w는 production rule에 의해 accept되는 지, reject되는 지 확인하고자 하는 문자열

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("규칙의 개수를 입력하세요>> "); //chomsky 정규형으로 변환한 production rule의 개수 입력(화살표 대신 > 사용해야 하고, | 사용하지 않기)
		ruleCnt = sc.nextInt(); //sc를 통해 입력받은 정수를 ruleCnt에 대입
		sc.nextLine(); //입력한 enter를 처리해주기 위한 코드
		System.out.println(ruleCnt + "개의 규칙을 입력하세요"); //ruleCnt만큼의 규칙을 입력하라고 사용자에게 알려주기 위한 코드
		
		for(int i=0; i<ruleCnt;i++) { //ruleCnt만큼 for문 반복
			String lnstr = sc.nextLine(); //sc를 통해 입력받은 production rule을 lnstr에 대입
			String[] str = lnstr.split(">"); //입력받은 lnstr을 > 기호를 기준으로 분리해 str 배열에 대입
			if(str.length == 2) { //>를 기준으로 잘 분리했을 경우 실행되는 코드 / >를 기준으로 앞과 뒤로 분리했기 때문에 str의 length는 2
				String key = str[1]; //>를 기준으로 뒤에 위치한 문자열을 key에 대입. 예) S>AB를 lnstr로 입력했을 경우, AB가 key에 대입됨 
				ArrayList<String> lst = ruleMap.get(key.trim()); //key를 trim한 것을 ruleMap에서 가져와 lst에 대입
				if(lst == null) { //lst가 null인 경우 실행되는 코드
					lst = new ArrayList<String>(); //새로운 arraylist lst 생성
					lst.add(str[0].trim()); //lnstr을 >를 기준으로 분리했을 때, 앞에 위치한 문자열을 trim하여 lst에 추가
					ruleMap.put(key, lst); //ruleMap에 (key, lst)를 put
				}
				else 
					lst.add(str[0].trim()); //lst가 null이 아닌 경우 실행되는 코드 / lnstr을 >를 기준으로 분리했을 때, 앞에 위치한 문자열을 trim하여 lst에 추가
			}
			else {	//>를 기준으로 분리했을 때, str의 length가 2가 아닌 경우에는 오류가 발생할 수 있음. 이 오류를 처리하기 위한 코드가 필요함.
				System.out.println("올바른 형식의 규칙을 입력하세요"); //다시 올바른 규칙을 입력할 수 있도록 안내하는 코드
				i--; //잘못된 규칙을 입력했을 때, i를 감소시킴으로써 올바른 규칙을 ruleCnt개 입력받을 수 있도록 하는 코드
			}
		}
		HashMap<Integer, ArrayList<String>> parseVarMap = new HashMap<Integer, ArrayList<String>>(); //parseVarMap은 w를 parsing하는 데 사용하는 hashmap
		System.out.println("\n멤버 확인이 필요한 문자열을 입력하세요"); //production rule에 의해 accept되는 지, reject되는 지를 판단해야 하는 문자열을 사용자로부터 입력 받기위한 코드
		w = sc.nextLine().trim(); //sc를 통해 입력받은 문자열의 공백 문자를 정리해 w에 대입
		int wlen = w.length(); //w의 길이를 wlen에 대입
			
		for(int len=1; len<=wlen; len++) { //wlen만큼 for문 반복
			for(int i=1, j=len; j<=wlen; i++, j++) { //i는 문자열의 시작 인덱스, j는 문자열의 끝 인덱스
				Integer newkey = makeKey(i, j, wlen); //i, j, wlen을 이용해 생성한 정수 값을 newkey에 대입
				ArrayList<String> newVars = new ArrayList<String>(); //arraylist newVars 생성
	            //V(ij)를 이용해 key를 찾아내는 코드
				if(len == 1) { //즉 V(ij)의 ij가 11 22 33 44 ...인 경우
					String findkey = w.substring(i-1, i); //w의 일부를 심볼로 추출해 findkey에 대입
					ArrayList<String> ruleVar = ruleMap.get(findkey); //findkey를 심볼로 갖고 있는 변수를 찾아 ruleVar에 대입
					if(ruleVar != null && !ruleVar.isEmpty()) { //ruleVar이 null이 아니고 ruleVar이 비어있지 않을 경우 실행되는 코드
						for(String var : ruleVar) {
							newVars.add(var); //ruleVar에 존재하는 변수들을 모두 newVars에 대입
						}
						parseVarMap.put(newkey, newVars); //parseVarMap에 (newkey, newVars) 대입
					}
					continue;
				}
				else { //len이 1이 아닐 경우 실행되는 코드
					for(int k=i; k<j; k++) {
						Integer key1 = makeKey(i, k, wlen); //i, k, wlen을 이용해 고유한 정수 값을 생성한 후 key1에 대입
						Integer key2 = makeKey(k+1, j, wlen); //(k+1), j, wlen을 이용해 고유한 정수 값을 생성한 후 key2에 대입 / V(ij)가 다양하게 만들어질 수 있기 때문에 key를 2개 사용. 예) V(13)의 경우, V(11)V(23) 조합도 가능하고 V(12)V(23) 조합도 가능함
						//V(i, k)V(k+1, j)를 이용해 key를 찾아내는 코드
						for(String var1 : parseVarMap.get(key1)) { 
							for(String var2 : parseVarMap.get(key2)) {
								String findkey = var1+var2; //var1과 var2의 값을 더해 findkey에 대입
								ArrayList<String> ruleVar = ruleMap.get(findkey); //findkey를 심볼로 갖고 있는 변수를 찾아 ruleVar에 대입
								if(ruleVar != null && !ruleVar.isEmpty()) { //ruleVar이 null이 아니고 ruleVar이 비어있지 않을 경우 실행되는 코드
									for(String var : ruleVar) {
										if(!newVars.contains(var)) newVars.add(var); //newVars가 var을 포함하고 있지 않은 경우, newVars에 var 대입
									}
									parseVarMap.put(newkey, newVars); //parseVarMap에 (newkey, newVars) 대입
								}
							}
						}
						parseVarMap.put(newkey, newVars); //parseVarMap에 (newkey, newVars) 대입
					}
				}
			}
		}
		//최종 key인 lastkey에 S가 있는지를 확인하기 위한 코드 (start variable인 S에서 시작해 w를 형성할 수 있어야 accept되기 때문)
		Integer lastkey = makeKey(1, wlen, wlen); //1, wlen, wlen을 이용해 만든 정수 값을 lastkey에 대입
		boolean bAccept = false; //bAccpet를 false로 초기화
		for(String var : parseVarMap.get(lastkey)) {
			if(var.contains("S")) { //var이 start variable인 S를 갖고 있는 경우 실행되는 코드
				bAccept = true; //bAccept의 boolean 값을 true로 변경
				break;
			}
		}
		if(bAccept) System.out.println("Accept"); //bAccept가 true일 경우(var이 S를 포함하고 있는 경우) 실행되는 코드
		else System.out.println("Reject"); //bAccept가 false일 경우(var이 S를 포함하고 있지 않은 경우) 실행되는 코드
	}
	//인덱스 i, j를 이용해 key를 만들어내는 메소드 makeKey
	private static int makeKey(int i, int j, int len) {
		int key = (i*len) + j; //key를 비교하기 쉽도록 배열마다 고유한 정수 값을 형성해 key에 대입 
		return key;
	}
}

/* 테스트 데이터 실행 결과
테스트 데이터 1
aabbb: accept
bbbb: reject
aaaaa: reject
테스트 데이터 2
baaba: accept
ababaabaaa: accept
ababaabaab: reject
*/