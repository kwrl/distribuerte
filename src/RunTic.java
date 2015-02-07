import java.rmi.registry.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RunTic {
	private static Registry reg;
	private static int playerID;
	
	public static void init_registry() {
		try {
			reg = LocateRegistry.createRegistry(Settings.REGISTRY_PORT);
			playerID = 0;
			System.out.println("Registry setup");
		} catch (RemoteException err) {
			try {
				reg = LocateRegistry.getRegistry(Settings.REGISTRY_PORT);
				playerID = 1;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("Connected to registry");
		}
	}

	public static void main(String[] args) {
		init_registry();
		try {
			new RemoteTicTacToeImpl(reg, playerID);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
