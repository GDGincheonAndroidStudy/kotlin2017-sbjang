package reports.gdgincheon.first

fun main(args : Array<String>) {
    // val NUM: Int = 2
    val LIMIT_DIGITS: Int = 500
    var digits = IntArray(LIMIT_DIGITS) // 저장공간 효율은 arraylist
    digits[0] = 2 // digits[0] = NUM // 초기값 지정
    var maxDigitPointer = 0

    for ( temp in 1..999) { // 1000승은 1..n-1번까지 반복
        for ( i in maxDigitPointer downTo 0 ){ // 각 자리수에 2배
            digits[i] *= 2 // digits[i] *= NUM
            if ( digits[i] > 9 ) { // 10이상일 떄
                digits[(i+1)] += 1 // digits[(i+1)] += (digits[i]/10).toInt()
                digits[i] = digits[i] - 10 // digits[i] = digits[i] % 10
                if( i==maxDigitPointer ) maxDigitPointer++
            }
        }
    }

    /*
    for ( i :Int in maxDigitPointer downTo 0 ) {
        print( digits[i] )
    }
    println( " " )
    */

    // 각 자리수 더하기
    var total = 0;
    for ( i :Int in 0..maxDigitPointer ) {
        total += digits[i]
    }
    println( total )

}
