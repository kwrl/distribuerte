import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteStub;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

/**
 * A Tic Tac Toe application. Currently this is a stand-alone application where
 * players take alternating turns using the same computer.
 * <p/>
 * The task is to transform it to a networking application using RMI.
 */
public class TicTacToe extends JFrame implements ListSelectionListener {
	private static final int BOARD_SIZE = 15;
	protected BoardModel boardModel;
	protected final JTable board;
	private final JLabel statusLabel = new JLabel();
	protected final char playerMarks[] = { 'X', 'O' };
	protected int currentPlayer = 0; // Player to set the next mark.

	public TicTacToe() {
		super("TDT4190: Tic Tac Toe");
		
		boardModel = new BoardModel(BOARD_SIZE);
		board = new JTable(boardModel);
		board.setFont(board.getFont().deriveFont(25.0f));
		board.setRowHeight(30);
		board.setCellSelectionEnabled(true);
		for (int i = 0; i < board.getColumnCount(); i++)
			board.getColumnModel().getColumn(i).setPreferredWidth(30);
		board.setGridColor(Color.BLACK);
		board.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer dtcl = new DefaultTableCellRenderer();
		dtcl.setHorizontalAlignment(SwingConstants.CENTER);
		board.setDefaultRenderer(Object.class, dtcl);
		board.getSelectionModel().addListSelectionListener(this);
		board.getColumnModel().getSelectionModel()
				.addListSelectionListener(this);

		statusLabel.setPreferredSize(new Dimension(statusLabel
				.getPreferredSize().width, 40));
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(statusLabel, BorderLayout.SOUTH);
		pack();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		int centerX = (int) (Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() - getSize().width) / 2;
		int centerY = (int) (Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight() - getSize().height) / 2;
		setLocation(centerX, centerY);
		setVisible(true);
	}

	void setStatusMessage(String status) {
		statusLabel.setText(status);
	}

	/**
	 * This has to be modified. Currently the application is stand-alone so both
	 * players have to use the same computer.
	 * <p/>
	 * When completed, marks from the first player originates from a
	 * ListSelectionEvent and is then sent to the second player. And marks from
	 * the second player is received and added to the board of the first player.
	 */
	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;
		int x = board.getSelectedColumn();
		int y = board.getSelectedRow();
		if (x == -1 || y == -1 || !boardModel.isEmpty(x, y))
			return;
		if (boardModel.setCell(x, y, playerMarks[currentPlayer]))
			setStatusMessage("Player " + playerMarks[currentPlayer] + " won!");
		currentPlayer = 1 - currentPlayer; // The next turn is by the other
											// player.
	}
	
}
