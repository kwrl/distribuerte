import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.event.ListSelectionEvent;

class RemoteTicTacToeImpl extends TicTacToe implements RemoteTicTacToe {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Registry registry;
	private RemoteTicTacToe remote;
	private int playerID;

	public RemoteTicTacToeImpl(Registry registry, int playerID)
			throws RemoteException, NotBoundException {
		super();
		this.registry = registry;
		this.playerID = playerID;
		RemoteTicTacToe stub = (RemoteTicTacToe) UnicastRemoteObject.exportObject(
				this, 0);
		this.registry.rebind(Settings.NAME+playerID, stub);
		while(remote==null) {
			try {
				this.remote = (RemoteTicTacToe) registry.lookup(Settings.NAME+(1-playerID));
			} catch(NotBoundException err) {
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if(evt.getValueIsAdjusting() || currentPlayer!=playerID) {
			return;
		}
		
		int x, y;
		x = board.getSelectedColumn();
		y = board.getSelectedRow();
		
		if(boardModel.isEmpty(x, y)) {
			try {
				this.remote.remoteEvent(x, y, playerMarks[playerID]);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (boardModel.setCell(x, y, playerMarks[currentPlayer]))
				setStatusMessage("Player " + playerMarks[currentPlayer] + " won!");
			currentPlayer = 1 - currentPlayer;
		}
	}

	@Override
	public void remoteEvent(int x, int y, char mark) throws RemoteException {
		if (boardModel.setCell(x, y, playerMarks[currentPlayer]))
			setStatusMessage("Player " + playerMarks[currentPlayer] + " won!");
		currentPlayer = 1 - currentPlayer;
	}
}