package com.SuperLcb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HttpSocket
{

    public static void main(String[] args) throws Exception
    {
        Run();
    }
    static  void Run()
    {
        JFrame jFrame=new JFrame("LCB Translater");
        jFrame.setBounds(300,300,300,300);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setLayout(new GridLayout(2,1));
        TextField textField=new TextField();
        textField.setFont(new Font("宋体",Font.BOLD,20));
        JTextArea textArea=new JTextArea();
        textArea.setFont(new Font("宋体",Font.BOLD,20));
        textArea.setLineWrap(true);
        jFrame.add(textField);
        jFrame.add(textArea);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar()=='\n')
                {
                    textArea.setText("");
                    try {
                        textArea.setText(Translater.Convert(textField.getText()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    textField.setText("");
                }
            }
        });
        jFrame.setVisible(true);
    }
}
class Translater
{
    public static String Convert(String query) throws Exception
    {
        String To="英文";
        char jurge=query.trim().charAt(0);
        if((jurge<='Z'&&jurge>='A')||(jurge>='a'&&jurge<='z'))
        {
            To="中文";
        }
        StringBuilder res=new StringBuilder();
        StringBuilder find=new StringBuilder("http://www.baidu.com/baidu?wd=");
        find.append(query+" ").append(To+"翻译");
//        Map<String,String> header=new HashMap<String, String>();
//        header.put("Host","www.baidu.com");
//        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
        Document document= Jsoup.connect(find.toString()).get();
        Elements Text1=document.select("span[class=op_dict_text1 c-gap-right]");
        Elements Text2=document.select("span[class=op_dict_text2]");
        if(Text1.size()+Text2.size()==0)
        {
            //System.out.println("Yes");
            //Text1=document.select("p[class=op_sp_fanyi_line_one]");
            Text2=document.select("p[class=op_sp_fanyi_line_two]");
        }
        for(int i=0;i<Text2.size();i++)
        {
            if(Text1.size()>i)
            res.append(Text1.get(i).text());
            res.append(Text2.get(i).text()).append('\n');
        }
        return res.toString();
    }
}