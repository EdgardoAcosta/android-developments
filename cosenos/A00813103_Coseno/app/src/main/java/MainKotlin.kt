import java.math.*
import kotlin.math.pow

val threshold: BigDecimal = BigDecimal.valueOf(0.00001)

// Funcion Main
fun main(args: Array<String>) {
    println("..... Bienvenido ..... ")

    var exitFalag = true
    var stringInput: String
    var doubleInput: Double


    while (exitFalag) {
        // Preguntar si el usuario quiere leer algun valor
        print("Quieres calcular un valor? (s/n) ")
        stringInput = readLine()!!
        // Comprobar valor ingresado
        if (stringInput == "n" || stringInput == "N" && stringInput != "s" && stringInput != "S") {
            println("..... Exit .....")

            exitFalag = false
        } else {
            // Solicitar valor a calcular
            print("Escribe un valor: ")
            doubleInput = readLine()!!.toDouble()
            // Validar valor ingresado
            validate(doubleInput)

        }

    }

}

// Funcion paara validar el input del usuario, ademas manda a llamar a la sumatoria si es valido
fun validate(num: Double) {

    if (num in 0..360) {
        // Calcular coseno
        val resultado = sumatoria(gradToRad(num))
        // Imprimir resultado en el formato correcto
        printRes(num, resultado)

    } else {
        // Error de valor del usuario
        println("Lo sentimos el valor dado no cumple el rango permitido")
    }
}

// Funcion para calcular la sumatorio
fun sumatoria(num: Double): BigDecimal {
    // Inicializar variables
    var result1: BigDecimal
    var result2: BigDecimal
    var finalResult = BigDecimal.valueOf(0)
    val potencia = 2
    var intCounter = 0

    var exit = false

    // Calcular primer valor
    result1 = BigDecimal.valueOf(num.pow(potencia * intCounter)).divide(factorial(potencia * intCounter),
            10, RoundingMode.HALF_UP)
    finalResult = finalResult.add(BigDecimal.valueOf((-1.0).pow(intCounter)).multiply(result1))

    // Mientras no se cumpla que el valor absoluto del ultimo menos el penultimo resultado sea menor
    // que el threshold
    while (!exit) {
        intCounter += 1

        // Calcular proximo valor
        result2 = BigDecimal.valueOf((-1.0).pow(intCounter)).multiply(BigDecimal.valueOf(
                num.pow(potencia * intCounter)).divide(factorial(potencia * intCounter),
                10, RoundingMode.HALF_UP))

        // Guardarlo en los resultados
        finalResult = finalResult.add(result2)

        // Comparar diferencias
        if ((result2 - result1).abs() <= threshold) {
            exit = true
        } else {
            result1 = result2
        }

    }

    return finalResult
}

// Calcular factorial de cualquier numero
fun factorial(num: Int): BigDecimal {

    var fact = BigInteger.valueOf(1)
    for (i in 1..num) {
        fact = fact.multiply(BigInteger.valueOf(i.toLong()))
    }
    return fact.toBigDecimal()


}

// Funcion para imprimir el resultado
fun printRes(grados: Double, resultado: BigDecimal) = println("El coseno de $grados.toInt() grados es $resultado")

// Funcion para cambiar de grados a radianes
fun gradToRad(grados: Double): Double = grados * (Math.PI / 180)