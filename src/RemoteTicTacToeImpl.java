import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

class RemoteTicTacToeImpl extends TicTacToe implements RemoteTicTacToe {
	private Registry registry;
	private RemoteTicTacToe remote;

	public RemoteTicTacToeImpl(Registry registry, boolean isPlayerOne)
			throws RemoteException, NotBoundException {
		super();
		boolean bound = false;
		this.registry = registry;
		RemoteTicTacToe stub = (RemoteTicTacToe) UnicastRemoteObject.exportObject(
				this, 0);
		this.registry.rebind(Settings.NAME+isPlayerOne, stub);
		while(!bound) {
			try {
				this.remote= (RemoteTicTacToe) registry.lookup(Settings.NAME+!isPlayerOne);
				bound=true;
			} catch(NotBoundException err) {
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		super.valueChanged(evt);
		try {
			this.remote.valueChanged(evt);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}