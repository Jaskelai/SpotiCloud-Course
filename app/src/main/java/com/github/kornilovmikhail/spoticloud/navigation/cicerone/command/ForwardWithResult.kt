package com.github.kornilovmikhail.spoticloud.navigation.cicerone.command

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.Command

class ForwardWithResult(private val screen: Screen, private val requestCode: Int) : Command {

    fun getScreen() = screen

    fun getRequestCode() = requestCode
}
