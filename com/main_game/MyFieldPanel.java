package com.main_game;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import com.main_game.main_game_model.MainGameModel;
import com.main_game.main_game_model.player_model.*;
import com.main_game.main_game_model.card_model.*;

public class MyFieldPanel extends JPanel {
  MainGameModel model;
  BasePlayer myPlayer;
  ArrayList<CardModel> myHands = null;
  JLabel deckImg = new JLabel( new ImageIcon("assets/img/card/deck.png") );

  public MyFieldPanel(MainGameModel model, JPanel mainPanel) {
    super();
    this.model = model;
    // カードの画像の高さが140pxなので160に設定
    this.setPreferredSize( new Dimension(mainPanel.getPreferredSize().width, 160) ); 

    this.setLayout( new FlowLayout() );

    myPlayer = model.getPlayer();
    myHands = myPlayer.getHands();

    ReshowCard();
  }
  public void setImvisible() {
    for(CardModel cm : myHands) {
      cm.getImageBtn().setVisible(false);
    }
    deckImg.setVisible(false);
  }

  public void ReshowCard(){
    for(CardModel cm : myHands) {
      // 将来的にTimerをセットしてアニメーションを入れる
      this.add(cm.getImageBtn());
      cm.getImageBtn().setVisible(true);
    }
    deckImg.setVisible(true);
    this.add(deckImg);
  }
}