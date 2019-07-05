package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName : String?) : Pair<String?, String?>{
        val parts : List<String>? = fullName?.trimIndent()?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName.equals("")) firstName = null
        if (lastName.equals("")) lastName = null

        //аналог return Pair(firstName, lastName)
        return firstName to lastName
    }

    private fun parseFullName(fullName : String?, delimiter : String = " ") : Pair<String?, String?>{

        val parts : List<String>? = fullName?.split(delimiter)

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
        var (firstName, lastName) = parseFullName(payload)

        firstName = translateStrings(firstName)
        lastName = translateStrings(lastName)

        var result = ""

        return if (firstName != null && lastName != null)
            "$firstName$divider$lastName"
//            "${firstName.capitalize()}$divider${lastName.capitalize()}"
        else if (firstName != null){
            firstName
//            firstName.capitalize()
        } else {
            "$lastName"
//            "${lastName?.capitalize()}"
        }
    }

    private fun translateStrings(string: String?) : String?{
        var result = ""
        string?.trimIndent()?.forEach {
                char -> when(char.toString()){
            "а" -> result += "a"
            "б" -> result += "b"
            "в" -> result += "v"
            "г" -> result += "g"
            "д" -> result += "d"
            "е" -> result += "e"
            "ё" -> result += "e"
            "ж" -> result += "zh"
            "з" -> result += "z"
            "и" -> result += "i"
            "й" -> result += "i"
            "к" -> result += "k"
            "л" -> result += "l"
            "м" -> result += "m"
            "н" -> result += "n"
            "о" -> result += "o"
            "п" -> result += "p"
            "р" -> result += "r"
            "с" -> result += "s"
            "т" -> result += "t"
            "у" -> result += "u"
            "ф" -> result += "f"
            "х" -> result += "h"
            "ц" -> result += "c"
            "ч" -> result += "ch"
            "ш" -> result += "sh"
            "щ" -> result += "sh'"
            "ъ" -> result += ""
            "ы" -> result += "i"
            "ь" -> result += ""
            "э" -> result += "e"
            "ю" -> result += "yu"
            "я" -> result += "ya"

            "а".capitalize() -> result += "a".capitalize()
            "б".capitalize() -> result += "b".capitalize()
            "в".capitalize() -> result += "v".capitalize()
            "г".capitalize() -> result += "g".capitalize()
            "д".capitalize() -> result += "d".capitalize()
            "е".capitalize() -> result += "e".capitalize()
            "ё".capitalize() -> result += "e".capitalize()
            "ж".capitalize() -> result += "zh".capitalize()
            "з".capitalize() -> result += "z".capitalize()
            "и".capitalize() -> result += "i".capitalize()
            "й".capitalize() -> result += "i".capitalize()
            "к".capitalize() -> result += "k".capitalize()
            "л".capitalize() -> result += "l".capitalize()
            "м".capitalize() -> result += "m".capitalize()
            "н".capitalize() -> result += "n".capitalize()
            "о".capitalize() -> result += "o".capitalize()
            "п".capitalize() -> result += "p".capitalize()
            "р".capitalize() -> result += "r".capitalize()
            "с".capitalize() -> result += "s".capitalize()
            "т".capitalize() -> result += "t".capitalize()
            "у".capitalize() -> result += "u".capitalize()
            "ф".capitalize() -> result += "f".capitalize()
            "х".capitalize() -> result += "h".capitalize()
            "ц".capitalize() -> result += "c".capitalize()
            "ч".capitalize() -> result += "ch".capitalize()
            "ш".capitalize() -> result += "sh".capitalize()
            "щ".capitalize() -> result += "sh'".capitalize()
            "ъ".capitalize() -> result += "".capitalize()
            "ы".capitalize() -> result += "i".capitalize()
            "ь".capitalize() -> result += "".capitalize()
            "э".capitalize() -> result += "e".capitalize()
            "ю".capitalize() -> result += "yu".capitalize()
            "я".capitalize() -> result += "ya".capitalize()

            else -> result += char.toString()
        }
        }

        return if (result == "") null
        else result


    }






}