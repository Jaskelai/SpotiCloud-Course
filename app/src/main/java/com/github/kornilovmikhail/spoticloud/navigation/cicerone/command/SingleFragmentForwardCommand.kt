package com.github.kornilovmikhail.spoticloud.navigation.cicerone.command

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.Command

class SingleFragmentForwardCommand(private val screen: Screen) : Command {

    fun getScreen() = screen
}
