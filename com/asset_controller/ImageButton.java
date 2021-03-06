package com.asset_controller;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import java.net.URL;

// assetsの中のimgをJButtonのように扱うことができるクラスです。

/* 使い方(以下のコンストラクタの引数は必ず4つ要素があるString配列型にしてください。 それ以外を入れるとコンパイルエラーになります。)
ImageButton btn = new ImageButton(
new String[] {
  "assets/img/button/pinkButton.png", // 通常状態で表示される画像
  "assets/img/button/pinkButton.png", // ボタンが押されている状態のときに表示する画像
  "assets/img/button/pinkButton.png", // マウスがボタンに覆いかぶさったときに表示する画像
  "assets/img/button/pinkButton.png"  // ボタンの機能を無効化させているときに表示する画像
}
);
また、assetのパスの指定の仕方はMain.javaがあるディレクトリからの相対パスとなります。
これをやるだけであとはJButtonと同じように使うことができます。
this.add(btn);
addActionListener(btn);
などこういった感じで使うことができます。

またボタンそのものを無効化したいときは
(ボタンが押せなくなってるあの状態です。この時はActionListenerに加えていてもそれが発動されなくなります。)
btn.Disabled();
というメソッドを使い、有効化したいときは
btn.Enabled();
というメソッドを使ってください。
*/

public class ImageButton extends JButton implements MouseListener {
  private javax.swing.ImageIcon image; // 画像ファイルを扱うためのクラスの宣言
  int w, h; // 画像の横幅、縦幅を格納する変数
  // 通常、押された状態、覆いかぶさった状態、無効状態のボタンの画像のパス。
  private ImageIcon normal, pressed, hovered, disabled;

  Boolean enable = true;

  public ImageButton(String[] imagePath) {
    // それぞれのPathを格納
    try {
      normal = new ImageIcon( ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath[0])) );
      pressed = new ImageIcon( ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath[1])) );
      hovered = new ImageIcon( ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath[2])) );
      disabled = new ImageIcon( ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath[3])));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.image = normal; // 表示画像を通常状態に設定
    setOpaque(false); // JButton panelの背景を透明にする。
    this.setBorderPainted(false); // 枠線をなしにする。
    this.w = image.getIconWidth(); // 画像の横幅を格納。
    this.h = image.getIconHeight(); // 画像の縦幅を格納。
    setPreferredSize(new Dimension(w, h) ); // ボタンのPanelのサイズを決定(JButtonはJPanelを継承しているため)

    this.addMouseListener(this); // MouseListenerに追加
  }

// ボタンの描画
  // Override
  public void paintComponent(Graphics g) {
    g.drawImage(image.getImage(), 0, 0, w, h, null);
  }

// ボタンの機能を有効化するメソッド
  public void Enabled() {
    this.setEnabled(true); // JButtonとしての機能を有効化する。
    image = normal; // ボタンの画像を通常状態に設定する
    enable = true;
    repaint(); // JPanel(JButton)の再描画
  }

 // ボタンの機能を無効化するメソッド
  public void Disabled() {
    this.setEnabled(false); // JButtonとしての機能を無効化する。
    image = disabled; // ボタンの画像を通常状態に設定する
    enable = false;
    repaint(); // JPanel(JButton)の再描画
  }

  // Override
  public void setEnabled(boolean b) {
    super.setEnabled(b);
    if(b) {
      image = normal; // ボタンの画像を通常状態に設定する
      enable = true;
    }
    else {
      image = disabled; // ボタンの画像を通常状態に設定する
      enable = false;
    }
    repaint(); // JPanel(JButton)の再描画
  }

// ボタンの画像変化処理の記述
  public void mouseClicked(MouseEvent e) { }
  public void mousePressed(MouseEvent e) {
    if(enable) {
      image = pressed;// ボタンの画像を押された状態に設定する
      repaint(); // JPanel(JButton)の再描画
    }
  }
  public void mouseReleased(MouseEvent e) {
		if(enable) {
			image = hovered; // ボタンの画像をhover状態に設定する
			repaint(); // JPanel(JButton)の再描画
		}
  }
  public void mouseEntered(MouseEvent e) {
    if(enable){
      image = hovered; // ボタンの画像をhover状態に設定する
      repaint(); // JPanel(JButton)の再描画
    }
  }
  public void mouseExited(MouseEvent e) {
    if(enable) {
      image = normal; // ボタンの画像を通常状態に設定する
      repaint(); // JPanel(JButton)の再描画
    }
  }
}