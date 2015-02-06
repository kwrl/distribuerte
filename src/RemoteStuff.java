import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.event.TableModelListener;

public interface RemoteStuff extends Remote {
	public void addTableModelListener(TableModelListener l) throws RemoteException;
}
