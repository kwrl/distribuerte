class ClientTicTacToe extends TicTacToe {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServerTicTacToe server;
	
	public ClientTicTacToe(ServerTicTacToe server) {
		this.server = server;
	}
}