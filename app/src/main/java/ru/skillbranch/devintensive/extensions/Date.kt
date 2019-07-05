package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern : String = "HH:mm:ss dd:MM:yy") : String{
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
 */
//fun Date.humanizeDiff(date : Date): String {
fun Date.humanizeDiff(date : Date = this): String {

    val differenceTime = Date().time - date.time

    if (differenceTime < 0) return "invalid date"

    return when(differenceTime){
        in 0L* SECOND..1L* SECOND -> "только что"
        in 1* SECOND..45* SECOND -> "несколько секунд назад"
        in 45* SECOND..75* SECOND -> "минуту назад"

        in 75* SECOND..45*MINUTE -> createTimeComments1(differenceTime/ MINUTE, TimeUnits.MINUTE)
        in 45*MINUTE..75*MINUTE -> "час назад"
        in 75*MINUTE..22*HOUR -> createTimeComments1(differenceTime/ HOUR, TimeUnits.HOUR)
        in 22*HOUR..26*HOUR -> "день назад"
        in 26*HOUR..360*DAY -> createTimeComments1(differenceTime/DAY, TimeUnits.DAY)
        else -> "более года назад"
    }
}

private fun createTimeComments(valueTime : Long, typeOfTime : TimeUnits) : String {
    return when(typeOfTime){
        TimeUnits.SECOND -> {
            return when(valueTime){
                1L -> "1 секунду назад"
                in 2L..5L -> "$valueTime секунды назад"
                else -> "$valueTime секунд назад"
            }
        }
        TimeUnits.MINUTE -> {
            /**
             * 1 минуту назад
             * 2, 3, 4 минуты
             * 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 минут
             * 21 минуту
             */
            return when(valueTime){
                in 5L..21L -> "$valueTime минут назад"
                1L -> "1 минуту назад"
                in 2L..5L -> "$valueTime минуты назад"
                else -> "$valueTime минут назад"
            }
        }
        TimeUnits.HOUR -> {
            /**
             * 1 час назад
             * 2, 3, 4 часа
             * 5, 6, 7, 8, 9, 10 часов
             */
            return when(valueTime){
                1L -> "1 час назад"
                in 2L..5L -> "$valueTime часа назад"
                else -> "$valueTime часов назад"
            }
        }
        TimeUnits.DAY -> {
            /**
             * 1 день
             * 2, 3, 4 дня
             * 5, 6, 7, 8, 9, 10 и т.д дней
             */
            return when(valueTime){
                1L -> "1 день назад"
                in 2L..5L -> "$valueTime дня назад"
                else -> "$valueTime дней назад"
            }
        }
    }
}

private fun createTimeComments1(valueTime : Long, typeOfTime : TimeUnits) : String {
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
                    1L -> "1 минуту назад"
                    in 2L..4L -> "$valueTime минуты назад"
                    else -> "$valueTime минут назад"
                }
            } else {
                if (valueTime < 100){
                    return when(valueTime.rem(10)){
                        1L -> "1 минуту назад"
                        in 2L..4L -> "$valueTime минуты назад"
                        else -> "$valueTime минут назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "1 минуту назад"
                        in 2L..4L -> "$valueTime минуты назад"
                        else -> "$valueTime минут назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "1 минуту назад"
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
                        1L -> "1 час назад"
                        in 2L..4L -> "$valueTime часа назад"
                        else -> "$valueTime часов назад"
                    }
                }
                return if (valueTime < 1000){
                    when(valueTime.rem(100)){
                        1L -> "1 час назад"
                        in 2L..4L -> "$valueTime часа назад"
                        else -> "$valueTime часов назад"
                    }
                } else {
                    when(valueTime.rem(1000)){
                        1L -> "1 час назад"
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

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}


