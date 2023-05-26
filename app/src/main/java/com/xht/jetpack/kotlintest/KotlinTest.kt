package com.xht.jetpack.kotlintest


class KotlinTest {

    fun test() {
        val min1 = min(20, 3)
        println(min1)
    }

    val min: (Int, Int) -> Int = { x, y ->
        //只能返回Int类型，最后一句表达式的返回值必须为Int
        //if表达式返回Int
        if (x < y) {
            x
        } else {
            y
        }
    }

    fun test1() {
        val persons = listOf(Person(18, "xixi"), Person(20, "haha"), Person(23, "lala"))
        val max1 = persons.maxByOrNull({ person: Person ->
            person.age
        })

        val max2 = persons.maxByOrNull() { person: Person ->
            person.age
        }

        val max3 = persons.maxByOrNull { person: Person ->
            person.age
        }

        val max4 = persons.maxByOrNull { person ->
            person.age
        }

        val max5 = persons.maxByOrNull { it.age }

        //val getAge = { p -> p.age }
        val getAge: (Person) -> Int = { p ->
            p.age
        }

        val joinToString = persons.joinToString("&") { person ->
            person.name
        }
        println(max1?.name)
        println(joinToString)

        persons.joinToString(separator = "|", transform = { person ->
            person.name
        })
    }

    fun test2() {
        var persons: List<Person>? = null
        persons = listOf(Person(18, "xixi"), Person(20, "haha"), Person(23, "lala"))
        val person = persons?.let(bb)
        println(person?.name)
    }

    val aa = fun(person: Person): Int {
        return person.age
    }

    val bb = fun(personList: List<Person>): Person? {
        return personList.maxByOrNull(aa)
    }

    fun test3() {
        val isEven = object : IntPredicate {
            override fun accept(i: Int): Boolean {
                return i % 2 == 0
            }
        }
    }

    fun test4() {
        val stringBuilder = StringBuilder()
        val result = with(stringBuilder, fun StringBuilder.(): String {
            append("daqi在努力学习Android")
            append("daqi在努力学习Kotlin")
            //最后一个表达式作为返回值返回
            return this.toString()
        })
        //打印结果便是上面添加的字符串
        println(result)
    }

    fun test5() {
        val stringBuilder = StringBuilder().apply {
            append("daqi在努力学习Android")
            append("daqi在努力学习Kotlin")
        }
        println(stringBuilder.toString())
    }

    fun test6() {
        val array = listOf("java", "kotlin")
        val buffer = with(StringBuffer()) {
            array.forEach { str ->
                if (str == "kotlin") {
                    return@with this.append(str)
                }
            }
        }
        println(buffer.toString())
    }

    companion object {
        /** 我是main入口函数 **/
        @JvmStatic
        fun main(args: Array<String>) {
            val kotlinTest = KotlinTest()
            //kotlinTest.test2()
            kotlinTest.test6()
            val innerTest = kotlinTest.InnerTest()
            innerTest.log()
        }
    }

    inner class InnerTest {
        var name: String = "haha"
        fun log() {
            println(name)
        }
    }
}

data class Person(val age: Int, val name: String)

fun interface IntPredicate {
    fun accept(i: Int): Boolean
}