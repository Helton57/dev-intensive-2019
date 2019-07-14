package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender (var status: Status = Status.NORMAL, var question: Question = Question.NAME){

    fun askQuestion() : String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL  -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    /**
     * Bender.listenAnswer (positive case)
     * Необходимо реализовать метод listenAnswer класса Bender,
     * принимающий в качестве аргумента ответ пользователя и возвращающий Pair,
     * содержащую следующий вопрос и цвет текущего статуса экземпляра класса Bender
     *
     * Реализуй метод listenAnswer с сигнатурой listenAnswer(answer: String): Pair>.
     * Вопросы и ответы класса Bender, а также значения цветов статусов, прикреплены к ресурсам урока
     * Требования к методу:
     * При вводе верного ответа изменить текущий вопрос на следующий вопрос
     * (question = question.nextQuestion()) и вернуть "Отлично - ты справился\n${question.question}" to status.color
     * Если вопросы закончились (Question.IDLE), вернуть "Отлично - ты справился\nНа этом все, вопросов больше нет"
     * Необходимо сохранять состояние экземпляра класса Bender при пересоздании Activity (достаточно сохранить Status, Question)
     * Пример:
     * //Как меня зовут?
     * benderObj.listenAnswer("Bender") //Отлично - ты справился\nНазови мою профессию?
     * //Мой серийный номер?
     * benderObj.listenAnswer("2716057") //Отлично - ты справился\nНа этом все, вопросов больше нет
     * //Как меня зовут?
     * benderObj.listenAnswer("Bender") //Отлично - ты справился\nНазови мою профессию?
     * //onPause() -> onStop() -> onDestroy() -> onCreate()
     * //Назови мою профессию?
     */

    /**
     * Bender.listenAnswer (negative case)
     * Необходимо реализовать метод listenAnswer класса Bender,
     * принимающий в качестве аргумента ответ пользователя и возвращающий Pair,
     * содержащую текст ошибки и цвет следующего статуса экземпляра класса Bender
     *
     * Реализуй метод listenAnswer со следующей сигнатурой listenAnswer(answer: String): Pair>.
     * Вопросы и верные ответы, а также значения цветов статусов, прикреплены к ресурсам урока
     *
     * Требования к методу:
     * При вводе неверного ответа изменить текущий статус на следующий статус (status = status.nextStatus()),
     * вернуть "Это неправильный ответ\n${question.question}" to status.color
     * и изменить цвет ImageView (iv_bender) на цвет status.color (метод setColorFilter(color,"MULTIPLY"))
     *
     * При вводе неверного ответа более 3 раз сбросить состояние сущности Bender на значение по умолчанию
     * (status = Status.NORMAL, question = Question.NAME) и вернуть
     * "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
     * и изменить цвет ImageView (iv_bender) на цвет status.color
     *
     * Необходимо сохранять состояние экземпляра класса Bender при пересоздании Activity (достаточно сохранить Status, Question)
     * Пример:
     * //Как меня зовут? #NORMAL(Triple(255, 255, 255))
     * benderObj.listenAnswer("Fry") //Это неправильный ответ\nКак меня зовут? #WARNING(Triple(255, 120, 0))
     * //Мой серийный номер? #CRITICAL(Triple(255, 0, 0))
     * benderObj.listenAnswer("0000000") //Это неправильный ответ. Давай все по новой\nКак меня зовут? #NORMAL(Triple(255, 255, 255))
     * //Как меня зовут? #DANGER(Triple(255, 60, 60))
     * benderObj.listenAnswer("Fry") //Это неправильный ответ\nКак меня зовут? #CRITICAL(Triple(255, 0, 0))
     * //onPause() -> onStop() -> onDestroy() -> onCreate()
     * //Как меня зовут? #CRITICAL(Triple(255, 0, 0))
     */
    fun listenAnswer(answer : String) : Pair <String, Triple<Int, Int, Int>>{

        return if (question.answers.contains(answer.toLowerCase())){
            val defaultString = "Отлично - ты справился"
            var validationErrorMessage = validateString(answer)
            /**
             * change question only if isValidate has not errors
             */
            if (validationErrorMessage == null){
                question = question.nextQuestion()
                validationErrorMessage = defaultString
            }
            "$validationErrorMessage\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            if (status == Status.values()[0]){
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                "Это неправильный ответ!\n${question.question}" to status.color
            }
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>){
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status{
            return if (this.ordinal < values().lastIndex)
                values()[this.ordinal+1]
            else
                values()[0]
        }
    }

    enum class Question(val question: String, val answers:List<String>){
        NAME("Как меня зовут?", listOf("бендер","bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл","дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    /**
     * *Проверка правильности формата ответов
     * Необходимо реализовать проверку вводимых пользователем ответов на соответствие условиям валидации для каждого типа вопроса
     *
     * Реализуй проверку вводимых пользователем ответов на соответствие условиям валидации для каждого типа вопроса (валидация НЕ влияет на Status)
     * Question.NAME -> "Имя должно начинаться с заглавной буквы"
     * Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
     * Question.MATERIAL -> "Материал не должен содержать цифр"
     * Question.BDAY -> "Год моего рождения должен содержать только цифры"
     * Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
     * Question.IDLE -> //игнорировать валидацию
     *
     * Пример:
     * //Как меня зовут? #NORMAL(Triple(255, 255, 255))
     * benderObj.listenAnswer("bender") //Имя должно начинаться с заглавной буквы\nКак меня зовут? #NORMAL(Triple(255, 255, 255))
     * //Отлично - ты справился\nНа этом все, вопросов больше нет #NORMAL(Triple(255, 255, 255)
     * benderObj.listenAnswer("any text") //На этом все, вопросов больше нет #NORMAL(Triple(255, 255, 255))
     *
     * Question.NAME -> "Имя должно начинаться с заглавной буквы"
     * Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
     * Question.MATERIAL -> "Материал не должен содержать цифр"
     * Question.BDAY -> "Год моего рождения должен содержать только цифры"
     * Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
     * Question.IDLE -> //игнорировать валидацию
     */
    fun validate(answer : String) : Boolean {
        return when(question){
            Question.NAME -> {
                answer[0].isUpperCase()
            }
            Question.PROFESSION -> {
                answer[0].isLowerCase()
            }
            Question.MATERIAL -> {
                var resultValue: Boolean = true
                for (char : Char in answer) resultValue = resultValue && (!char.isDigit())
                return resultValue
            }
            Question.BDAY -> {
                return answer.isDigitsOnly()
            }
            Question.SERIAL -> {
                return answer.isDigitsOnly() && answer.length == 7
            }
            else -> true //IDLE
        }
    }

    private fun validateString(answer : String) : String? {
        val resultString = null
        return when(question){
            Question.NAME -> {
                if (answer[0].isUpperCase())
                    resultString
                else
                    "Имя должно начинаться с заглавной буквы"
            }
            Question.PROFESSION -> {
                if (answer[0].isLowerCase())
                    resultString
                else
                    "Профессия должна начинаться со строчной буквы"
            }
            Question.MATERIAL -> {
                var resultValue: Boolean = true
                for (char : Char in answer) resultValue = resultValue && (!char.isDigit())
                if (resultValue){
                    resultString
                } else {
                    "Материал не должен содержать цифр"
                }
            }
            Question.BDAY -> {
                if (answer.isDigitsOnly()){
                    resultString
                } else {
                    "Год моего рождения должен содержать только цифры"
                }
            }
            Question.SERIAL -> {
                if (answer.isDigitsOnly() && answer.length == 7) {
                    resultString
                } else {
                    "Серийный номер содержит только цифры, и их 7"
                }
            }
            else -> resultString //IDLE
        }
    }
}