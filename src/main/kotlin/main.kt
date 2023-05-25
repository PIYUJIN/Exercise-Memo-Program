import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

fun main(){
    var ex = Exercise()
//    println(ex.now)
    ex.running()
}

class Exercise {

    val scan = Scanner(System.`in`)
    var inputNumber = 0
    var now = LocalDate.now()
    var memoList = ArrayList<Memo>()
    var dateList = mutableSetOf<LocalDate>()

    // 초기 상태를 동물 정보를 입력받는 상태로 설정한다.
    var programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER

    fun running() {
        while (true) {
            // 프로그램 상태에 따른 분기
            when (programState) {
                ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER -> {
                    inputMenuNumber()
                    if(inputNumber==3) {
                        break
                    }
                }

                ProgramState.PROGRAM_STATE_INPUT_EXERCISE_INFO -> {
                    inputExerciseInfo()
                    programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                }

                ProgramState.PROGRAM_STATE_PRINT_EXERCISE_INFO -> {
                    selectDate()
                    programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                }
            }
        }

    }

    fun inputMenuNumber() {
        while (true) {
            try {
                println("메뉴를 선택해주세요.")
                println("1. 오늘의 운동 기록")
                println("2. 날짜별 운동 기록 보기")
                println("3. 종료")
                print("번호 입력 : ")
                inputNumber = scan.nextInt()
            } catch (e: Exception) {
                println("잘못 입력하였습니다. 다시 입력해주세요.")
            }

            if (inputNumber !in 1..3) {
                println("잘못된 번호입니다. 1,2,3번의 번호 중 입력해주세요.")
                continue
            }

            if (inputNumber == 1) {
                programState = ProgramState.PROGRAM_STATE_INPUT_EXERCISE_INFO
                break
            } else if (inputNumber == 2) {
                programState = ProgramState.PROGRAM_STATE_PRINT_EXERCISE_INFO
                break
            } else if (inputNumber == 3) {
                break
            }
            else {
                break
            }
        }
    }

    fun inputExerciseInfo() {
        var type = " "
        var num = 0L
        var setNum = 0L
        try {
            print("운동 종류 : ")
            type = scan.next()
            print("횟수 : ")
            num = scan.nextLong()
            print("세트 : ")
            setNum = scan.nextLong()
        } catch (e: java.lang.Exception) {
            println("잘못된 정보입니다.")
        }
        memoList.add(Memo(now, type, num, setNum))
        dateList.add(now)
    }

    fun printExerciseInfo() {
        for (i in memoList) {
            println()
            println("운동 종류 : ${i.type}")
            println("횟수 : ${i.num}")
            println("세트 : ${i.set}")
        }
    }

    fun selectDate() {
        var sortedDateList = dateList.sorted()
        var n = 1
        for (i in sortedDateList)
            println("$n. $i")

            println()
            println("날짜를 선택해주세요. (0.이전) : ")
            var inputNum = scan.nextInt()
            if (inputNum == 0) {
                programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
            }
            if (inputNum > dateList.size) {
                println("해당 번호가 없습니다. 다시 입력해주세요.")
            }

            for (i in memoList) {
                var date = i.date
                if(date==dateList.elementAt(inputNum-1)) {
                    println()
                    println("운동 종류 : ${i.type}")
                    println("횟수 : ${i.num}")
                    println("세트 : ${i.set}")
                }
            }
            programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
    }

}

data class Memo (var date: LocalDate, var type:String, var num:Long, var set:Long) {

}

enum class ProgramState {
    PROGRAM_STATE_INPUT_MENU_NUMBER, PROGRAM_STATE_INPUT_EXERCISE_INFO, PROGRAM_STATE_PRINT_EXERCISE_INFO
}


