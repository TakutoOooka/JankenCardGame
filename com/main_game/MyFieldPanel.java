/*
  自分のフィールドのViewをつかさどるJPanel

*/

package com.main_game;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.IOException;

import com.main_game.main_game_model.MainGameModel;
import com.main_game.main_game_model.player_model.*;
import com.main_game.main_game_model.card_model.*;

public class MyFieldPanel extends JPanel {
  MainGameModel model;
  BasePlayer myPlayer;
  ArrayList<CardModel> myHands = null;
  JLabel deckImg;

  public MyFieldPanel(MainGameModel model, JPanel mainPanel) {
    super();
    this.model = model;
    try {
      deckImg = new JLabel( 
        new ImageIcon(
          ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/img/card/btnimg/deck.png"))
        )
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setOpaque(false); // 背景画像を表示するためにこのJPanelそのものを透明化する。
    // カードの画像の高さが140pxなので160に設定
    this.setPreferredSize( new Dimension(mainPanel.getPreferredSize().width, 160) ); 

    this.setLayout( new FlowLayout() );

    myPlayer = model.getPlayer();
    myHands = myPlayer.getHands();

  }
  
  // このフィールドをいったんすべて見えなくするメソッド。 再描画のために使う
  public void setImvisible() {
    for(CardModel cm : myHands) {
      cm.getImageBtn().setVisible(false);
    }
    deckImg.setVisible(false);
  }

//  再描画のために使う
  public void ReshowCard(){
    this.removeAll();
    for(CardModel cm : myHands) {
      // 将来的にTimerをセットしてアニメーションを入れる
      this.add(cm.getImageBtn());
      cm.getImageBtn().setVisible(true);
    }
    this.add(deckImg);
    deckImg.setVisible(true);
  }
}