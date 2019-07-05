package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

/**
 * Реализуй extension Date.format(pattern)
 * возвращающий отформатированную дату по паттерну
 * передаваемому в качестве аргумента (значение по умолчанию "HH:mm:ss dd.MM.yy" локаль "ru")
 * Пример:
 * Date().format() //14:00:00 27.06.19
 * Date().format("HH:mm") //14:00
 */
fun Date.format(pattern : String = "HH:mm:ss dd.MM.yy") : String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date{

    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}


/**
 * Реализуй extension Date.humanizeDiff(date)
 * (значение по умолчанию текущий момент времени)
 * для форматирования вывода разницы между датами в человекообразном формате,
 * с учетом склонения числительных.
 * Временные интервалы преобразований к человекообразному формату доступны в ресурсах к заданию
 * Пример:
 * Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
 * Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
 * Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
 * Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
 * Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
 * Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
 *
 * 0с - 1с "только что"
 * 1с - 45с "несколько секунд назад"
 * 45с - 75с "минуту назад"
 * 75с - 45мин "N минут назад"
 * 45мин - 75мин "час назад"
 * 75мин 22ч "N часов назад"
 * 22ч - 26ч "день назад"
 * 26ч - 360д "N дней назад"
 * >360д "более года назад"
 *
 * 1с - 45с "через несколько секунд"
 * 45с - 75с "через минуту"
 * 75с - 45мин "через N минут"
 * 45мин - 75мин "через час"
 * 75мин 22ч "через N часов"
 * 22ч - 26ч "через день"
 * 26ч - 360д "через N дней"
 * >360д "более чем через год"
 */
//fun Date.humanizeDiff(date : Date): String {
fun Date.humanizeDiff(date : Date = Date()): String {

    val timeThis = this.time
    val timeNow = date.time

    var differenceTime = timeNow - timeThis
    println("differenceTime = $differenceTime")

    differenceTime = fixDifferenceTime(differenceTime)

    println("differenceTime = $differenceTime")

    if (differenceTime >= 0) //now or future
        return when(differenceTime){
            in 0* SECOND..SECOND -> "только что"
            in 1* SECOND..45* SECOND -> "несколько секунд назад"
            in 46L* SECOND..75L* SECOND -> "минуту назад"

            in 75* SECOND..45*MINUTE -> createTimeCommentsPast(differenceTime/ MINUTE, TimeUnits.MINUTE)
            in 45*MINUTE..75*MINUTE -> "час назад"
            in 75*MINUTE..22*HOUR -> createTimeCommentsPast(differenceTime/ HOUR, TimeUnits.HOUR)
            in 22*HOUR..26*HOUR -> "день назад"
            in 26*HOUR..360*DAY -> createTimeCommentsPast(differenceTime/DAY, TimeUnits.DAY)
            else -> "более года назад"
        }
    else { //future
        differenceTime = abs(differenceTime)
        return when(differenceTime){
            1* SECOND -> "только что"
            in 1* SECOND..45* SECOND -> "через несколько секунд"
            in 45* SECOND..75* SECOND -> "через минуту"

            in 75* SECOND..45*MINUTE -> createTimeCommentsFuture(differenceTime/ MINUTE, TimeUnits.MINUTE)
            in 45*MINUTE..75*MINUTE -> "через час"
            in 75*MINUTE..22*HOUR -> createTimeCommentsFuture(differenceTime/ HOUR, TimeUnits.HOUR)
            in 22*HOUR..26*HOUR -> "через день"
            in 26*HOUR..360*DAY -> createTimeCommentsFuture(differenceTime/DAY, TimeUnits.DAY)
            else -> "более чем через год"
        }
    }
}

private fun fixDifferenceTime(value : Long) = value.div(1000) * 1000

private fun createTimeCommentsPast(valueTime : Long, typeOfTime : TimeUnits) : String {
    return when(typeOfTime){
        TimeUnits.SECOND -> {
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "1 секунду назад"
                    in 2L..4L -> "$valueTime секунды назад"
                    else -> "$valueTime секунд назад"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "$valueTime секунду назад"
                        in 2L..4L -> "$valueTime секунды назад"
                        else -> "$valueTime секунд назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "$valueTime секунду назад"
                        in 2L..4L -> "$valueTime секунды назад"
                        else -> "$valueTime секунд назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "$valueTime секунду назад"
                        in 2L..4L -> "$valueTime секунды назад"
                        else -> "$valueTime секунд назад"
                    }
                }
            }
        }
        TimeUnits.MINUTE -> {
            /**
             * 1 минуту назад
             * 2, 3, 4 минуты
             * 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 минут
             * 21 минуту
             */
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "$valueTime минуту назад"
                    in 2L..4L -> "$valueTime минуты назад"
                    else -> "$valueTime минут назад"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "$valueTime минуту назад"
                        in 2L..4L -> "$valueTime минуты назад"
                        else -> "$valueTime минут назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "$valueTime минуту назад"
                        in 2L..4L -> "$valueTime минуты назад"
                        else -> "$valueTime минут назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "$valueTime минуту назад"
                        in 2L..4L -> "$valueTime минуты назад"
                        else -> "$valueTime минут назад"
                    }
                }
            }
        }
        TimeUnits.HOUR -> {
            /**
             * 1 час назад
             * 2, 3, 4 часа
             * 5, 6, 7, 8, 9, 10 часов
             */
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "1 час назад"
                    in 2L..4L -> "$valueTime часа назад"
                    else -> "$valueTime часов назад"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "$valueTime час назад"
                        in 2L..4L -> "$valueTime часа назад"
                        else -> "$valueTime часов назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "$valueTime час назад"
                        in 2L..4L -> "$valueTime часа назад"
                        else -> "$valueTime часов назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "$valueTime час назад"
                        in 2L..4L -> "$valueTime часа назад"
                        else -> "$valueTime часов назад"
                    }
                }
            }
        }
        TimeUnits.DAY -> {
            /**
             * 1 день
             * 2, 3, 4 дня
             * 5, 6, 7, 8, 9, 10 и т.д дней
             */
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "1 день назад"
                    in 2L..4L -> "$valueTime дня назад"
                    else -> "$valueTime дней назад"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "1 день назад"
                        in 2L..4L -> "$valueTime дня назад"
                        else -> "$valueTime дней назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "1 день назад"
                        in 2L..4L -> "$valueTime дня назад"
                        else -> "$valueTime дней назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "1 день назад"
                        in 2L..4L -> "$valueTime дня назад"
                        else -> "$valueTime дней назад"
                    }
                }
            }
        }
    }
}

private fun createTimeCommentsFuture(valueTime : Long, typeOfTime : TimeUnits) : String {
    return when(typeOfTime){
        TimeUnits.SECOND -> {
            if (valueTime in 1..45)
                return "через несколько секунд"
            if (valueTime in 46..99){
                return when(valueTime.rem(10)){
                    1L -> "$valueTime секунду назад"
                    in 2L..4L -> "$valueTime секунды назад"
                    else -> "$valueTime секунд назад"
                }
            }
            return if (valueTime < 1000){
                when(valueTime.rem(100)){
                    1L -> "через $valueTime секунду"
                    in 2L..4L -> "через $valueTime секунды"
                    else -> "через $valueTime секунд"
                }
            } else {
                when(valueTime.rem(1000)){
                    1L -> "через $valueTime секунду"
                    in 2L..4L -> "через $valueTime секунды"
                    else -> "через $valueTime секунд"
                }
            }
        }
        TimeUnits.MINUTE -> {
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "через $valueTime минуту"
                    in 2L..4L -> "через $valueTime минуты"
                    else -> "через $valueTime минут"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "через $valueTime минуту"
                        in 2L..4L -> "через $valueTime минуты"
                        else -> "через $valueTime минут"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "через $valueTime минуту"
                        in 2L..4L -> "через $valueTime минуты"
                        else -> "через $valueTime минут"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "через $valueTime минуту"
                        in 2L..4L -> "через $valueTime минуты"
                        else -> "через $valueTime минут"
                    }
                }
            }
        }
        TimeUnits.HOUR -> {
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "через $valueTime час"
                    in 2L..4L -> "через $valueTime часа"
                    else -> "через $valueTime часов"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "через $valueTime час"
                        in 2L..4L -> "через $valueTime часа"
                        else -> "через $valueTime часов"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "через $valueTime час"
                        in 2L..4L -> "через $valueTime часа"
                        else -> "через $valueTime часов"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "через $valueTime час"
                        in 2L..4L -> "через $valueTime часа"
                        else -> "через $valueTime часов"
                    }
                }
            }
        }
        TimeUnits.DAY -> {
            if (valueTime < 21){
                return when(valueTime){
                    1L -> "через $valueTime день"
                    in 2L..4L -> "через $valueTime дня"
                    else -> "через $valueTime дней"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "через $valueTime день"
                        in 2L..4L -> "через $valueTime дня"
                        else -> "через $valueTime дней"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "через $valueTime день"
                        in 2L..4L -> "через $valueTime дня"
                        else -> "через $valueTime дней"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "через $valueTime день"
                        in 2L..4L -> "через $valueTime дня"
                        else -> "через $valueTime дней"
                    }
                }
            }
        }
    }
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}


