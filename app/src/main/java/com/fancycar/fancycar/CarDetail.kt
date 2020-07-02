package com.fancycar.fancycar

import kotlin.random.Random

data class CarDetail(
    var id: Long = 0,
    var name: String = "",
    var inDealer: Boolean = false,
    var make: String = "Lexus",
    var model: String =  "RX 350",
    var year: Int = 2020,
    var image: String = ""
) {
    companion object {
//        private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        private const val charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"


        fun generate(): CarDetail {
            return CarDetail(
                id = System.currentTimeMillis(),
                inDealer = Random.nextInt(0,2) == 1,
                name = getString(Random.nextInt(2,6))
            )
        }

        private fun getWord(len: Int): String {
            val allowedChars = ('a'..'z')
            return (1..len)
                .map { allowedChars.random() }
                .joinToString("").capitalize()
        }

        private fun getString(len: Int): String {
            var s = ""
            (1..len).forEach {
                s += getWord(Random.nextInt(2, 5)) + " "
            }
            return s
        }
    }
}
