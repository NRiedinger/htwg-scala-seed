package de.htwg.se.skyjo.controller

import de.htwg.se.skyjo.model.{Deck, Hand, Player}
import de.htwg.se.skyjo.util.{Observable, UndoManager}


class Controller(var deck: Deck, player: Player) extends Observable {

  private val undoManager = new UndoManager
  player.hand.cards = deck.drawHand()


  def moveCursor(dir: String): Unit = {
    player.hand.move(dir)
    player.stillMyTurn = true
    notifyObservers
  }

  def uncoverCard(): Unit = {
    undoManager.doStep(new UncoverCommand(player))
    player.stillMyTurn = false
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    player.stillMyTurn = true
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    player.stillMyTurn = true
    notifyObservers
  }

  def tradeCard: Unit = {
    undoManager.doStep(new TradeCommand(player))
    player.stillMyTurn = false
    notifyObservers
  }

  def drawCard: Unit = {
    undoManager.doStep(new DrawCommand(player))
    player.stillMyTurn = true
    notifyObservers
  }
}
