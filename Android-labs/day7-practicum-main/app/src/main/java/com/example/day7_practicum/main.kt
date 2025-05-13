package com.example.day7_practicum

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

suspend fun main (){
    println("In main before coroutineScope.")
    coroutineScope {
    runBlocking {
             val job1 = launch {
                milkCows()
            }
            val job2 = launch {
                feedChickens()
            }
            job1.join()
            job2.join()
            println("in main in coroutineScope after two launches.")
        }
        println("in main after coroutineScope.")
    }
}

suspend fun milkCows(){
    var cow = 1
    println("milking cows #${cow}")
    yield()
    cow += 1
    println("milking cows #${cow}")
    yield()
    cow += 1
    println("milking cows #${cow}")
    yield()
    cow += 1
    println("milking cows #${cow}")
    yield()
    cow += 1
}

suspend fun feedChickens(){
    var chicken = 1
    println("Feeding Chickens #${chicken}")
    yield()
    chicken += 1
    println("Feeding Chickens #${chicken}")
    yield()
    chicken += 1
    println("Feeding Chickens #${chicken}")
    yield()
    chicken += 1
    println("Feeding Chickens #${chicken}")
    yield()
    chicken += 1
}
