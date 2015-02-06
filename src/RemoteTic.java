import java.rmi.Remote;

import javax.swing.event.ListSelectionEvent;

public interface RemoteTic extends Remote {
	void setClient(ClientTicTacToe client);
	void valueChanged(ListSelectionEvent evt);
}
