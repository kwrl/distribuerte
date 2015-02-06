import javax.swing.event.ListSelectionEvent;


class ServerTicTacToe extends TicTacToe implements RemoteTic {
	private static final long serialVersionUID = 1L;
	private ClientTicTacToe client;

	public ServerTicTacToe() {
	}

	@Override
	public void valueChanged(ListSelectionEvent evt) {
		super.valueChanged(evt);
	}

	@Override
	public void setClient(ClientTicTacToe client) {
		this.client = client;
	}
  }