import java.util.*

val scan = Scanner(System.`in`)

fun main(args: Array<String>){
    val x: Int = getNum()
    val y: Int = getNum()
    val op: Char = getOperation()
    calc(x, y, op)
}

fun getNum(): Int {
    println("Введите число ")
    var num: Int
    if (scan.hasNextInt())
        num = scan.nextInt()
    else{
        println("Число должно быть целым. Попробуйте еще раз ")
        scan.next()
        num = getNum()
    }
    return num
}

fun getOperation(): Char{
    println("Введите операцию ")
    val operations = arrayOf('+', '-', '*', '/','%')
    var operation: Char = scan.next().toCharArray()[0]
    if (operations.contains(operation))
        operation
    else{
        println("Вы допустили ошибку при вводе операции. Существующие операции : + , - , * , / , % . Попробуйте еще раз ")
        operation = getOperation()
    }
    return operation
}

fun calc(x: Int, y: Int, op: Char){
    print("Результат ")
    when(op){
        '+' -> println(x + y)
        '-' -> println(x - y)
        '*' -> println(x * y)
        '/' -> println(x / y)
        '%' -> println(x % y)
        else -> {
            println("Что-то пошло не так")
        }
    }

}


