package com.explain;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;

import com.FrameController;
import com.asset_controller.*;

final public class ExplainFrame extends JFrame implements ActionListener {

  JPanel cardPanel; // CardLayoutを張り付けるPanel
  CardLayout layout; 
  ImageButton previousButton;
  ImageButton nextButton;
  BufferedImage backgroundImage = null; // 背景画像のインスタンスを保存するための変数

  public ExplainFrame(){

    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setBounds(100,100,820,600);

    // cardPanelに張り付ける2枚のPanelのうちの1枚目
    //　説明文
    JPanel card1 = new JPanel();
    card1.setLayout(new BorderLayout());
    JLabel label = new JLabel(
        "<html><body><h3>＜遊び方説明＞<h3>"+
        "<h4>〇概要<h4>"+
        "　7種類のじゃんけんカードで40枚のデッキを作り、カードを用いてじゃんけんをするゲーム。<br />"+
        "　先に20ポイントある相手のライフポイントを0にするか、相手のデッキにカードが無くなると勝ちになります。<br />"+
        "<h4>〇カード紹介<h4>"+
        "　・カードの種類<br />"+
        "　　グー、チョキ、パー、グーチー、チーパー、パーグー、グーチョキパーの計7種類のカードがあります。<br />"+
        "　　カードの相性は次ページのカード相性表で確認してください。<br />"+
        "　・点数計算<br />"+
        "　　カードの種類によって、勝った時相手に与えるダメージが変わります。<br />"+
        "　　(1)グー、チョキ、パー : 2point<br />"+
        "　　(2)グーチー、チーパー、パーグー : 5point<br />"+
        "　　(3)グーチョキパー : 7point<br />"+
        "<h4>〇ゲームの進め方<h4>"+
        "　１．ゲームを始める前に、[デッキ編集]でデッキを作ります。<br />"+
        "　　　デッキは全部で40枚で、グーチーパーが3枚、グーチー、チーパー、パーグーがそれぞれ5枚ずつまでの枚数制限があります。<br />"+
        "　２．デッキ編集が終了したら、ホーム画面に戻り、[1人で遊ぶ]、[2人で遊ぶ]でゲーム開始です。<br />"+
        "　３．ゲーム画面の説明です。<br />"+
        "　　　画面下に自分のデッキと5枚の手札、画面中央にバトルフィールド、画面上に相手のデッキと5枚の手札があります。<br />"+
        "　４．始めに先攻を決めるじゃんけんをします。<br />"+
        "　　　グーチョキパーの3つのカードの中から1枚を選択すると、自動で先攻を決めるじゃんけんが行われます。<br />"+
        "　　　もし自分の手札が相手より先に並べられたら、あなたが先行、自分より先に相手の手札が先に並べられたら、後攻となります。<br />"+
        "　４．始めにドローフェイズです。デッキから1枚ドローし、自分の手札が6枚になります。<br />"+
        "　５．次にバトルフェイズです。自分の手札から1枚、じゃんけんをするカードを手札からフィールドに出します。<br />"+
        "　　　1枚選んだあとに[カードを出す]でお互いのカードを開示します。<br />"+
        "　　　勝った場合は相手にダメージ、負けた場合は自分にダメージが与えられます。<br />"+
        "　　　ダメージ計算が終わった後は、[次のバトルへ進む]で、4.に戻ります。<br />"+
        "　６．バトルを続け、先に相手のライフポイントを0にするか、相手のデッキにカードが無くなると勝ちになります。<br />"+
        "　７．最後に[Go result]で、ゲーム結果を見ることができます。</body></html>");
     card1.add(label,BorderLayout.NORTH);

     // 1枚目にスクロール追加
     JScrollPane scrollpane = new JScrollPane(label); 
     scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
     scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     card1.add(scrollpane);

     // cardPanelに張り付ける2枚のPanelのうちの2枚目
     JTable table;
     JScrollPane pane;
     JPanel card2 = new JPanel();

     // 勝敗表tableに入れ込むデータ配列
     String[][] data = {
        {"グー","△","〇","×","△","×","×","×"},
        {"チョキ","×","△","〇","×","△","×","×"},
        {"パー","〇","×","△","×","×","△","×"},
        {"グーチー","△","〇","〇","△","×","〇","×"},
        {"チーパー","〇","△","〇","〇","△","×","×"},
        {"パーグー","〇","〇","△","×","〇","△","×"},
        {"グーチーパー","〇","〇","〇","〇","〇","〇","△"},
     };

     // 勝敗表tableに入れ込むデータ
     String[] header = {"あなた|相手","グー","チョキ","パー","グーチー","チーパー","パーグー","グーチーパー"};
     table = new JTable(data,header);
     table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     pane = new JScrollPane(table);
     card2.add(pane,BorderLayout.CENTER);

     JScrollPane sp = new JScrollPane(table);
     sp.setPreferredSize(new Dimension(605, 135));
     card2.add(sp);


     //　新しくパネル生成
     cardPanel = new JPanel();
     layout = new CardLayout();
     cardPanel.setLayout(layout);

     //　cardPannleに2つのPanel追加
     cardPanel.add(card1, "description");
     cardPanel.add(card2, "flagSheet");
 
     //　カード移動用ボタン追加
     previousButton = new ImageButton(
        new String[] {
                "assets/img/edit_button/toPreviousButton.png",
                "assets/img/edit_button/toPreviousButton_pressed.png",
                "assets/img/edit_button/toPreviousButton_hover.png",
                "assets/img/edit_button/toPreviousButton_unable.png"
        }
     );
     previousButton.addActionListener(this);
     previousButton.setActionCommand("Previous");

     nextButton = new ImageButton(
        new String[] {
                "assets/img/edit_button/toNextButton.png",
                "assets/img/edit_button/toNextButton_pressed.png",
                "assets/img/edit_button/toNextButton_hover.png",
                "assets/img/edit_button/toNextButton_unable.png"
        }
     );
     nextButton.addActionListener(this);
     nextButton.setActionCommand("Next");

     JPanel btnPanel = new JPanel();
     btnPanel.add(previousButton);
     btnPanel.add(nextButton);

     add(cardPanel, BorderLayout.CENTER);
     add(btnPanel, BorderLayout.PAGE_END);
    try {
      backgroundImage = ImageIO.read(
        getClass().getClassLoader().getResourceAsStream(
          "assets/img/background/explain.png"
        )
      );
    } catch (IOException e) {
      e.printStackTrace();
      backgroundImage = null;
    }

     this.setVisible(true);
   }

   // paintComponentによりJPanelを背景画像で上塗りする処理
   // Override
   public void paintComponent(Graphics g) {
     Graphics2D g2 = (Graphics2D)g;

     if(backgroundImage != null) {
        g2.drawImage(backgroundImage, 0, 0, this);
     }
   }

   // ボタンを押した際の処理
   public void actionPerformed(ActionEvent e) {
     String cmd = e.getActionCommand();

     if(cmd.equals("Previous")) {
        layout.previous(cardPanel);
     }else if(cmd.equals("Next")) {
        layout.next(cardPanel);
     }
   }

}
