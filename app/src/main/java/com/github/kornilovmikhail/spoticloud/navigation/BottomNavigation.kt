package com.github.kornilovmikhail.spoticloud.navigation

import java.util.LinkedList

class BottomNavigation<E : Any>(private val limit: Int) : LinkedList<E>() {

    override fun add(element: E): Boolean {
        // For uniqueness
        for (i in 0 until super.size) {
            val item = super.get(i)
            if (item == element) {
                super.removeAt(i)
                break
            }
        }
        val added = super.add(element)
        // For size limit
        while (added && size > limit) {
            super.remove()
        }
        return added
    }
}
