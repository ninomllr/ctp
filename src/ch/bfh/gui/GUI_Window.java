package ch.bfh.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ch.bfh.simulation.*;

public class GUI_Window implements ActionListener{

	private static final int FRAME_WIDTH = 1024;
	private static final int FRAME_HEIGHT = 600;
	
	private Timer _timer;
	private DynamicPanel _panel;

	//Init Frame
	static JFrame mFrame = null;
	
	//init static panel
	static StaticPanel statPanel = new StaticPanel();
	
	//init center panel
	static JPanel center = new JPanel();
	
	//init control panel
	static ControlPanel contrPanel = new ControlPanel();
	
	//init scollable pane
	static JScrollPane scrollPane = new JScrollPane();
	
	static TaskPanel tPanel = TaskPanel.getInstance();
	
	//Create new dimension for center panel
	static Dimension dimFrame = new Dimension();
	
	//init TaskPanel
	static JScrollPane scrTaskPane = null;
	
	//init main panel
	static JPanel main = null;
	
	static GUI_Window m;
	
	/**
	 * Returns panel
	 * @return
	 */
	public DynamicPanel getPanel() {
		return _panel;
	}

	/**
	 * Init GUI
	 * @param dynamicPanel
	 */
	GUI_Window(DynamicPanel dynamicPanel) {
		_timer = new Timer(100, this);
		_panel = dynamicPanel;
	}
	
	/**
	 * action that repaints gui
	 */
	public void actionPerformed(ActionEvent e) {
		_panel.repaint();		
		
		//reset size of Panel
		dimFrame.setSize(StaticPanel.getDimension());
		center.setPreferredSize(dimFrame);
		scrTaskPane.setPreferredSize(new Dimension(FRAME_WIDTH, 250));

		tPanel.updateTaskList();
		
		contrPanel.setLblSysTime(Simulation.getInstance().getSimulationTimeFormatted());
	}
	
	/**
	 * start timer
	 */
	public void startTimer() {
		_timer.start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		initCenterPanel();
		initStaticPanel();
		initFrame();
		
		//Init Tasks
		tPanel.setNewTask(Simulation.getInstance().getModel().getTasks());

		//start frame timer for automatic refresh
		m.startTimer();
	}

	private static void initStaticPanel() {
		scrTaskPane = new JScrollPane(tPanel.getTable());
		
		//JSplitPane spPaneTop = new JSplitPane(JSplitPane.VERTICAL_SPLIT,scrollPane, contrPanel);
		main = new JPanel(new BorderLayout());
		
		main.add(scrollPane, BorderLayout.CENTER);
		main.add(contrPanel, BorderLayout.EAST);
	}

	private static void initFrame() {
		//Create and init new frame
		mFrame = new JFrame();
		mFrame.setTitle("AGV Simulation");
		mFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setVisible(true);
		mFrame.validate();
		
		//Set Frame to fullscreen
		GraphicsEnvironment localEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultDevice = localEnvironment.getDefaultScreenDevice();
		defaultDevice.setFullScreenWindow(mFrame);
		
		JSplitPane spPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, main, scrTaskPane);
		spPaneMain.setDividerLocation(FRAME_HEIGHT-250);
		
		mFrame.add(spPaneMain);
	}

	private static void initCenterPanel() {
		DynamicPanel dynPanel = new DynamicPanel();		
		m = new GUI_Window(dynPanel);
		LayoutManager overlay = new OverlayLayout(center); 
		center.setLayout(overlay);
		center.add(m.getPanel());
		center.add(statPanel);
		center.setBackground(Color.decode("#006666"));
		
		//Set size for center panel
		dimFrame.setSize(StaticPanel.getDimension());
		center.setPreferredSize(dimFrame);
		
		scrollPane.setViewportView(center);
	}
}
