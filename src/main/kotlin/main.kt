import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.encoding.encodingWith
import kotlin.system.exitProcess
import kotlin.text.Charsets.UTF_8

fun main(){
    val ex = Exercise()
//    println(ex.now)
    ex.running()
}

class Exercise {

    val scan = Scanner(System.`in`)
    var inputNumber = 0
    var now = LocalDate.now()
    var memoList = ArrayList<Memo>()
    var dateList = mutableSetOf<LocalDate>()

    // 초기 상태를 파일 읽어오는 상태로 설정한다.
    var programState = ProgramState.PROGRAM_STATE_READ_FILE

    fun running() {
        while (true) {
            // 프로그램 상태에 따른 분기
            when (programState) {
                // read file data
                ProgramState.PROGRAM_STATE_READ_FILE -> {
                    try {
                        readObjectStream()
                    }
                    catch(e: Exception) {
                        print("")
                    }
                    programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                }

                // input menu number
                ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER -> {
                    try {
                        inputMenuNumber()
                    } catch (e: Exception) {
                        scan.nextLine()
                        println("잘못된 정보입니다. 숫자로 다시 입력해주세요.")
                        continue
                    }
                }

                // menu 1 : 운동 기록
                ProgramState.PROGRAM_STATE_INPUT_EXERCISE_INFO -> {
                    inputExerciseInfo()
                    programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                }

                // menu 2 : 날짜별 운동 기록 출력
                ProgramState.PROGRAM_STATE_PRINT_EXERCISE_INFO -> {
                    selectDate()
                    programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                }

                // 끝날때 운동 기록 file에 저장
                ProgramState.PROGRAM_SATAE_WRITE_FILE -> {
                    saveObjectStream()
                    // 프로그램을 강제 종료시킨다
                    // 0 : 정상 종료를 나타내는 코드
                    exitProcess(0)
//                    break
                }
            }
        }

    }

    // write data
    fun saveObjectStream() {
        val fos = FileOutputStream("exercise.txt")
        val oos = ObjectOutputStream(fos)

        for (i in memoList) {
            oos.writeObject(i)
        }

        oos.flush()
        oos.close()
        fos.close()
    }

    // read data
    fun readObjectStream() {
        val fis = FileInputStream("exercise.txt")
        val ois = ObjectInputStream(fis)
        for (i in 0 until fis.available()) {
            memoList.add(ois.readObject() as Memo)
            for (i in memoList) {
                dateList.add(i.date)
            }
        }


        ois.close()
        fis.close()
    }

    // menu 출력 및 입력 번호 읽어오기
    fun inputMenuNumber() {
        println()
        println("메뉴를 선택해주세요.")
        println("1. 오늘의 운동 기록")
        println("2. 날짜별 운동 기록 보기")
        println("3. 종료")
        print("번호 입력 : ")
        inputNumber = scan.nextInt()

        if (inputNumber !in 1..3) {
            println("잘못된 번호입니다. 1,2,3번의 번호 중 입력해주세요.")
            programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
        }

        // 메뉴 1번을 선택한 경우
        if (inputNumber == 1) {
            programState = ProgramState.PROGRAM_STATE_INPUT_EXERCISE_INFO
        }
        // 메뉴 2번을 선택한 경우
        else if (inputNumber == 2) {
            programState = ProgramState.PROGRAM_STATE_PRINT_EXERCISE_INFO
        }
        // 메뉴 3번을 선택한 경우
        else if (inputNumber == 3) {
            println("프로그램이 종료되었습니다.")
            programState = ProgramState.PROGRAM_SATAE_WRITE_FILE
        }
    }

    // menu 1 : 운동 기록하기
    fun inputExerciseInfo() {
        var type = " "
        var num = 0L
        var setNum = 0L
        var date = " "
        var localDate : LocalDate = now
        try {
            //날짜를 입력받아 사용하는 경우 날짜 입력받기
//            print("날짜 : ")
//            date = scan.next()
//            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
            print("운동 종류 : ")
            type = scan.next()
            print("횟수 : ")
            num = scan.nextLong()
            print("세트 : ")
            setNum = scan.nextLong()

            // 객체 생성 후 리스트에 저장
            // 현재 날짜로 저장
            memoList.add(Memo(now, type, num, setNum))
            dateList.add(now)

            // 날짜 입력받은 후 해당 날짜로 저장
//            memoList.add(Memo(localDate, type, num, setNum))
//            dateList.add(localDate)
        } catch (e: java.lang.Exception) {
            scan.nextLine()
            println("잘못된 정보입니다.")
        }
    }

    // menu 2 : 날짜 출력 후 날짜 입력받아 해당 날짜 운동 기록 출력하기
    fun selectDate() {
        // 날짜 데이터 시간 순서대로 정렬
        var sortedDateList = dateList.sorted()
        var n = 1
        println()

        // 날짜 메뉴 출력
        for (i in sortedDateList) {
            println("$n. $i")
            n++
        }

        // 날짜 선택
        try {
            println()
            print("날짜를 선택해주세요. (0.이전) : ")
            var inputNum = scan.nextInt()
            if (inputNum == 0) {
                programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
                return
            }
            if (inputNum > dateList.size) {
                println("해당 번호가 없습니다. 다시 입력해주세요.")
                return
            }

            // 날짜별 운동 기록 출력
            for (i in memoList) {
                var date = i.date
                if(date==sortedDateList.elementAt(inputNum-1)) {
                    println()
                    println("운동 종류 : ${i.type}")
                    println("횟수 : ${i.num}")
                    println("세트 : ${i.set}")
                }
            }
            programState = ProgramState.PROGRAM_STATE_INPUT_MENU_NUMBER
            } catch (e:Exception) {
                scan.nextLine()
                println("잘못된 정보입니다. 숫자로 다시 입력해주세요.")
                return
            }
    }

}

// 운동 기록 데이터
data class Memo (var date: LocalDate, var type:String, var num:Long, var set:Long) : Serializable {

}

// 프로그램 state 관리
enum class ProgramState {
    PROGRAM_STATE_INPUT_MENU_NUMBER, PROGRAM_STATE_INPUT_EXERCISE_INFO, PROGRAM_STATE_PRINT_EXERCISE_INFO, PROGRAM_STATE_READ_FILE, PROGRAM_SATAE_WRITE_FILE
}


