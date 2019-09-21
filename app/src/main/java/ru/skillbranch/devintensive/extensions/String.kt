package ru.skillbranch.devintensive.extensions

/**
 * Необходимо реализовать метод truncate, усекающий исходную строку до указанного числа символов
 * и добавляющий заполнитель "..." в конец строки
 *
 * Реализуй extension, усекающий исходную строку до указанного числа символов (по умолчанию 16)
 * и возвращающий усеченную строку с заполнителем "..." (если строка была усечена)
 * если последний символ усеченной строки является пробелом - удалить его и добавить заполнитель
 * Пример:
 * "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending R...
 * "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15) //Bender Bending...
 * "A     ".truncate(3) //A
 */
fun String.truncate(lengthValue : Int = 16) : String {
    val string = this.trim( )
    return when(string.length){
        in 0..lengthValue ->{
            string.trimEnd()
        }
        lengthValue -> {
            string
        }
        else -> {
            val subStr = string.substring(0, lengthValue)
            if (subStr[subStr.length-1].toString() == " ")
                "${subStr.trimEnd()}..."
            else
                "$subStr..."
        }
    }
}

/**
 * Необходимо реализовать метод stripHtml для очистки строки от
 * лишних пробелов,
 * html тегов,
 * escape последовательностей
 *
 * Реализуй extension, позволяющий очистить строку от html тегов и
 * html escape последовательностей("& < > ' ""),
 * а так же удалить пустые символы (пробелы) между словами если их больше 1.
 * Необходимо вернуть модифицированную строку
 * Пример:
 * "<p class="title">Образовательное IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
 * "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
 */
fun String.stripHtml(): String? =
    this
        .replace(Regex("(&[a-z]*?;)|(&#[0-9]*;)"), "")
        .replace(Regex("<[^>]*>"), "")
        .replace(Regex("[ ]+"), " ")

fun String.isNonDigitsOnly() : Boolean{
    var resultValue = true
    for (char : Char in this) resultValue = resultValue && (!char.isDigit())
    return resultValue
}


/**
 * https://dev.to/catherinecodes/a-regex-cheatsheet-for-all-those-regex-haters-and-lovers--2cj1
 *
 * /* lookaheads */
    regex = /z(?=a)/; // positive lookahead... matches the "z" before the "a" in pizza" but not the first "z"
    regex = /z(?!a)/; // negative lookahead... matches the first "z" but not the "z" before the "a"
 * /* groups */
    regex = /it is (ice )?cold outside/; // matches "it is ice cold outside" and "it is cold outside"
    regex = /it is (?:ice )?cold outside/; // same as above except it is a non-capturing group

 */
fun String.isRepositoryGithubValid() : Boolean{
    val exceptions = getExcludePathOfGithub().joinToString("|")
    val regExrString = "^(https://)?(www\\.)?(github\\.com/)(?!($exceptions)(?=/|\$))[a-zA-Z\\d](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){0,38}(/)?$"

    return if (isEmpty())
        true
    else {
        regExrString.toRegex().matches(this)
    }
}

fun getExcludePathOfGithub() = arrayOf(
    "enterprise",
    "features",
    "topics",
    "collections",
    "trending",
    "events",
    "marketplace",
    "pricing",
    "nonprofit",
    "customer-stories",
    "security",
    "login",
    "join"
)