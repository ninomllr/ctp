package ch.bfh.gui;

import java.awt.Color;
import java.awt.Component;

import java.util.List;
import java.util.Vector;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import ch.bfh.simulation.*;
import ch.bfh.simulation.tasks.Task;
import ch.bfh.utils.TaskListInfo;

/**
 * Class to list tasks on the GUI
 * @author gigi
 *
 */
public class TaskPanel{

	private static TaskPanel _task;
	Vector<Vector> _data = new Vector<Vector>();
	JTable _taskList = null;
	
	public static TaskPanel getInstance()
	{
		if(_task == null) _task = new TaskPanel();	
		return _task;
	}
	
	
	public TaskPanel()
	{
		initTaskPanel();
	}
	
	public void initTaskPanel()
	{
		Vector<String> columnNames = new Vector<String>(); //{"Start Point", "End Point", "Start Time" , "AGV" };
		columnNames.add("Start Point");
		columnNames.add("End Point");
		columnNames.add("Start Time");
		columnNames.add("AGV");

		_taskList = new JTable(_data, columnNames)
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(
		        TableCellRenderer renderer, int row, int column)
		    {
		        Component c = super.prepareRenderer(renderer, row, column);

		        int modelRow = convertRowIndexToModel(row);
		        
				String type = (String)getModel().getValueAt(modelRow, 3);
				
				if ("".equals(type))
					c.setBackground(Color.white);
				else
					c.setBackground(Simulation.getInstance().getModel().getVehicle(type).getToken().getColor());

		        return c;
		    }
		};;
		
		JScrollPane scrPane = new JScrollPane(_taskList);
		_taskList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public JTable getTable()
	{
		return _taskList;
	}
	
	public void setNewTask(Task tList)
	{
		Vector<String> s = new Vector<String>(); 
		s.add(tList.getFrom());
		s.add(tList.getTo());
		s.add(String.valueOf(tList.getArrival()));
		
		if(tList.getVehicle() != null)
		{
			s.add(tList.getVehicle().getId());
			
		}
		else
			s.add("");
		
		_data.add(s);
		
		((DefaultTableModel)_taskList.getModel()).fireTableDataChanged();
	}
	
	public void removeTask(TaskListInfo tlist, String idVehicle)
	{	
		int i = 0;
		for(Vector<String> v : _data)
		{
			if(v.elementAt(0) == tlist.getFrom() &&
					v.elementAt(1) == tlist.getTo() &&
					v.elementAt(3) == idVehicle)
			{
				_data.remove(i);
				((DefaultTableModel)_taskList.getModel()).fireTableDataChanged();
			}
			i++;
		}
		
	}


	public void setNewTask(List<Task> tasks) 
	{
		for(Task tli : tasks)
		{
			setNewTask(tli);
		}
	}
	
	public void updateTaskList()
	{
		_data.clear();
		
		for(Task t : Simulation.getInstance().getModel().getTasks()){
			setNewTask(t);
		}
	}
	
	
}
