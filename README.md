# fastCampus_Android_part1
35개 프로젝트로 배우는 Android App개발 강의 part1

#### CH02
 숫자 세기 앱
  - 2개의 Button과 1개의 TextView 존재 (초기화 버튼 : 0으로 초기화, + 버튼 -> 1씩 증가)<br></br>
    추가사항</br>
    ※ ViewModel을 활용하여 화면이 회전해도 숫자 유지<br></br>
    ※ layout 가중치를 활용하여 Portrait에서 LandScape로 바뀌어도 화면이 깨지지 않고 구성되도록 구현<br></br>
    **xml 방식의 Layout 구성 시 weight나 height에 0dp를 넣어 가중치 값 이용하여 남은 공간이 없도록 하기 때문에 가급적 0dp를 쓰는 것이 좋다**

#### CH03
단위 변환 앱 (CM <-> M>
 - 1개의 EditText, 3개의 TextView, 1개의 ImageView 존재</br>
   추가사항</br>
   ※ BigDecimal 자료형으로 바꿔 소숫점 문제와 Int 범위 초과하는 문제를 해결<br></br>
   ** onSaveInstanaceState, onRestoreInstanceState 등 샘명주기 관련 내용이 핵심

#### CH04
 응급 의료정보 앱
  - 2개의 Activity로 구성 (사용자 정보표시 + 정보 입력 : 이름, 생년월일, 혈액형, 전화번호, 주의사항)</br>
  추가사항</br>
  ※ 초기화를 ActionBar의 메뉴에 넣어 진행</br>
  ※ ToolBar와 최상단 Bar의 색상 변경
     
