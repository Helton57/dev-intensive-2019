package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName : String?) : Pair<String?, String?>{
        val parts : List<String>? = fullName?.split(" ")

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
            "${firstName.capitalize()}$divider${lastName.capitalize()}"
        else if (firstName != null){
            firstName.capitalize()
        } else {
            "${lastName?.capitalize()}"
        }
    }

    private fun translateStrings(string: String?) : String?{
        var result = ""
        string?.trimIndent()?.toLowerCase()?.forEach {
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
            else -> result += char.toString()
        }
        }

        return if (result == "") null
        else result


    }






}