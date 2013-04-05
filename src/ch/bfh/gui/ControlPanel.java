package ch.bfh.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.bfh.simulation.Simulation;

public class ControlPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimulationObjects _simObjects;
	JLabel lblSysTime;
	JLabel lblPercentage;
	JButton btRun;
	
	public ControlPanel()
	{
		initControlPanel();
	}
	
	private void initControlPanel()
	{
		_simObjects = SimulationObjects.getInstance();
		
		//Scale Slider
		final JSlider scaleSlider = new JSlider(JSlider.VERTICAL,1, 10, 10);
		scaleSlider.setValue(0);
		scaleSlider.setMinorTickSpacing(1);
		scaleSlider.setMajorTickSpacing(9);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		scaleSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				//TestGui.m.getPanel().setScalefactor(scaleSlider.getValue());
				_simObjects.setScalefactor(scaleSlider.getValue());
				GUI_Window.mFrame.validate();
			}
		});
		
		//Speed Slider
		final JSlider speedSlider = new JSlider(JSlider.VERTICAL,1, 100, 1);
		speedSlider.setValue(4);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setMajorTickSpacing(10);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Simulation.getInstance().setSpeed(speedSlider.getValue());
				
			}
		});
		
		//Start Stop Button
		btRun = new JButton("Stop");
		btRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Simulation.getInstance().getRunning())
				{
					btRun.setText("Stop");
					Simulation.getInstance().start();
				}
				else
				{
					btRun.setText("Start");
					Simulation.getInstance().stop();
				}
				
			}
		});
		
		lblSysTime = new JLabel();
		
		this.add(lblSysTime);
		this.add(btRun);
		this.add(speedSlider);
		this.add(scaleSlider);

	}

	public void setLblSysTime(String s) {
		this.lblSysTime.setText(s);
	}
	
	public void setButtonText() {
		
		if(Simulation.getInstance().getRunning())
		{
			this.btRun.setText("Stop");
		}
		else
		{
			btRun.setText("Start");
		}
	}
	
	
	
}
