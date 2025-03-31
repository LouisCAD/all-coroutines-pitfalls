package content.basics

val crappyChatBot = ChatBot()
val humanLabor = HumanLabor()

suspend fun answerToTheUltimateQuestion(): String {
    val question = "What is the answer to the ultimate question?"
    val essay = crappyChatBot.ask(question)
    return humanLabor.sanitizeNonsense(essay)
}

// Supporting symbols below

class A {
    fun answerToTheUltimateQuestion(): Int = 42
}

private suspend fun a() {
    A().answerToTheUltimateQuestion()
    answerToTheUltimateQuestion()
}

class ChatBot {
    suspend fun ask(question: String): String {
        TODO()
    }
}

class HumanLabor {
    suspend fun sanitizeNonsense(essay: String): String {
        TODO()
    }
}
