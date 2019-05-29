package me.grapescan.cards.data

import java.util.*

interface Mapper<Input, Output> : (Input) -> Output {

    fun map(input: Input): Output

    fun map(input: Collection<Input>): List<Output> = LinkedList<Output>().apply {
        input.forEach {
            add(map(it))
        }
    }

    override fun invoke(input: Input) = map(input)
}
