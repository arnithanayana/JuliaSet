package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class JuliaSet extends JPanel implements AdjustmentListener, ActionListener
{
    JFrame frame;
    JScrollBar saturationScroller,hueScroller,zoomScroller, aScoller, bScroller, optionScroller, brightScroller;
    JLabel saturationLabel,hueLabel,zoomLabel, aLabel, bLabel, optionLabel, brightLabel;
    JPanel scrollPanel, labelPanel, buttonPanel, mainPanel;
    JButton reset, save;
    int c,option;
    float maxIterations = 300.0f;
    double zoom;
    float saturation,hue,brightness;
    double a,b;
    JFileChooser fileChooser;
    BufferedImage juliaImage;

    public JuliaSet()
    {
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1400,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        aScoller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,-2000,2000);
        aScoller.setPreferredSize(new Dimension(780,20));
        aScoller.addAdjustmentListener(this);

        bScroller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,-2000,2000);
        bScroller.setPreferredSize(new Dimension(780,20));
        bScroller.addAdjustmentListener(this);

        saturationScroller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,100);
        saturationScroller.setPreferredSize(new Dimension(780,20));
        saturationScroller.addAdjustmentListener(this);

        hueScroller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,100);
        hueScroller.setPreferredSize(new Dimension(780,20));
        hueScroller.addAdjustmentListener(this);

        brightScroller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,100);
        brightScroller.setPreferredSize(new Dimension(780,20));
        brightScroller.addAdjustmentListener(this);

        zoomScroller = new JScrollBar(JScrollBar.HORIZONTAL,10,0,0,100);
        zoomScroller.setPreferredSize(new Dimension(780,20));
        zoomScroller.addAdjustmentListener(this);

        optionScroller = new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,3);
        optionScroller.setPreferredSize(new Dimension(780,20));
        optionScroller.setUnitIncrement(1);
        optionScroller.addAdjustmentListener(this);

        reset = new JButton("Reset");
        reset.addActionListener(this);

        save = new JButton("Save");
        save.addActionListener(this);
        String currDir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(currDir);

        saturation = saturationScroller.getValue()/100.0f;
        hue = hueScroller.getValue()/100.0f;
        zoom = zoomScroller.getValue()/10.0;
        a = aScoller.getValue()/1000.0;
        b = bScroller.getValue()/1000.0;
        option = optionScroller.getValue();
        brightness = brightScroller.getValue()/100.0f;

        GridLayout grid = new GridLayout(7,1);

        saturationLabel = new JLabel("Saturation: "+saturation);
        hueLabel = new JLabel("Hue: "+hue);
        zoomLabel = new JLabel("Zoom: "+zoom);
        aLabel = new JLabel("A: "+a);
        bLabel = new JLabel("B: "+b);
        optionLabel = new JLabel("Graph Option: "+option);
        brightLabel = new JLabel("Brightness: "+brightness);

        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(saturationLabel);
        labelPanel.add(hueLabel);
        labelPanel.add(zoomLabel);
        labelPanel.add(optionLabel);
        labelPanel.add(brightLabel);



        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(aScoller);
        scrollPanel.add(bScroller);
        scrollPanel.add(saturationScroller);
        scrollPanel.add(hueScroller);
        scrollPanel.add(zoomScroller);
        scrollPanel.add(optionScroller);
        scrollPanel.add(brightScroller);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.add(reset);
        buttonPanel.add(save);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(labelPanel,BorderLayout.WEST);
        mainPanel.add(scrollPanel,BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(mainPanel,BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.fillRect(0,0,frame.getWidth(),frame.getHeight());
        g.drawImage(drawJulia(),0,0,null);

    }

    public BufferedImage drawJulia()
    {
        int w = getWidth();
        int h = getHeight();
         juliaImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < w; row++) {
            for (int col = 0; col < h; col++) {
                double zx = 1.5 * (row - w / 2) / (0.5 * zoom * w);
                double zy = (col - h / 2) / (0.5 * zoom * h);
                float i = maxIterations;
            switch(option) {
                case 0:
                while (zx * zx + zy * zy < 6 && i > 0) {
                    double temp = zx * zx - zy * zy + a;
                    zy = 2.0 * zx * zy + b;
                    zx = temp;
                    i--;
                }
                break;
                case 1:
                    while (zx * zx + zy < 6 && i > 0) {
                        double temp = zx * zx - zy + a;
                        zy = 2.0 * zx * zy + b;
                        zx = temp;
                        i--;
                    }
                    break;
                case 2:
                    while (zx + zy * zy < 6 && i > 0) {
                        double temp = zx - zy * zy + a;
                        zy = 2.0 * zx * zy + b;
                        zx = temp;
                        i--;
                    }
                    break;
                case 3:
                    while (zx* zx* zx +  zy < 6 && i > 0) {
                        double temp = zx* zx* zx - zy + a;
                        zy = 2.0 * zx * zy + b;
                        zx = temp;
                        i--;
                    }
                    break;
            }

                if (i > 0)
                    c = Color.HSBtoRGB(hue*(i /maxIterations) % 1, saturation, brightness);
                else c = Color.HSBtoRGB(i/maxIterations, saturation, brightness);
                juliaImage.setRGB(row, col, c);
            }
        }

        return juliaImage;
    }

    public void reset()
    {
        aScoller.setValue(0);
        bScroller.setValue(0);
        zoomScroller.setValue(10);
        saturationScroller.setValue(0);
        hueScroller.setValue(0);
        brightScroller.setValue(0);
        optionScroller.setValue(0);
        repaint();
    }
    public void save()
    {
        if(juliaImage!=null)   
        {
            FileFilter filter=new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                File file=fileChooser.getSelectedFile();
                try
                {
                    String st=file.getAbsolutePath();
                    if(st.indexOf(".png")>=0)
                        st=st.substring(0,st.length()-4);
                    ImageIO.write(juliaImage,"png",new File(st+".png"));
                }catch(IOException e)
                {
                }

            }
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
       if(e.getSource()==saturationScroller) {
           saturation = saturationScroller.getValue()/100.0f;
           saturationLabel.setText("Saturation: "+saturation+"\t\t");
       }
        if(e.getSource()==hueScroller) {
            hue = hueScroller.getValue()/100.0f;
            hueLabel.setText("Hue: "+hue+"\t\t");
        }
        if(e.getSource()==zoomScroller) {
            zoom = zoomScroller.getValue()/10.0;
            zoomLabel.setText("Zoom: "+zoom+"\t\t");
        }
        if(e.getSource()==aScoller) {
            a = aScoller.getValue()/1000.0;
            aLabel.setText("A: "+a+"\t\t");
        }
        if(e.getSource()==bScroller) {
            b = bScroller.getValue()/1000.0;
            bLabel.setText("B: "+b+"\t\t");
        }
        if(e.getSource()==optionScroller){
            option = optionScroller.getValue();
            optionLabel.setText("Graph Option: "+option+"\t\t");
        }
        if(e.getSource()==brightScroller){
            brightness=brightScroller.getValue()/100.0f;
            brightLabel.setText("Brightness: "+brightness+"\t\t");
        }
       repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==reset){
            reset();
        }
        if(e.getSource()==save){
            save();
        }

    }

    public static void main(String[] args)
    {
	    JuliaSet juliaSet = new JuliaSet();
    }



}
