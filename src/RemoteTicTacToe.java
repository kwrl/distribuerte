import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

public interface RemoteTicTacToe extends Remote {
	public void valueChanged(ListSelectionEvent evt) throws RemoteException;
}
