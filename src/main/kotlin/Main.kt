import kotlin.system.exitProcess

class CoffeeMachine {

    private var water: Int = 0
    private var milk: Int = 0
    private var beans: Int = 0
    private var command: String = " "

    enum class Text (val text: String) {
        REQUEST_1("Введите команду"),
        MISTAKE_1("Ошибка. Введите корректную команду"),
        REQUEST_2("Введите название напитка или \"назад\" для возврата в главное меню"),
        MISTAKE_2("Рецепт не найден!");
    }

    private val commandMainMenu = listOf(
        "выключить",
        "наполнить",
        "статус",
        "кофе"
    )
    private val commandCoffeeMenu = listOf(
        "Эспрессо",
        "Американо",
        "Капучино",
        "Латте",
        "назад"
    )


    enum class Coffee(
        val coffeeName: String,
        val waterForPortion: Int,
        val milkForPortion: Int,
        val beansForPortion: Int
    ) {
        ESPRESSO("Эспрессо", 60, 0, 10),
        AMERICANO("Американо", 120, 0, 10),
        CAPPUCCINO("Капучино", 120, 60, 20),
        LATTE("Латте", 240, 120, 20);

        override fun toString(): String {
            return coffeeName
        }
    }

    private fun getCommand(
        request: Text,
        mistake: Text,
        commandMenu: List<String>
    ): String {
        println(request.text)
        val scanner = readlnOrNull()
        if (scanner in commandMenu) {
            command = scanner!!
        } else {
            println(mistake.text)
        }
        return command
    }

    private fun goToMainMenu() {
        command = " "
        while (command !in commandMainMenu) {
            getCommand(Text.REQUEST_1, Text.MISTAKE_1, commandMainMenu)
        }
        mainMenu(command)
    }

    private fun goToCoffeeMenu() {
        command = " "
        while (command !in commandCoffeeMenu) {
            getCommand(Text.REQUEST_2, Text.MISTAKE_2, commandCoffeeMenu)
        }
        if (command == "назад") {
            goToMainMenu()
        } else {
            makeCoffee(command)
        }
    }

    private fun mainMenu(command: String) {
        when (command) {
            "выключить" -> {
                println("До свидания!")
                exitProcess(0)
            }

            "наполнить" -> {
                water = 2000
                milk = 1000
                beans = 500
                println("Ингредиенты пополнены")
                goToMainMenu()
            }

            "статус" -> {
                println("Ингредиентов осталось: \n$water мл воды\n$milk мл молока\n$beans гр кофе")
                goToMainMenu()
            }

            "кофе" -> goToCoffeeMenu()
        }
    }

    private fun makeCoffee(command: String) {
        val newPortion = when (command) {
            "Эспрессо" -> Coffee.ESPRESSO
            "Американо" -> Coffee.AMERICANO
            "Капучино" -> Coffee.CAPPUCCINO
            else -> Coffee.LATTE
        }
        val condition1: Boolean = newPortion.waterForPortion <= water
        val condition2: Boolean = newPortion.milkForPortion <= milk
        val condition3: Boolean = newPortion.beansForPortion <= beans
        if (condition1 && condition2 && condition3) {
            water -= newPortion.waterForPortion
            milk -= newPortion.milkForPortion
            beans -= newPortion.beansForPortion
            println("${newPortion.coffeeName} готов")
            goToCoffeeMenu()
        } else {
            if (!condition1) println("Недостаточно воды!")
            if (!condition2) println("Недостаточно молока!")
            if (!condition3) println("Недостаточно кофе!")
            goToCoffeeMenu()
        }
    }

   fun start() {
        println("Кофемашина готова к работе")
        goToMainMenu()
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.start()
}