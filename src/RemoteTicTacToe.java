import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.event.ListSelectionEvent;

public interface RemoteTicTacToe extends Remote {
	public void remoteEvent(int x, int y, char mark) throws RemoteException;
	public void valueChanged(ListSelectionEvent evt) throws RemoteException;
}
