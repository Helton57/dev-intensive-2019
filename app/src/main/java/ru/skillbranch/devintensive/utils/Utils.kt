package ru.skillbranch.devintensive.utils

object Utils {

    private val mapLetters : Map<String, String> = mapOf("а" to "a", "б" to "b", "в" to "v", "г" to "g",
        "д" to "d", "е" to "e", "ё" to "e", "ж" to "zh", "з" to "z", "и" to "i", "й" to "i", "к" to "k",
        "л" to "l", "м" to "m", "н" to "n", "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t",
        "у" to "u", "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch", "ш" to "sh", "щ" to "sh'", "ъ" to "",
        "ы" to "i", "ь" to "", "э" to "e", "ю" to "yu", "я" to "ya" )

    fun parseFullName(fullName : String?) : Pair<String?, String?>{
        val parts : List<String>? = fullName?.trimIndent()?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName.equals("")) firstName = null
        if (lastName.equals("")) lastName = null

        //аналог return Pair(firstName, lastName)
        return firstName to lastName
    }

    /**
     * принимает в качестве аргументов имя и фамилию пользователя
     * (null, пустую строку) и
     * возвращающий строку с первыми буквами имени и фамилии в верхнем регистре
     * (если один из аргументов null то вернуть один инициал,
     * если оба аргумента null вернуть null)
     */
    fun toInitials(firstName: String?, lastName: String?): String? {

        var firstName = firstName?.trimIndent()
        var lastName = lastName?.trimIndent()
        if (firstName == "") firstName = null
        if (lastName == "") lastName = null

        //оба null
        if ((firstName == null) && (lastName == null)) return null

        val initialFirst = firstName?.getOrNull(0)
        val initialLast = lastName?.getOrNull(0)

        var result = ""

        //записать, если не null
        if (initialFirst != null)  result = "${initialFirst.toUpperCase()}"
        if (initialLast != null) result += "${initialLast.toUpperCase()}"

        return if (result == "") null
        else result
    }


    /**
     * Реализуй метод Utils.transliteration(payload divider),
     * принимающий в качестве аргумента строку (divider по умолчанию " ")
     * и возвращающий преобразованную строку из латинских символов,
     * словарь символов соответствия алфавитов доступен в ресурсах к заданию
     * Пример:
     * Utils.transliteration("Женя Стереотипов") //Zhenya Stereotipov
     * Utils.transliteration("Amazing Петр","_") //Amazing_Petr
     */
    fun transliteration(payload: String, divider : String = " "): String {

        var result = ""
        payload.trimIndent().forEach {
                char ->
            run {

                val isUpperCase: Boolean = char.isUpperCase()
                var letter = char.toString()

                if (letter == " ") letter = divider

                result += if (mapLetters.containsKey(letter.toLowerCase())){
                    if (isUpperCase)
                        mapLetters[letter.toLowerCase()]?.capitalize()
                    else
                        mapLetters[letter.toLowerCase()]
                } else
                    letter
            }

        }
        return result
    }
}