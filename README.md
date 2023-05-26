# Exercise-Memo-Program
This is Exercise Memo Program that can record exercise information.

**PROGRAM STATE**
- **PROGRAM_STATE_INPUT_MENU_NUMBER**
  -  실행 method : **_inputMenuNumber()_**
  
          메뉴를 선택해주세요.
          1. 오늘의 운동 기록
          2. 날짜별 운동 기록 보기
          3. 종료
          번호 입력 :
    
  -  다음 상태
      -  1번 선택한 경우 : _**PROGRAM_STATE_INPUT_EXERCISE_INFO**_
      -  2번 선택한 경우 : _**PROGRAM_STATE_PRINT_EXERCISE_INFO**_
      -  3번 선택한 경우 : _**PROGRAM_SATAE_WRITE_FILE**_

- **PROGRAM_STATE_INPUT_EXERCISE_INFO**
  - 실행 method : _**inputExerciseInfo()**_
  
        운동 종류 : 
        횟수 : 
        세트 : 
        
  - 입력받는 정보를 객체 생성 후 리스트에 저장
  - 다음 상태 : _**PROGRAM_STATE_INPUT_MENU_NUMBER**_
- PROGRAM_STATE_PRINT_EXERCISE_INFO
  - 실행 method : _**selectDate()**_
  
        1. 저장된 날짜 리스트
        2. 저장된 날짜 리스트
        
        날짜를 선택해주세요. (0.이전) : 
  
  - 해당 날짜 운동기록 출력     
    
         운동 종류 : 
         횟수 : 
         세트 : 
        
  - 다음 상태 : _**PROGRAM_STATE_INPUT_MENU_NUMBER**_
- PROGRAM_STATE_READ_FILE
  - 초기 상태
  - 실행 method : **_readObjectStream()_**
  - 저장되어 있는 txt file 읽어오기
  - 저장되어 있는 파일이 없는 경우 다음 단계로 이동
  - 다음 상태 : **_PROGRAM_STATE_INPUT_MENU_NUMBER_**
- PROGRAM_SATAE_WRITE_FILE
  - 실행 method : _**saveObjectStream()**_
  - 실행 후 종료
 

## 실행 영상


- menu 1

![화면-기록-2023-05-26-오후-4 42 14_2](https://github.com/PIYUJIN/Exercise-Memo-Program/assets/86800087/497c68ff-47bf-455e-8afe-4b1c9a265cff)

- menu 2

![화면-기록-2023-05-26-오후-4 42 57](https://github.com/PIYUJIN/Exercise-Memo-Program/assets/86800087/4c95861c-eafe-4521-85d1-266c50d941ba)

- menu 3

![화면-기록-2023-05-26-오후-4 43 54](https://github.com/PIYUJIN/Exercise-Memo-Program/assets/86800087/36872fde-5299-49c4-833c-251822d82479)


