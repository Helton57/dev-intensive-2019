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
